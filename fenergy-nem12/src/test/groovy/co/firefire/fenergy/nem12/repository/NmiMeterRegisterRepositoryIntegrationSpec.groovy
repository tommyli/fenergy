// Tommy Li (tommy.li@firefire.co), 2017-02-20

package co.firefire.fenergy.nem12.repository

import java.time.LocalDateTime

import co.firefire.fenergy.nem12.domain.IntervalDay
import co.firefire.fenergy.nem12.domain.IntervalLength
import co.firefire.fenergy.nem12.domain.IntervalQuality
import co.firefire.fenergy.nem12.domain.IntervalValue
import co.firefire.fenergy.nem12.domain.LoginNmi
import co.firefire.fenergy.nem12.domain.NmiMeterRegister
import co.firefire.fenergy.nem12.domain.Quality
import co.firefire.fenergy.nem12.domain.UnitOfMeasure
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import spock.lang.Specification

import static co.firefire.fenergy.nem12.TestUtils.dt
import static co.firefire.fenergy.nem12.TestUtils.dts

@Transactional
@SpringBootTest
class NmiMeterRegisterRepositoryIntegrationSpec extends Specification {

  @Autowired
  NmiMeterRegisterRepository underTest

  @Autowired
  LoginNmiRepository loginNmiRepo

  def 'findByNmiAndMeterSerialAndRegisterIdAndNmiSuffix() returns correct results'() {
    when:
    LoginNmi loginNmi = loginNmiRepo.findByLoginUsernameAndNmi('tommy.li@firefire.co', '6123456789')
    NmiMeterRegister actualResult = underTest.findByLoginNmiAndMeterSerialAndRegisterIdAndNmiSuffix(loginNmi, 'ABCD-12345', 'E1', 'E1')

    then:
    actualResult.loginNmi.nmiMeterRegisters.contains(actualResult)
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
    LoginNmi loginNmi = loginNmiRepo.findByLoginUsernameAndNmi('tommy.li@firefire.co', '6123456789')
    NmiMeterRegister nmiMeterRegister = new NmiMeterRegister(loginNmi: loginNmi, meterSerial: '123456', registerId: 'E1', nmiSuffix: 'E1', uom: UnitOfMeasure.KWH, intervalLength: IntervalLength.IL_30, nextScheduledReadDate: dt('2017-02-23'))

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
    actualResult.loginNmi == loginNmi
    actualDay20170101.nmiMeterRegister == actualResult
    actualDay20170102.nmiMeterRegister == actualResult
  }
}
