// Tommy Li (tommy.li@firefire.co), 2017-06-24

package co.firefire.fenergy.nem12.domain.projection

//import org.springframework.data.rest.core.config.Projection
import co.firefire.fenergy.nem12.domain.IntervalQuality
import java.math.BigDecimal
import java.time.LocalDate

//@Projection(name = "totals", types = arrayOf(IntervalDay::class))
interface Totals {

    fun getIntervalDate(): LocalDate

    fun getIntervalQuality(): IntervalQuality

    fun getTotal(): BigDecimal

}
