// Tommy Li (tommy.li@firefire.co), 2017-03-10

package co.firefire.n12m.api

import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime

class IntervalDay {

  static final Long MINUTES_IN_DAY = Duration.ofDays(1).toMinutes()

  NmiMeterRegister nmiMeterRegister
  LocalDate intervalDate
  QualityMethod qualityMethod
  String reasonCode
  String reasonDescription
  LocalDateTime updateDateTime
  LocalDateTime msatsLoadDateTime
  String values

  static constraints = {
    nmiMeterRegister(unique: ['intervalDate'])
    reasonCode nullable: true
    reasonDescription nullable: true
    updateDateTime nullable: true
    msatsLoadDateTime nullable: true

    values validator: { valuesStr, intervalDay ->
      def splittedValues = valuesStr.split(/,/)
      if (!splittedValues.every { it.isBigDecimal() }) {
        return ['intervalDay.values.invalid.number']
      }
      Duration.ofDays(1).toMinutes()
      if (splittedValues.size() != MINUTES_IN_DAY / intervalDay.nmiMeterRegister.intervalLength.minute) {
        return ['intervalDay.values.invalid.count']
      }
    }
  }

  static mapping = {
    id generator: 'sequence', params: ['sequence': 'interval_day_id_seq']
    nmiMeterRegister column: 'nmi_meter_register'
  }

  static belongsTo = [nmiMeterRegister: NmiMeterRegister]

  Map<Integer, BigDecimal> getIntervalVolumes() {
    int i = 1
    values.split(/,/).collectEntries { def value ->
      [(i++): value.toBigDecimal()]
    }
  }
}
