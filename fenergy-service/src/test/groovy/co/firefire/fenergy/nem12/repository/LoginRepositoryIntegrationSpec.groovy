// Tommy Li (tommy.li@firefire.co), 2017-07-04

package co.firefire.fenergy.nem12.repository

import co.firefire.fenergy.nem12.DataJpaTest
import co.firefire.fenergy.shared.domain.Login
import co.firefire.fenergy.shared.repository.LoginRepository
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@DataJpaTest
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
