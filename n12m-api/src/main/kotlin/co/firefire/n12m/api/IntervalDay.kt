// Tommy Li (tommy.li@firefire.co), 2017-03-10

package co.firefire.n12m.api

import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*
import java.util.regex.Pattern
import javax.persistence.CascadeType
import javax.persistence.Embedded
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.MapKey
import javax.persistence.OneToMany
import javax.persistence.OrderBy
import org.hibernate.annotations.GenericGenerator as HbmGenericGenerator
import org.hibernate.annotations.Parameter as HbmParameter

val MINUTES_IN_DAY: Long = Duration.ofDays(1).toMinutes()
private val VALUE_DELIMITER: Pattern = ",".toPattern()

@Entity
data class IntervalDay(

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
        var id: Long = -1,

        @ManyToOne(optional = false)
        @JoinColumn(name = "nmi_meter_register", referencedColumnName = "id", nullable = false)
        var nmiMeterRegister: NmiMeterRegister = NmiMeterRegister(),

        var intervalDate: LocalDate = LocalDate.MIN,

        @Embedded
        var intervalQuality: IntervalQuality = IntervalQuality(Quality.A)

) {

    var updateDateTime: LocalDateTime? = null
    var msatsLoadDateTime: LocalDateTime? = null

    var values: String? = ""

    @OneToMany(mappedBy = "intervalDay", cascade = arrayOf(CascadeType.ALL))
    @MapKey(name = "startInterval")
    @OrderBy("startInterval")
    var intervalEvents: SortedMap<Int, IntervalEvent> = TreeMap()

    fun getIntervalVolumes(): SortedMap<Int, Double> {
        var i: Int = 1

        return TreeMap(values?.split(VALUE_DELIMITER)?.associate({ Pair(i++, it.toDouble()) }) ?: sortedMapOf())
    }

    override fun toString(): String {
        return "IntervalDay(id=$id, nmiMeterRegister=$nmiMeterRegister, intervalDate=$intervalDate)"
    }
}
