// Tommy Li (tommy.li@firefire.co), 2017-02-20

package co.firefire.n12m.api

import java.time.LocalDate

class NmiMeterRegister implements Serializable {

  static constraints = {
//    nmi unique: ['meterSerial', 'registerId', 'nmiSuffix']
    nmiConfig nullable: true
    mdmDataStreamId nullable: true
  }

  static mapping = {
    id generator: 'increment'
  }

  String nmi
  String meterSerial
  String registerId
  String nmiSuffix
  String nmiConfig
  String mdmDataStreamId
  UnitOfMeasure uom
  IntervalLength intervalLength
  LocalDate nextScheduledReadDate
  
}
