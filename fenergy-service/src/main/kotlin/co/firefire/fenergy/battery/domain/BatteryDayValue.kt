package co.firefire.fenergy.battery.domain

import java.math.BigDecimal
import java.time.LocalDate
import javax.persistence.Embeddable
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Embeddable
data class BatteryDayValue(

        @ManyToOne(optional = false)
        @JoinColumn(name = "battery", referencedColumnName = "id", nullable = false)
        var battery: Battery = Battery(),

        var dateInterval: DateInterval = DateInterval()

) : Comparable<BatteryDayValue> {

    var charge: BigDecimal = BigDecimal.ZERO
    var disCharge: BigDecimal = BigDecimal.ZERO
    var stateOfCharge: BigDecimal = BigDecimal.ZERO

    fun getDate(): LocalDate {
        return dateInterval.localDate
    }

    fun getInterval(): Int {
        return dateInterval.interval
    }

    override fun compareTo(other: BatteryDayValue) = compareValuesBy(this, other, { it.battery }, { it.dateInterval })

    override fun toString() = "BatteryDayValue(battery=$battery, dateInterval='$dateInterval')"

}
