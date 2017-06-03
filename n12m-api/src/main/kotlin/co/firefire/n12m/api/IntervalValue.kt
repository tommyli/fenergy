// Tommy Li (tommy.li@firefire.co), 2017-06-03

package co.firefire.n12m.api

import javax.persistence.Embeddable
import javax.persistence.Embedded

@Embeddable
data class IntervalValue(

        var interval: Int = -1,

        var value: Double = 0.0,

        @Embedded
        var intervalQuality: IntervalQuality = IntervalQuality(Quality.A)
) {

    override fun toString(): String {
        return "IntervalValue(interval=$interval, value=$value, intervalQuality=$intervalQuality)"
    }
}
