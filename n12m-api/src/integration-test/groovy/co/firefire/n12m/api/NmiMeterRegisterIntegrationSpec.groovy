// Tommy Li (tommy.li@firefire.co), 2017-02-20

package co.firefire.n12m.api

import grails.test.mixin.integration.Integration
import grails.transaction.Rollback
import grails.transaction.Transactional
import spock.lang.Specification

import static co.firefire.n12m.api.TestUtils.dt

@Integration
@Transactional
class NmiMeterRegisterIntegrationSpec extends Specification {

  NmiMeterRegister underTest

  @Rollback
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
