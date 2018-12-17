// Tommy Li (tommy.li@firefire.co), 2018-04-28

package co.firefire.fenergy.battery.domain

import co.firefire.fenergy.shared.domain.IntervalLength
import co.firefire.fenergy.shared.domain.LoginNmi
import co.firefire.fenergy.shared.domain.UnitOfMeasure
import co.firefire.fenergy.shared.repository.IntervalLengthConverter
import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.Parameter
import java.time.Duration
import java.util.*
import javax.persistence.CascadeType
import javax.persistence.Convert
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.MapKey
import javax.persistence.OneToMany
import javax.persistence.OrderBy

//@Entity
data class Battery(

        @ManyToOne(optional = false)
        @JoinColumn(name = "login_nmi", referencedColumnName = "id", nullable = false)
        var loginNmi: LoginNmi = LoginNmi()

) : Comparable<Battery> {

    @Id
    @GenericGenerator(
            name = "BatteryIdSeq",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = arrayOf(
                    Parameter(name = "sequence_name", value = "battery_id_seq"),
                    Parameter(name = "initial_value", value = "1000"),
                    Parameter(name = "increment_size", value = "1")
            )
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BatteryIdSeq")
    var id: Long? = null

    var version: Int? = 0

    @Enumerated(EnumType.STRING)
    var uom: UnitOfMeasure = UnitOfMeasure.KWH

    @Convert(converter = IntervalLengthConverter::class)
    var intervalLength: IntervalLength = IntervalLength.IL_30

    @OneToMany(mappedBy = "battery", cascade = arrayOf(CascadeType.ALL), orphanRemoval = true)
    @MapKey(name = "dateInterval")
    @OrderBy("dateInterval")
    var batteryDayValues: SortedMap<DateInterval, BatteryDayValue> = TreeMap()

    fun intervalRange(): IntRange {
        return 1..(Duration.ofDays(1).toMinutes().toInt() / intervalLength.minute)
    }

    fun mergeIntervalDays(newBatteryDays: Collection<BatteryDayValue>) {
        newBatteryDays.forEach({ mergeBatteryDay(it) })
    }

    fun mergeBatteryDay(newBatteryDay: BatteryDayValue) {
        batteryDayValues.merge(newBatteryDay.dateInterval, newBatteryDay, { existing: BatteryDayValue, new: BatteryDayValue ->
            existing.charge = new.charge
            existing.disCharge = new.disCharge
            existing.stateOfCharge = new.stateOfCharge
            existing
        })
    }

    override fun compareTo(other: Battery) = compareValuesBy(this, other, { it.loginNmi })

    override fun toString() = "Battery(id=$id, loginNmi='$loginNmi')"

}
