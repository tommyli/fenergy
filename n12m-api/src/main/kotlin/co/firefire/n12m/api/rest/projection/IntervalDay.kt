// Tommy Li (tommy.li@firefire.co), 2017-06-24

package co.firefire.n12m.api.rest.projection

import co.firefire.n12m.api.domain.IntervalDay
import co.firefire.n12m.api.domain.IntervalQuality
import org.springframework.data.rest.core.config.Projection
import java.math.BigDecimal
import java.time.LocalDate

@Projection(name = "totals", types = arrayOf(IntervalDay::class))
interface Totals {

    fun getIntervalDate(): LocalDate

    fun getIntervalQuality(): IntervalQuality

    fun getTotal(): BigDecimal

}
