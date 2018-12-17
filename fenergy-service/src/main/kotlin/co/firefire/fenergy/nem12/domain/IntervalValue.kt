// Tommy Li (tommy.li@firefire.co), 2017-06-03

package co.firefire.fenergy.nem12.domain

import java.io.Serializable
import java.math.BigDecimal
import javax.persistence.Embeddable
import javax.persistence.Embedded
import javax.persistence.EmbeddedId
import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Entity
data class IntervalValue(

        @EmbeddedId
        var id: IntervalKey = IntervalKey(),

        var value: BigDecimal = BigDecimal.ZERO,

        @Embedded
        var intervalQuality: IntervalQuality = IntervalQuality(Quality.A)
) {

    constructor(intervalDay: IntervalDay, interval: Int, value: BigDecimal, intervalQuality: IntervalQuality) : this(IntervalKey(intervalDay, interval), value, intervalQuality)

    var version: Int? = 0

    val intervalDay get() = id.intervalDay

    val interval get() = id.interval

    override fun toString(): String {
        return "IntervalValue(intervalDay=${intervalDay}, interval=${interval}, value=$value, intervalQuality=$intervalQuality)"
    }
}

@Embeddable
data class IntervalKey(

        @ManyToOne(optional = false)
        @JoinColumn(name = "interval_day", referencedColumnName = "id", nullable = false)
        var intervalDay: IntervalDay = IntervalDay(),

        var interval: Int = -1

) : Serializable
