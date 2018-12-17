// Tommy Li (tommy.li@firefire.co), 2017-07-03

package co.firefire.fenergy.nem12.domain

import co.firefire.fenergy.nem12.service.Nem12Line
import co.firefire.fenergy.nem12.service.Nem12RecordId
import co.firefire.fenergy.nem12.service.Nem12RecordType
import co.firefire.fenergy.shared.domain.DomainException
import co.firefire.fenergy.shared.domain.NEM12_DELIMITER
import java.io.InputStreamReader
import java.math.BigDecimal
import java.util.regex.Pattern

fun InputStreamReader.forEachNem12Line(delimiter: Pattern = NEM12_DELIMITER, block: (nem12Line: Nem12Line) -> Unit, finalizer: () -> Unit) {
    try {
        this.use { inputStreamReader: InputStreamReader ->
            var lineNumber: Int = 1

            val distinctRecordTypes = mutableSetOf<Nem12RecordType>()
            inputStreamReader.forEachLine { line: String ->
                val items = line.split(delimiter)
                val recordType = Nem12RecordType(Nem12RecordId.valueOf("ID_${items[0]}"))
                val nem12Line = Nem12Line(lineNumber++, recordType, items)
                distinctRecordTypes.add(recordType)
                block(nem12Line)
            }
            if (distinctRecordTypes.none { it == Nem12RecordType.Record900 }) {
                finalizer()
            }
        }
    } catch (e: Exception) {
        throw DomainException("Error splitting file contents using delimiter [$delimiter]: $e", e)
    }
}

inline fun <T> Iterable<T>.sumByBigDecimal(selector: (T) -> BigDecimal): BigDecimal {
    return this.fold(BigDecimal.ZERO) { acc, t -> acc + selector(t) }
}
