// Tommy Li (tommy.li@firefire.co), 2017-07-03

package co.firefire.n12m.api

import java.time.format.DateTimeFormatter
import java.util.regex.Pattern

val NEM12_DELIMITER: Pattern = ",".toPattern()
val DEFAULT_DATE_TIME_FORMATTER: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdHHmmss")
