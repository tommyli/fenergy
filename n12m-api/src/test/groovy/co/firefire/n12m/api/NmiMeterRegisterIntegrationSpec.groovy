// Tommy Li (tommy.li@firefire.co), 2017-02-20

package co.firefire.n12m.api

import spock.lang.Specification

import static co.firefire.n12m.api.TestUtils.dt

class NmiMeterRegisterIntegrationSpec extends Specification {

  NmiMeterRegister underTest

  def 'New NmiMeterRegister saves and updates correctly'() {
    given:
    underTest = new NmiMeterRegister(nmi: '6123456789', meterSerial: '123456', registerId: 'E1', nmiSuffix: 'E1', uom: UnitOfMeasure.KWH, intervalLength: IntervalLength.IL_30, nextScheduledReadDate: dt('2017-02-23'))

    when:
    NmiMeterRegister actualResult = underTest.save(flush: true)

    then:
    actualResult.nmi == '6123456789'

    when:
    actualResult.nextScheduledReadDate = dt('2017-05-29')

    then:
    actualResult.nextScheduledReadDate == dt('2017-05-29')
  }
}
