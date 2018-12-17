// Tommy Li (tommy.li@firefire.co), 2017-02-20

package co.firefire.fenergy.shared.domain

enum class IntervalLength(val minute: Int) {

    IL_1(1),
    IL_5(5),
    IL_10(10),
    IL_15(15),
    IL_30(30);

    companion object Factory {
        fun fromMinute(minute: Int): IntervalLength {
            return IntervalLength.values().find { it.minute == minute } ?: throw DomainException("Given value [$minute] is not a valid IntervalLength")
        }
    }
}
