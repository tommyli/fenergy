// Tommy Li (tommy.li@firefire.co), 2017-07-04

package co.firefire.n12m.api.repository

import co.firefire.n12m.api.domain.IntervalDay
import co.firefire.n12m.api.domain.IntervalQuality
import co.firefire.n12m.api.domain.Login
import co.firefire.n12m.api.domain.LoginNmi
import co.firefire.n12m.api.domain.NmiMeterRegister
import co.firefire.n12m.api.domain.Quality
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import spock.lang.Specification

import static co.firefire.n12m.api.TestUtils.dt

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
