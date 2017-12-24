// Tommy Li (tommy.li@firefire.co), 2017-03-10

package co.firefire.fenergy.nem12.domain

import javax.persistence.Embedded

data class IntervalEvent(

        var intervalRange: IntRange = 0..0,

        @Embedded
        var intervalQuality: IntervalQuality = IntervalQuality(Quality.A)

) {

    override fun toString(): String {
        return "IntervalEvent(intervalRange=$intervalRange, intervalQuality=$intervalQuality)"
    }
}
