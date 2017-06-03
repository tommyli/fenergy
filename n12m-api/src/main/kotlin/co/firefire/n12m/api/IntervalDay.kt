// Tommy Li (tommy.li@firefire.co), 2017-03-10

package co.firefire.n12m.api

import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*
import java.util.regex.Pattern
import javax.persistence.CollectionTable
import javax.persistence.ElementCollection
import javax.persistence.Embedded
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.MapKey
import javax.persistence.OrderBy
import org.hibernate.annotations.GenericGenerator as HbmGenericGenerator
import org.hibernate.annotations.Parameter as HbmParameter
import javax.persistence.Transient as JpaTransient

val MINUTES_IN_DAY: Long = Duration.ofDays(1).toMinutes()
private val VALUE_DELIMITER: Pattern = ",".toPattern()

@Entity
data class IntervalDay(

        @ManyToOne(optional = false)
        @JoinColumn(name = "nmi_meter_register", referencedColumnName = "id", nullable = false)
        var nmiMeterRegister: NmiMeterRegister = NmiMeterRegister(),

        var intervalDate: LocalDate = LocalDate.MIN,

        @Embedded
        var intervalQuality: IntervalQuality = IntervalQuality(Quality.A)

) {

    @Id
    @HbmGenericGenerator(
            name = "IntervalDayIdSeq",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = arrayOf(
                    HbmParameter(name = "sequence_name", value = "interval_day_id_seq"),
                    HbmParameter(name = "initial_value", value = "1000"),
                    HbmParameter(name = "increment_size", value = "50")
            )
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "IntervalDayIdSeq")
    var id: Long = -1

    var updateDateTime: LocalDateTime? = null
    var msatsLoadDateTime: LocalDateTime? = null

    @JpaTransient
    @Transient
    var intervalEvents: MutableMap<IntRange, IntervalEvent> = mutableMapOf()

    @ElementCollection
    @CollectionTable(
            name = "interval_value",
            joinColumns = arrayOf(JoinColumn(name = "interval_day", referencedColumnName = "id", nullable = false))
    )
    @MapKey(name = "interval")
    @OrderBy("interval")
    var intervalValues: SortedMap<Int, IntervalValue> = TreeMap()

    fun applyIntervalEvents() {
        assert(intervalValues.all { it.value.intervalQuality.quality != Quality.V }, { "Individual values should never have V quality." })

        if (intervalQuality.quality == Quality.V) {
            intervalValues = TreeMap(intervalValues.mapValues { (interval, intervalValue) ->
                val newQuality = intervalEvents.values.find { it.intervalRange.contains(interval) }?.intervalQuality ?: IntervalQuality(Quality.A)
                IntervalValue(interval, intervalValue.value, newQuality)
            })
        }
    }

    fun mergeNewIntervalValues(newIntervalValues: Map<Int, IntervalValue>, newUpdateDateTime: LocalDateTime?, newMsatsLoadDateTime: LocalDateTime?) {
        val existingDateTime = updateDateTime ?: msatsLoadDateTime ?: LocalDateTime.now()
        val newDateTime = newUpdateDateTime ?: newMsatsLoadDateTime ?: LocalDateTime.now()

        validateIntervalValueQuality(intervalValues)
        validateIntervalValueQuality(newIntervalValues)

        newIntervalValues.forEach({ newInterval: Int, newIntervalValue: IntervalValue ->
            intervalValues.merge(newInterval, newIntervalValue, { existing: IntervalValue, new: IntervalValue ->
                val existingQuality = TimestampedQuality(existing.intervalQuality.quality, existingDateTime)
                val newQuality = TimestampedQuality(new.intervalQuality.quality, newDateTime)
                if (newQuality >= existingQuality) {
                    new
                } else {
                    existing
                }
            })
        })
    }

    fun appendIntervalEvent(intervalEvent: IntervalEvent) {
        intervalEvents.put(intervalEvent.intervalRange, intervalEvent)
    }

    override fun toString(): String {
        return "IntervalDay(id=$id, nmiMeterRegister=$nmiMeterRegister, intervalDate=$intervalDate)"
    }

    private fun validateIntervalValueQuality(intervalValues: Map<Int, IntervalValue>) {
        assert(intervalValues.all { it.value.intervalQuality.quality != Quality.V }, { "Individual values should never have V quality." })
    }
}
