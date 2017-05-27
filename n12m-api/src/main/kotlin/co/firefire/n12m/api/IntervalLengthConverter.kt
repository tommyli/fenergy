// Tommy Li (tommy.li@firefire.co), 2017-05-20

package co.firefire.n12m.api

import javax.persistence.AttributeConverter
import javax.persistence.Converter

@Converter
class IntervalLengthConverter : AttributeConverter<IntervalLength, Int> {

    override fun convertToEntityAttribute(dbData: Int?): IntervalLength? {
        dbData?.let {
            return IntervalLength.fromMinute(it)
        }

        return null
    }

    override fun convertToDatabaseColumn(attribute: IntervalLength?): Int? {

        attribute?.let {
            return it.minute
        }

        return null
    }
}
