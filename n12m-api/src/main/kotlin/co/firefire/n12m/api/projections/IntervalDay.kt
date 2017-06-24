// Tommy Li (tommy.li@firefire.co), 2017-06-24

package co.firefire.n12m.api.projections

import co.firefire.n12m.api.IntervalDay
import co.firefire.n12m.api.IntervalQuality
import org.springframework.data.rest.core.config.Projection
import java.math.BigDecimal
import java.time.LocalDate

@Projection(name = "totals", types = arrayOf(IntervalDay::class))
interface Totals {

    fun getIntervalDate(): LocalDate

    fun getIntervalQuality(): IntervalQuality

    fun getTotal(): BigDecimal

}
