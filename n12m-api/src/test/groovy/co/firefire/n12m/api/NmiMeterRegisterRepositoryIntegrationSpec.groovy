// Tommy Li (tommy.li@firefire.co), 2017-02-20

package co.firefire.n12m.api

import java.time.LocalDateTime

import javax.annotation.Resource

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.Commit
import org.springframework.transaction.annotation.Transactional
import spock.lang.Specification

import static co.firefire.n12m.api.TestUtils.dt
import static co.firefire.n12m.api.TestUtils.dts

@Commit
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
    actualDay20170101.intervalValues.isEmpty()

    and:
    IntervalDay actualDay20170102 = actualResult.getDay(dt('2017-01-02'))
    actualDay20170102.nmiMeterRegister == actualResult
    actualDay20170102.intervalQuality == new IntervalQuality(Quality.V, '50', '501', null)
    actualDay20170102.updateDateTime == dts('2016-12-31 20:28:28')
    actualDay20170102.msatsLoadDateTime == dts('2017-01-01 20:28:28')
    actualDay20170102.intervalValues.values().sum { it.value } == 1238.4

    and:
    IntervalDay actualDay20170103 = actualResult.getDay(dt('2017-01-03'))
    actualDay20170103.nmiMeterRegister == actualResult
    actualDay20170103.intervalQuality == new IntervalQuality(Quality.A, '70', null, null)
    actualDay20170103.updateDateTime == dts('2017-01-01 20:28:28')
    actualDay20170103.msatsLoadDateTime == dts('2017-01-02 20:28:28')
    actualDay20170103.intervalValues.values().sum { it.value } == 1233.6
  }

  def 'New NmiMeterRegister saves correctly with associated IntervalDay and IntervalValue'() {
    given:
    NmiMeterRegister nmiMeterRegister = new NmiMeterRegister(nmi: '6123456789', meterSerial: '123456', registerId: 'E1', nmiSuffix: 'E1', uom: UnitOfMeasure.KWH, intervalLength: IntervalLength.IL_30, nextScheduledReadDate: dt('2017-02-23'))

    when:
    IntervalQuality quality = new IntervalQuality(Quality.A)

    IntervalDay id20170101 = new IntervalDay(nmiMeterRegister, dt('2017-01-01'), quality)
    id20170101.mergeNewIntervalValues([1: new IntervalValue(id20170101, 1, 1.1, quality), 2: new IntervalValue(id20170101, 2, 1.2, quality)], LocalDateTime.now(), null)
    nmiMeterRegister.mergeIntervalDay(id20170101)

    IntervalDay id20170102 = new IntervalDay(nmiMeterRegister, dt('2017-01-02'), quality)
    id20170102.mergeNewIntervalValues([1: new IntervalValue(id20170102, 1, 2.1, quality), 2: new IntervalValue(id20170102, 2, 2.2, quality)], null, LocalDateTime.now())
    nmiMeterRegister.mergeIntervalDay(id20170102)

    NmiMeterRegister actualResult = underTest.save(nmiMeterRegister)
    IntervalDay actualDay20170101 = actualResult.getDay(dt('2017-01-01'))
    IntervalDay actualDay20170102 = actualResult.getDay(dt('2017-01-02'))

    then:
    actualResult.nmi == '6123456789'
    actualDay20170101.nmiMeterRegister == actualResult
    actualDay20170102.nmiMeterRegister == actualResult
  }
}
