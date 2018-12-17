// Tommy Li (tommy.li@firefire.co), 2017-02-25

package co.firefire.fenergy.nem12

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class TestUtils {

  static final DateTimeFormatter DEFAULT_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern('yyyy-MM-dd HH:mm:ss')

  static LocalDate dt(String dateStr) {
    LocalDate.parse(dateStr, DateTimeFormatter.ISO_DATE)
  }

  static LocalDateTime dts(String dateTimeStr) {
    LocalDateTime.parse(dateTimeStr, DEFAULT_DATE_TIME_FORMATTER)
  }
}
