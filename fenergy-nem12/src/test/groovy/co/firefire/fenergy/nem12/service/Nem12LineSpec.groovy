// Tommy Li (tommy.li@firefire.co), 2017-05-30

package co.firefire.fenergy.nem12.service

import co.firefire.fenergy.nem12.domain.IntervalDay
import co.firefire.fenergy.nem12.domain.IntervalQuality
import co.firefire.fenergy.nem12.domain.Login
import co.firefire.fenergy.nem12.domain.LoginNmi
import co.firefire.fenergy.nem12.domain.NmiMeterRegister
import co.firefire.fenergy.nem12.domain.Quality
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

import static co.firefire.fenergy.nem12.TestUtils.dt

class Nem12LineSpec extends Specification {

  Nem12Line underTest
  Nem12ParserContext mockParserContext = Mock(Nem12ParserContext)
  ErrorCollector mockErrorCollector = Mock(ErrorCollector)
  Login login = new Login(username: 'user')

  @Shared
  def validValues = ['.194', '.188', '.225', '.263', '.181', '.175', '.156', '.169', '.15', '.169', '.15', '.169', '.15', '.163', '.156', '.194', '.138', '.15', '.131', '.15', '.138', '.144', '.15', '.144', '.144', '.138', '.15', '.125', '.138', '.125', '.144', '.125', '.138', '.131', '.138', '.131', '.138', '.138', '.125', '.144', '.119', '.144', '.125', '.144', '.125', '.163', '.244', '.194']

  def setup() {
    mockParserContext.getCurrentLogin() >> login
    mockParserContext.getProcessedResults() >> []
  }

  def 'Valid 200 record is parsed correctly'() {
    given:
    underTest = new Nem12Line(2, new Nem12RecordType.Record200(), ['200', '6408091979', 'E1', 'E1', 'E1', '', '1236594', 'KWH', '30', ''])
    def loginNmi = new LoginNmi(login, '6408091979')
    def expNmiMeterRegister = new NmiMeterRegister(loginNmi, '1236594', 'E1', 'E1')

    when:
    underTest.handleLine(mockParserContext, mockErrorCollector)

    then:
    1 * mockParserContext.mergeNmiMeterRegisterResult()

    then:
    1 * mockParserContext.updateCurrentNmiMeterRegister({ it == expNmiMeterRegister && it.nmiConfig == 'E1' && it.mdmDataStreamId == null && it.nextScheduledReadDate == null })
  }

  @Unroll
  def 'Errors in 200 record are handled correctly'() {
    given:
    underTest = new Nem12Line(2, new Nem12RecordType.Record200(), lineItems)

    when:
    underTest.handleLine(mockParserContext, mockErrorCollector)

    then:
    0 * mockParserContext.updateCurrentNmiMeterRegister(_)
    1 * mockErrorCollector.addError(expError)
    1 * mockParserContext.mergeNmiMeterRegisterResult()

    where:
    lineItems                                                               | expError
    ['']                                                                    | 'co.firefire.nem12.api.DomainException: Error parsing nmi, position 1, line 2, exception: java.lang.IndexOutOfBoundsException: Index: 1, Size: 1, cause: null'
    ['200', '', 'E1', 'E1', 'E1', '', '1236594', 'KWH', '30', '']           | 'co.firefire.nem12.api.DomainException: Error parsing nmi, position 1, line 2, exception: co.firefire.nem12.api.DomainException: nmi is mandatory and cannot be blank, cause: null'
    ['200', '6408091979', 'E1', '', 'E1', '', '1236594', 'KWH', '30', '']   | 'co.firefire.nem12.api.DomainException: Error parsing registerId, position 3, line 2, exception: co.firefire.nem12.api.DomainException: registerId is mandatory and cannot be blank, cause: null'
    ['200', '6408091979', 'E1', 'E1', '', '', '1236594', 'KWH', '30', '']   | 'co.firefire.nem12.api.DomainException: Error parsing nmiSuffix, position 4, line 2, exception: co.firefire.nem12.api.DomainException: nmiSuffix is mandatory and cannot be blank, cause: null'
    ['200', '6408091979', 'E1', 'E1', 'E1', '', '', 'KWH', '30', '']        | 'co.firefire.nem12.api.DomainException: Error parsing meterSerial, position 6, line 2, exception: co.firefire.nem12.api.DomainException: meterSerial is mandatory and cannot be blank, cause: null'
    ['200', '6408091979', 'E1', 'E1', 'E1', '', '1236594', '', '30', '']    | 'co.firefire.nem12.api.DomainException: Error parsing uom, position 7, line 2, exception: co.firefire.nem12.api.DomainException: uom is mandatory and cannot be blank, cause: null'
    ['200', '6408091979', 'E1', 'E1', 'E1', '', '1236594', 'KW', '30', '']  | 'co.firefire.nem12.api.DomainException: Error parsing uom, position 7, line 2, exception: java.lang.IllegalArgumentException: No enum constant co.firefire.nem12.api.domain.UnitOfMeasure.KW, cause: null'
    ['200', '6408091979', 'E1', 'E1', 'E1', '', '1236594', 'KWH', '', '']   | 'co.firefire.nem12.api.DomainException: Error parsing intervalLength, position 8, line 2, exception: co.firefire.nem12.api.DomainException: intervalLength is mandatory and cannot be blank, cause: null'
    ['200', '6408091979', 'E1', 'E1', 'E1', '', '1236594', 'KWH', '20', ''] | 'co.firefire.nem12.api.DomainException: Error parsing intervalLength, position 8, line 2, exception: co.firefire.nem12.api.DomainException: Given value [20] is not a valid IntervalLength, cause: null'
  }

