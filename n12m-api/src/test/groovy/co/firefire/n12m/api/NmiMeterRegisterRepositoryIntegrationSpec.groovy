// Tommy Li (tommy.li@firefire.co), 2017-02-20

package co.firefire.n12m.api

import javax.annotation.Resource

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import spock.lang.Specification

import static co.firefire.n12m.api.TestUtils.dt
import static co.firefire.n12m.api.TestUtils.dts

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
    actualDay20170101.nmiMeterRegister == actualResult
    actualDay20170101.intervalQuality == new IntervalQuality(Quality.A)
    actualDay20170101.updateDateTime == dts('2016-12-30 20:28:28')
    actualDay20170101.msatsLoadDateTime == dts('2016-12-31 20:28:28')
//    actualDay20170101.values == '0.0,1.1,2.2,3.3,4.4,5.5,6.6,7.7,8.8,9.9,11.0,12.1,13.2,14.3,15.4,16.5,17.6,-18.7,19.8,20.9,22.0,23.1,24.2,25.3,26.4,27.5,28.6,29.7,30.8,31.9,33.0,34.1,35.2,36.3,37.4,-38.5,39.6,40.7,41.8,42.9,44.0,45.1,46.2,47.3,48.4,49.5,50.6,51.7'
//    actualDay20170101.intervalEvents.isEmpty()

    and:
    IntervalDay actualDay20170102 = actualResult.getDay(dt('2017-01-02'))
    actualDay20170102.nmiMeterRegister == actualResult
    actualDay20170102.intervalQuality == new IntervalQuality(Quality.V, '50', '501', null)
    actualDay20170102.updateDateTime == dts('2016-12-31 20:28:28')
    actualDay20170102.msatsLoadDateTime == dts('2017-01-01 20:28:28')
//    actualDay20170102.values == '0.0,-1.2,2.4,3.6,4.8,6.0,7.2,8.4,9.6,10.8,12.0,13.2,14.4,15.6,16.8,18.0,19.2,20.4,21.6,22.8,24.0,25.2,26.4,27.6,28.8,30.0,31.2,32.4,33.6,34.8,36.0,37.2,38.4,39.6,40.8,42.0,43.2,44.4,45.6,46.8,48.0,49.2,50.4,51.6,52.8,54.0,55.2,-56.4'
//    !actualDay20170102.intervalEvents.isEmpty()

    and:
    IntervalDay actualDay20170103 = actualResult.getDay(dt('2017-01-03'))
    actualDay20170103.nmiMeterRegister == actualResult
    actualDay20170103.intervalQuality == new IntervalQuality(Quality.A, '70', null, null)
    actualDay20170103.updateDateTime == dts('2017-01-01 20:28:28')
    actualDay20170103.msatsLoadDateTime == dts('2017-01-02 20:28:28')
//    actualDay20170103.values == '0.0,-1.2,-2.4,3.6,4.8,6.0,7.2,8.4,9.6,10.8,12.0,13.2,14.4,15.6,16.8,18.0,19.2,20.4,21.6,22.8,24.0,25.2,26.4,27.6,28.8,30.0,31.2,32.4,33.6,34.8,36.0,37.2,38.4,39.6,40.8,42.0,43.2,44.4,45.6,46.8,48.0,49.2,50.4,51.6,52.8,54.0,55.2,-56.4'
//    !actualDay20170103.intervalEvents.isEmpty()
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
