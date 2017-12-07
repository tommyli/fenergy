// Tommy Li (tommy.li@firefire.co), 2017-07-04

package co.firefire.n12m.api.repository

import co.firefire.n12m.api.domain.Login
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import spock.lang.Specification

@Transactional
@SpringBootTest
class LoginRepositoryIntegrationSpec extends Specification {

  @Autowired
  LoginRepository underTest

  def 'findByUsername() returns correct results'() {
    when:
    Login acutalResult = underTest.findByUsername('tommy.li@firefire.co')

    then:
    acutalResult.email == 'tommy.li@firefire.co'
    acutalResult.name == 'Tommy Li'
    acutalResult.pictureUrl == null
    acutalResult.locale == null
    acutalResult.familyName == 'Li'
    acutalResult.givenName == 'Tommy'
  }
}
