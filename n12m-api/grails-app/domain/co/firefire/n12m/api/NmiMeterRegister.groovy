// Tommy Li (tommy.li@firefire.co), 2017-02-20

package co.firefire.n12m.api

import java.time.LocalDate

class NmiMeterRegister implements Serializable {

  String nmi
  String meterSerial
  String registerId
  String nmiSuffix
  String nmiConfig
  String mdmDataStreamId
  UnitOfMeasure uom
  IntervalLength intervalLength
  LocalDate nextScheduledReadDate
  SortedSet<IntervalDay> intervalDays = new TreeSet<>()

  static constraints = {
    nmi(unique: ['meterSerial', 'registerId', 'nmiSuffix'])
    nmiConfig nullable: true
    mdmDataStreamId nullable: true
  }

  static mapping = {
    id generator: 'increment'
    intervalDays cascade: 'all-delete-orphan', fetch: 'join'
  }

  static hasMany = [intervalDays: IntervalDay]

}
