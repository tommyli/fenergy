// Tommy Li (tommy.li@firefire.co), 2018-04-28

package co.firefire.fenergy.battery.domain

import java.time.LocalDate
import javax.persistence.Embeddable

@Embeddable
data class DateInterval(

        var localDate: LocalDate = LocalDate.MIN,
        var interval: Int = -1

) : Comparable<DateInterval> {

    override fun compareTo(other: DateInterval) = compareValuesBy(this, other, { it.localDate }, { it.interval })

    override fun toString() = "DateInterval(localDate=$localDate, interval='$interval')"

}