  def 'Valid 300 record is parsed correctly'() {
    given:
    def loginNmi = new LoginNmi(login, '6408091979')
    NmiMeterRegister nmiMeterRegister = new NmiMeterRegister(loginNmi, '1236594', 'E1', 'E1')
    mockParserContext.getCurrentNmiMeterRegister() >> nmiMeterRegister
    underTest = new Nem12Line(3, new Nem12RecordType.Record300(), ['300', '20141126'] + validValues + ['A', '', '', '20141127021242', ''])
    def expIntervalDay = new IntervalDay(nmiMeterRegister, dt('2014-11-26'), new IntervalQuality(Quality.A))

    when:
    underTest.handleLine(mockParserContext, mockErrorCollector)

    then:
    1 * mockParserContext.updateCurrentIntervalDay(expIntervalDay)
  }

  @Unroll
  def 'Errors in 300 record are handled correctly'() {
    given:
    def loginNmi = new LoginNmi(login, '6408091979')
    NmiMeterRegister nmiMeterRegister = new NmiMeterRegister(loginNmi, '1236594', 'E1', 'E1')
    underTest = new Nem12Line(3, new Nem12RecordType.Record300(), lineItems)

    when:
    mockParserContext.getCurrentNmiMeterRegister() >> nmiMeterRegister
    underTest.handleLine(mockParserContext, mockErrorCollector)

    then:
    true
    1 * mockErrorCollector.addError(expError)

    where:
    lineItems << [
      ['300', ''] + validValues + ['A', '', '', '20141127021242', ''],
      ['300', 'abc'] + validValues + ['A', '', '', '20141127021242', ''],
      ['300', '20170101'] + validValues + ['', '', '', '20141127021242', ''],
      ['300', '20170101'] + validValues + ['B', '', '', '20141127021242', '']
    ]
    expError << [
      'co.firefire.nem12.api.DomainException: Error parsing intervalDate, position 1, line 3, exception: co.firefire.nem12.api.DomainException: intervalDate is mandatory and cannot be blank, cause: null',
      'co.firefire.nem12.api.DomainException: Error parsing intervalDate, position 1, line 3, exception: java.time.format.DateTimeParseException: Text \'abc\' could not be parsed at index 0, cause: null',
      'co.firefire.nem12.api.DomainException: Error parsing intervalQuality, position 50, line 3, exception: co.firefire.nem12.api.DomainException: intervalQuality is mandatory and cannot be blank, cause: null',
      'co.firefire.nem12.api.DomainException: Error parsing intervalQuality, position 50, line 3, exception: java.lang.IllegalArgumentException: No enum constant co.firefire.nem12.api.domain.Quality.B, cause: null'
    ]
  }
}
