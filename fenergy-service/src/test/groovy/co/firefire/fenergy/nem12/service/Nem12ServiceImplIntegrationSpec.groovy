// Tommy Li (tommy.li@firefire.co), 2017-07-05

package co.firefire.fenergy.nem12.service

import co.firefire.fenergy.nem12.DataJpaTest
import co.firefire.fenergy.nem12.domain.NmiMeterRegister
import co.firefire.fenergy.nem12.repository.NmiMeterRegisterRepository
import co.firefire.fenergy.shared.domain.Login
import co.firefire.fenergy.shared.domain.LoginNmi
import co.firefire.fenergy.shared.repository.LoginNmiRepository
import co.firefire.fenergy.shared.repository.LoginRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.ClassPathResource
import org.springframework.core.io.Resource
import spock.lang.Specification

@DataJpaTest
class Nem12ServiceImplIntegrationSpec extends Specification {

  @Autowired
  Nem12Service underTest

  @Autowired
  LoginRepository loginRepo

  @Autowired
  LoginNmiRepository loginNmiRepo

  @Autowired
  NmiMeterRegisterRepository nmrRepo

  def 'uploadNem12 correctly parses resource and persists results'() {
    given:
    Login login = loginRepo.findByUsername('tommy.li@firefire.co')
    Resource nem12FileResource = new ClassPathResource('6408091979_20141126_20161126_20161127103000_UNITEDENERGY.csv')

    when:
    underTest.uploadNem12(login, nem12FileResource)

    then:
    LoginNmi loginNmi = loginNmiRepo.findByLoginAndNmi(login, '6408091979')
    Iterable<NmiMeterRegister> nmrs1 = nmrRepo.findAllByLoginNmi(loginNmi)
    nmrs1.size() == 2
    nmrs1.every { it.intervalDays.values().every { it.intervalValues.size() == 48 } }

    when:
    underTest.uploadNem12(login, nem12FileResource)

    then:
    Iterable<NmiMeterRegister> nmrs2 = nmrRepo.findAllByLoginNmi(loginNmi)
    nmrs2.size() == 2
    nmrs2.every { it.intervalDays.values().every { it.intervalValues.size() == 48 } }
  }
}
