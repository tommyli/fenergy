// Tommy Li (tommy.li@firefire.co), 2017-02-20

package co.firefire.n12m.api

import javax.annotation.Resource

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import spock.lang.Specification

import static co.firefire.n12m.api.TestUtils.dt

@Transactional
@SpringBootTest
class NmiMeterRegisterRepositoryIntegrationSpec extends Specification {

  @Resource
  NmiMeterRegisterRepository underTest

  def 'findByNmiAndMeterSerialAndRegisterIdAndNmiSuffix() returns correct results'() {
    when:
    NmiMeterRegister actualResult = underTest.findByNmiAndMeterSerialAndRegisterIdAndNmiSuffix('6123456789', 'ABCD-12345', 'E1', 'E1')

    then:
    actualResult.nmiConfig == 'E1E1'
    actualResult.mdmDataStreamId == null
    actualResult.uom == UnitOfMeasure.KWH
    actualResult.intervalLength == IntervalLength.IL_30
    actualResult.nextScheduledReadDate == dt('2017-02-23')
    !actualResult.intervalDays.isEmpty()

    and:
    IntervalDay actualDay20170101 = actualResult.getDay(dt('2017-01-01'))
    actualDay20170101.intervalEvents.isEmpty()

    and:
    IntervalDay actualDay20170102 = actualResult.getDay(dt('2017-01-02'))
    !actualDay20170102.intervalEvents.isEmpty()

    IntervalDay actualDay20170103 = actualResult.getDay(dt('2017-01-03'))
    !actualDay20170103.intervalEvents.isEmpty()
  }

  def 'New NmiMeterRegister saves correctly'() {
    given:
    NmiMeterRegister nmiMeterRegister = new NmiMeterRegister(nmi: '6123456789', meterSerial: '123456', registerId: 'E1', nmiSuffix: 'E1', uom: UnitOfMeasure.KWH, intervalLength: IntervalLength.IL_30, nextScheduledReadDate: dt('2017-02-23'))

    when:
    NmiMeterRegister actualResult = underTest.save(nmiMeterRegister)

    then:
    actualResult.nmi == '6123456789'
  }
}
