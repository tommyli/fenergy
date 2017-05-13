// Tommy Li (tommy.li@firefire.co), 2017-03-15

package co.firefire.n12m.api

import grails.test.mixin.TestFor
import org.springframework.validation.Errors
import spock.lang.Specification

import static co.firefire.n12m.api.TestUtils.dt

@TestFor(IntervalDay)
class IntervalDaySpec extends Specification {

  NmiMeterRegister nmiMeterRegister = new NmiMeterRegister(intervalLength: IntervalLength.IL_30)

  IntervalDay underTest

  def 'getVolumes() parses values correct'() {
    given:
    underTest = new IntervalDay(nmiMeterRegister: nmiMeterRegister, intervalDate: dt('2017-02-23'), qualityMethod: QualityMethod.A)
    underTest.values = '1,2,-3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,-47,48'

    when:
    Map<Integer, BigDecimal> actualResult = underTest.getVolumes()

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

  def 'valid values pass validation'() {
    given:
    underTest = new IntervalDay(nmiMeterRegister: nmiMeterRegister, intervalDate: dt('2017-02-23'), qualityMethod: QualityMethod.A)
    underTest.values = '1,-2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,-47,48'

    when:
    Boolean actualResult = underTest.validate()
    Errors errors = underTest.errors

    then:
    actualResult
    errors.allErrors.empty
  }

  def 'invalid values count fails validation'() {
    given:
    underTest = new IntervalDay(nmiMeterRegister: nmiMeterRegister, intervalDate: dt('2017-02-23'), qualityMethod: QualityMethod.A)
    underTest.values = '1,2,3'

    when:
    Boolean actualResult = underTest.validate()
    Errors errors = underTest.errors

    then:
    !actualResult
    errors['values'].code == 'intervalDay.values.invalid.count'
  }

  def 'values containing non numbers fails validation'() {
    given:
    underTest = new IntervalDay(nmiMeterRegister: nmiMeterRegister, intervalDate: dt('2017-02-23'), qualityMethod: QualityMethod.A)
    underTest.values = '1,2,3,4,5,6,7,8,9.10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,a,48'

    when:
    Boolean actualResult = underTest.validate()
    Errors errors = underTest.errors

    then:
    !actualResult
    errors['values'].code == 'intervalDay.values.invalid.number'
  }
}
