// Tommy Li (tommy.li@firefire.co), 2017-02-20

package co.firefire.n12m.api

enum class IntervalLength(val minute: Int) {

    IL_15(15),
    IL_30(30);

    companion object Factory {
        fun fromMinute(minute: Int): IntervalLength {
            return IntervalLength.values().find { it.minute == minute } ?: throw RuntimeException("Given value [$minute] is not a valid IntervalLength")
        }
    }
}
