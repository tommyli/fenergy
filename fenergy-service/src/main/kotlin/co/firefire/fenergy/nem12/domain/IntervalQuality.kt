// Tommy Li (tommy.li@firefire.co), 2017-03-10

package co.firefire.fenergy.nem12.domain

import java.time.LocalDateTime
import javax.persistence.Embeddable
import javax.persistence.EnumType
import javax.persistence.Enumerated

/**
 * Order of declaration matters here, it determines which intervalQuality takes precedense
 */
enum class Quality {
    V,
    N,
    E,
    S,
    F,
    A
}

@Embeddable
data class IntervalQuality(
        @Enumerated(EnumType.STRING)
        val quality: Quality = Quality.A
) {

    constructor(quality: Quality, method: String?, reasonCode: String?, reasonDescription: String?) : this(quality) {
        this.method = method
        this.reasonCode = reasonCode
        this.reasonDescription = reasonDescription
    }

    var method: String? = null
    var reasonCode: String? = null
    var reasonDescription: String? = null

    override fun toString(): String {
        return "IntervalQuality(intervalQuality=$quality)"
    }
}

data class TimestampedQuality(val quality: Quality, val timestamp: LocalDateTime) : Comparable<TimestampedQuality> {

    override fun compareTo(other: TimestampedQuality): Int {
        return if (quality.compareTo(other.quality) == 0) {
            if (timestamp < other.timestamp) {
                -1
            } else if (timestamp == other.timestamp) {
                0
            } else {
                1
            }
        } else {
            quality.compareTo(other.quality)
        }
    }
}
