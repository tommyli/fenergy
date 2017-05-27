// Tommy Li (tommy.li@firefire.co), 2017-03-10

package co.firefire.n12m.api

import javax.persistence.Embeddable
import javax.persistence.EnumType
import javax.persistence.Enumerated

enum class Quality {
    A,
    E,
    F,
    N,
    S,
    V
}

@Embeddable
data class IntervalQuality(
        @Enumerated(EnumType.STRING) val quality: Quality = Quality.A
) {

    constructor(quality: Quality, method: String?, reasonCode: String?, reasonDescription: String?) : this(quality)

    val method: String? = null
    val reasonCode: String? = null
    val reasonDescription: String? = null
}
