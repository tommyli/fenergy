// Tommy Li (tommy.li@firefire.co), 2017-05-20

package co.firefire.fenergy.nem12.repository

import co.firefire.fenergy.nem12.domain.IntervalLength
import javax.persistence.AttributeConverter
import javax.persistence.Converter

@Converter
class IntervalLengthConverter : AttributeConverter<IntervalLength, Int> {

    override fun convertToEntityAttribute(dbData: Int?): IntervalLength? {
        return if (dbData != null) IntervalLength.fromMinute(dbData) else dbData
    }

    override fun convertToDatabaseColumn(attribute: IntervalLength?): Int? {
        return if (attribute != null) attribute.minute else null
    }
}
