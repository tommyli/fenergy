// Tommy Li (tommy.li@firefire.co), 2017-06-03

package co.firefire.fenergy.nem12.domain

import spock.lang.Specification
import spock.lang.Unroll

import static co.firefire.fenergy.nem12.TestUtils.dts

class TimestampedQualitySpec extends Specification {

  TimestampedQuality underTest

  @Unroll
  def '#left compareTo(#right) equals #expResult'() {
    expect:
    TimestampedQuality underTest = left
    TimestampedQuality other = right
    underTest.compareTo(other) == expResult

    where:
    left                                                          | right                                                         | expResult
    new TimestampedQuality(Quality.A, dts('2017-01-01 20:28:28')) | new TimestampedQuality(Quality.A, dts('2017-01-01 20:28:28')) | 0
    new TimestampedQuality(Quality.A, dts('2017-01-01 20:28:27')) | new TimestampedQuality(Quality.A, dts('2017-01-01 20:28:28')) | -1
    new TimestampedQuality(Quality.A, dts('2017-01-01 20:28:29')) | new TimestampedQuality(Quality.A, dts('2017-01-01 20:28:28')) | 1
    new TimestampedQuality(Quality.A, dts('2017-01-01 20:28:28')) | new TimestampedQuality(Quality.E, dts('2017-01-01 20:28:28')) | 3
    new TimestampedQuality(Quality.A, dts('2017-01-01 20:28:27')) | new TimestampedQuality(Quality.E, dts('2017-01-01 20:28:28')) | 3
    new TimestampedQuality(Quality.E, dts('2017-01-01 20:28:28')) | new TimestampedQuality(Quality.A, dts('2017-01-01 20:28:28')) | -3
    new TimestampedQuality(Quality.E, dts('2017-01-01 20:28:29')) | new TimestampedQuality(Quality.A, dts('2017-01-01 20:28:28')) | -3
  }
}
