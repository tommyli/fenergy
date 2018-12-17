// Tommy Li (tommy.li@firefire.co), 2017-07-04

package co.firefire.fenergy.nem12.repository

import co.firefire.fenergy.nem12.domain.IntervalDay
import co.firefire.fenergy.nem12.domain.IntervalQuality
import co.firefire.fenergy.nem12.domain.NmiMeterRegister
import co.firefire.fenergy.nem12.domain.Quality
import co.firefire.fenergy.shared.domain.Login
import co.firefire.fenergy.shared.domain.LoginNmi
import co.firefire.fenergy.shared.repository.LoginNmiRepository
import co.firefire.fenergy.shared.repository.LoginRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import spock.lang.Specification

import static co.firefire.fenergy.nem12.TestUtils.dt

@Transactional
@SpringBootTest
class LoginNmiRepositoryIntegrationSpec extends Specification {

  @Autowired
  LoginRepository loginRepo

  @Autowired
  LoginNmiRepository underTest

  def 'findByLoginAndNmi() returns correct results'() {
    when:
    Login login = loginRepo.findByUsername('tommy.li@firefire.co')
    LoginNmi acutalResult = underTest.findByLoginAndNmi(login, '6123456789')

    then:
    acutalResult == underTest.findByLoginUsernameAndNmi('tommy.li@firefire.co', '6123456789')
    acutalResult.label == 'Home'
    acutalResult.login == login
  }

  def 'save() correctly persists to DB'() {
    given:
    Login login = loginRepo.findByUsername('tommy.li@firefire.co')
    LoginNmi loginNmi = new LoginNmi(login, '4123456789')
    loginNmi.label = 'test label'
    NmiMeterRegister nmiMeterRegister = new NmiMeterRegister(loginNmi, 'test serial', '1', 'E1')
    IntervalDay intervalDay = new IntervalDay(nmiMeterRegister, dt('2017-02-02'), new IntervalQuality(Quality.A))
    nmiMeterRegister.mergeIntervalDay(intervalDay)
    loginNmi.addNmiMeterRegister(nmiMeterRegister)

    when:
    LoginNmi actualResult = underTest.save(loginNmi)

    then:
    actualResult == loginNmi
    noExceptionThrown()
  }
}
