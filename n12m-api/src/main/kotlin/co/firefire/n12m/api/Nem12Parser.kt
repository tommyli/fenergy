// Tommy Li (tommy.li@firefire.co), 2017-05-27

package co.firefire.n12m.api

import org.slf4j.LoggerFactory
import org.springframework.core.io.Resource
import java.io.InputStreamReader
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.regex.Pattern

interface Nem12Parser {

    fun parseNem12Resource(resource: Resource): Collection<NmiMeterRegister>

}

interface Nem12ParserContext {

    fun getCurrentRecordType(): Nem12RecordType?
    fun updateCurrentRecordType(recordType: Nem12RecordType)
    fun getCurrentNmiMeterRegister(): NmiMeterRegister?
    fun updateCurrentNmiMeterRegister(nmiMeterRegister: NmiMeterRegister)
    fun getCurrentIntervalDay(): IntervalDay?
    fun updateCurrentIntervalDay(intervalDay: IntervalDay)
    fun mergeNmiMeterRegisterResult()

}

interface ErrorCollector {

    fun addError(error: String)

}

private val NEM12_DELIMITER: Pattern = ",".toPattern()

enum class Nem12RecordId(val id: String) {
    ID_100("100"), ID_200("200"), ID_300("300"), ID_400("400"), ID_500("500"), ID_900("600");
}

sealed class Nem12RecordType(val nem12RecordId: Nem12RecordId, val nextValidRecordTypes: Set<Nem12RecordType>) {

    fun isNextRecordTypeValid(recordType: Nem12RecordType): Boolean {
        return nextValidRecordTypes.contains(recordType)
    }

    companion object {
        operator fun invoke(nem12RecordId: Nem12RecordId): Nem12RecordType {
            return when (nem12RecordId) {
                Nem12RecordId.ID_100 -> Nem12RecordType.Record100
                Nem12RecordId.ID_200 -> Nem12RecordType.Record200
                Nem12RecordId.ID_300 -> Nem12RecordType.Record300
                Nem12RecordId.ID_400 -> Nem12RecordType.Record400
                Nem12RecordId.ID_500 -> Nem12RecordType.Record500
                Nem12RecordId.ID_900 -> Nem12RecordType.Record900
            }
        }
    }

    object Record100 : Nem12RecordType(Nem12RecordId.ID_100, setOf(Record200, Record900))

    object Record200 : Nem12RecordType(Nem12RecordId.ID_200, setOf(Record300, Record900))

    object Record300 : Nem12RecordType(Nem12RecordId.ID_300, setOf(Record200, Record300, Record400, Record900))

    object Record400 : Nem12RecordType(Nem12RecordId.ID_400, setOf(Record200, Record300, Record400, Record500, Record900))

    object Record500 : Nem12RecordType(Nem12RecordId.ID_500, setOf(Record200, Record300, Record500, Record900))

    object Record900 : Nem12RecordType(Nem12RecordId.ID_900, setOf())

}

data class Nem12Line(val lineNumber: Int, val recordType: Nem12RecordType, val lineItems: List<String>) {

    companion object {
        var log = LoggerFactory.getLogger(this::class.java)
    }

    fun handleLine(parsingContext: Nem12ParserContext, errorCollector: ErrorCollector): Unit {

        val currentRecordType: Nem12RecordType? = parsingContext.getCurrentRecordType()
        if (currentRecordType != null && currentRecordType.isNextRecordTypeValid(currentRecordType)) {
            throw RuntimeException("Invalid next record type [${recordType}], current record type [${currentRecordType}]")
        }
        try {
            when (recordType) {
                is Nem12RecordType.Record100 -> { /* ignore */
                }
                is Nem12RecordType.Record200 -> {
                    parsingContext.mergeNmiMeterRegisterResult()

                    val nmi: String = parseMandatory("nmi", 1, lineNumber, { lineItems[11] })
                    val nmiConfig = parseOptional("nmiConfig", 2, lineNumber, { if (lineItems[2].isNotBlank()) lineItems[2] else null })
                    val registerId = parseMandatory("registerId", 3, lineNumber, { lineItems[3] })
                    val nmiSuffix = parseMandatory("nmiSuffix", 4, lineNumber, { lineItems[4] })
                    val mdmDataStreamId = parseOptional("mdmDataStreamId", 5, lineNumber, { if (lineItems[5].isNotBlank()) lineItems[5] else null })
                    val meterSerial = parseMandatory("meterSerial", 6, lineNumber, { lineItems[6] })
                    val uom = parseMandatory("uom", 7, lineNumber, { UnitOfMeasure.valueOf(lineItems[7]) })
                    val intervalLength = parseMandatory("intervalLength", 8, lineNumber, { IntervalLength.fromMinute(lineItems[8].toInt()) })
                    val nextScheduledReadDate = parseOptional("nextScheduledReadDate", 9, lineNumber, { if (lineItems[9].isNotBlank()) LocalDate.parse(lineItems[9], DateTimeFormatter.BASIC_ISO_DATE) else null })

                    val nmiMeterRegister: NmiMeterRegister = NmiMeterRegister(nmi, meterSerial, registerId, nmiSuffix, uom, intervalLength)
                    nmiMeterRegister.nmiConfig = nmiConfig
                    nmiMeterRegister.mdmDataStreamId = mdmDataStreamId
                    nmiMeterRegister.nextScheduledReadDate = nextScheduledReadDate

                    parsingContext.updateCurrentNmiMeterRegister(nmiMeterRegister)
                }
                is Nem12RecordType.Record300 -> {
                }
                is Nem12RecordType.Record400 -> {
                }
                is Nem12RecordType.Record500 -> {
                }
                is Nem12RecordType.Record900 -> {
                    parsingContext.mergeNmiMeterRegisterResult()
                }
            }
        } catch (e: Exception) {
            errorCollector.addError("${e.message}")
        }
    }

