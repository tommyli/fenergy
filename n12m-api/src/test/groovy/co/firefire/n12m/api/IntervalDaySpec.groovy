// Tommy Li (tommy.li@firefire.co), 2017-03-15

package co.firefire.n12m.api

import java.time.LocalDate

import spock.lang.Specification

class IntervalDaySpec extends Specification {

  NmiMeterRegister nmiMeterRegister = new NmiMeterRegister()

  IntervalDay underTest

  def 'getVolumes() parses values correct'() {
    given:
    nmiMeterRegister.intervalLength = IntervalLength.IL_30
    underTest = new IntervalDay(-1, nmiMeterRegister, LocalDate.MIN, new IntervalQuality(Quality.A))
    underTest.values = '1,2,-3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,-47,48'

    when:
    Map<Integer, BigDecimal> actualResult = underTest.getIntervalVolumes()

    then:
    actualResult == [
      1 : 1,
      2 : 2,
      3 : -3,
      4 : 4,
      5 : 5,
      6 : 6,
      7 : 7,
      8 : 8,
      9 : 9,
      10: 10,
      11: 11,
      12: 12,
      13: 13,
      14: 14,
      15: 15,
      16: 16,
      17: 17,
      18: 18,
      19: 19,
      20: 20,
      21: 21,
      22: 22,
      23: 23,
      24: 24,
      25: 25,
      26: 26,
      27: 27,
      28: 28,
      29: 29,
      30: 30,
      31: 31,
      32: 32,
      33: 33,
      34: 34,
      35: 35,
      36: 36,
      37: 37,
      38: 38,
      39: 39,
      40: 40,
      41: 41,
      42: 42,
      43: 43,
      44: 44,
      45: 45,
      46: 46,
      47: -47,
      48: 48
    ]
  }
}
