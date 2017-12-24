// Tommy Li (tommy.li@firefire.co), 2017-06-03

package co.firefire.fenergy.nem12.domain

import spock.lang.Specification
import spock.lang.Unroll

class NmiMeterRegisterSpec extends Specification {

  NmiMeterRegister underTest

  @Unroll
  def 'intervalLength (#intervalLength) yields intervalRange (#expResult)'() {
    given:
    underTest = new NmiMeterRegister()

    underTest.intervalLength = intervalLength

    expect:
    underTest.intervalRange().toString() == expResult

    where:
    intervalLength       | expResult
    IntervalLength.IL_15 | '1..96'
    IntervalLength.IL_30 | '1..48'
  }
}