    private fun <T> parseMandatory(propertyName: String, position: Int, lineNumber: Int, supplier: () -> T): T {
        return try {
            supplier()
        } catch (e: Exception) {
            throw RuntimeException("Error parsing $propertyName, position $position, line $lineNumber, exception: $e, cause: ${e.cause}")
        }
    }

    private fun <T> parseOptional(propertyName: String, position: Int, lineNumber: Int, supplier: () -> T?): T? {
        return try {
            supplier()
        } catch (e: Exception) {
            log.warn("Error parsing $propertyName, position $position, line $lineNumber, exception: $e, cause: ${e.cause}")
            null
        }
    }
}

fun InputStreamReader.forEachNem12Line(delimiter: Pattern = NEM12_DELIMITER, block: (nem12Line: Nem12Line) -> Unit) {
    try {
        this.use { inputStreamReader: InputStreamReader ->
            var lineNumber: Int = 1

            inputStreamReader.forEachLine { line: String ->
                val items = line.split(delimiter)
                val nem12Line = Nem12Line(lineNumber++, Nem12RecordType(Nem12RecordId.valueOf("ID_${items[0]}")), items)
                block(nem12Line)
            }
        }
    } catch (e: Exception) {
        throw RuntimeException("Error splitting file contents using delimiter [$delimiter]: $e", e)
    }
}

class Nem12ParserImpl : Nem12Parser, Nem12ParserContext, ErrorCollector {

    var currRecordType: Nem12RecordType? = null
    var currNmiMeterRegister: NmiMeterRegister? = null
    var currIntervalDay: IntervalDay? = null
    var result: MutableList<NmiMeterRegister> = arrayListOf()
    var errors: MutableList<String> = arrayListOf()

    override fun parseNem12Resource(resource: Resource): Collection<NmiMeterRegister> {
        try {
            InputStreamReader(resource.inputStream).forEachNem12Line { nem12Line: Nem12Line ->
                nem12Line.handleLine(this, this)
            }
        } catch (e: Exception) {
            errors.add("Error reading file: $e")
        }

        println(errors)
        println(result)

        if (errors.isNotEmpty()) {
            throw RuntimeException("${errors.joinToString("\n", "Found following errors when parsing resource [${resource.filename}]:\n", "\n", 100, "...")}")
        } else {
            return result
        }
    }

    override fun getCurrentRecordType(): Nem12RecordType? {
        return currRecordType
    }

    override fun updateCurrentRecordType(recordType: Nem12RecordType) {
        currRecordType = recordType
    }

    override fun getCurrentNmiMeterRegister(): NmiMeterRegister? {
        return currNmiMeterRegister
    }

    override fun updateCurrentNmiMeterRegister(nmiMeterRegister: NmiMeterRegister) {
        currNmiMeterRegister = nmiMeterRegister
    }

    override fun getCurrentIntervalDay(): IntervalDay? {
        return currIntervalDay
    }

    override fun updateCurrentIntervalDay(intervalDay: IntervalDay) {
        currIntervalDay = intervalDay
    }

    override fun mergeNmiMeterRegisterResult() {
        val currNmiMeterRegister = currNmiMeterRegister
        if (currNmiMeterRegister != null) {
            val existingNmiMeterRegister = result.find { it.nmi == currNmiMeterRegister.nmi && it.meterSerial == currNmiMeterRegister.meterSerial && it.registerId == currNmiMeterRegister.registerId && it.nmiSuffix == currNmiMeterRegister.nmiSuffix }
            if (existingNmiMeterRegister != null) {
                existingNmiMeterRegister.putAllDays(currNmiMeterRegister.intervalDays)
            } else {
                result.add(currNmiMeterRegister)
            }
        }
    }

    override fun addError(error: String) {
        errors.add(error)
    }
}
