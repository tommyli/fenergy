// Tommy Li (tommy.li@firefire.co), 2017-05-28

package co.firefire.n12m.api

import org.springframework.core.io.ClassPathResource
import org.springframework.core.io.Resource
import spock.lang.Specification

class Nem12ParserImplSpec extends Specification {

  Nem12Parser underTest = new Nem12ParserImpl()

  def 'Nem12ParserImpl parses valid NEM12 file correctly'() {
    given: 'NEM12 file'
    Resource nem12FileResource = new ClassPathResource('6408091979_20141126_20161126_20161127103000_UNITEDENERGY_DETAILED.csv')

    when:
    underTest.parseNem12Resource(nem12FileResource)

    then:
    notThrown(Throwable)
  }
}
