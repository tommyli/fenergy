// Tommy Li (tommy.li@firefire.co), 2017-02-25

package co.firefire.n12m.api

import java.time.LocalDate
import java.time.format.DateTimeFormatter

class TestUtils {

  static LocalDate dt(String dateStr) {
    LocalDate.parse(dateStr, DateTimeFormatter.ISO_DATE)
  }
}
