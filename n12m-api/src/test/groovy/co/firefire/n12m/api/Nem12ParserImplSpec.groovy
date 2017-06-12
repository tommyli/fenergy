// Tommy Li (tommy.li@firefire.co), 2017-05-28

package co.firefire.n12m.api

import org.springframework.core.io.ClassPathResource
import org.springframework.core.io.Resource
import spock.lang.Specification

import static co.firefire.n12m.api.TestUtils.dt

class Nem12ParserImplSpec extends Specification {

  Nem12Parser underTest = new Nem12ParserImpl()

  def 'Nem12ParserImpl parses valid NEM12 file correctly'() {
    given: 'NEM12 file'
    Resource nem12FileResource = new ClassPathResource('6408091979_20141126_20161126_20161127103000_UNITEDENERGY.csv')

    when:
    Collection<NmiMeterRegister> actualResult = underTest.parseNem12Resource(nem12FileResource)
    NmiMeterRegister e1 = actualResult.find { it.nmiSuffix == 'E1' }
    NmiMeterRegister b1 = actualResult.find { it.nmiSuffix == 'B1' }

    then:
    actualResult.size() == 2

    e1.intervalDays.collectEntries { k, v -> [(k): v.intervalValues.values().sum { it.value }] } == [
      (dt('2014-11-26')): 7.427,
      (dt('2015-04-20')): 7.440,
      (dt('2015-04-21')): 8.194,
      (dt('2015-04-22')): 8.890,
      (dt('2015-04-23')): 6.738,
      (dt('2016-11-26')): 0.314
    ]
    b1.intervalDays.collectEntries { k, v -> [(k): v.intervalValues.values().sum { it.value }] } == [
      (dt('2015-04-22')): -0.100,
      (dt('2015-04-23')): -9.085,
      (dt('2015-04-24')): -2.119,
      (dt('2016-11-26')): -10.433]
  }
}
