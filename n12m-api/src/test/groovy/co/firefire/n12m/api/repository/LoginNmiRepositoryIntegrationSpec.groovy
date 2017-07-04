// Tommy Li (tommy.li@firefire.co), 2017-07-04

package co.firefire.n12m.api.repository

import co.firefire.n12m.api.domain.Login
import co.firefire.n12m.api.domain.LoginNmi
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import spock.lang.Specification

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
}
