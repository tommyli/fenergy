// Tommy Li (tommy.li@firefire.co), 2017-05-20

package co.firefire.n12m.api

import org.springframework.data.repository.Repository

interface NmiMeterRegisterRepository : Repository<NmiMeterRegister, Long> {

    fun findOne(id: Long): NmiMeterRegister?

    fun findByNmiAndMeterSerialAndRegisterIdAndNmiSuffix(nmi: String, meterSerial: String, registerId: String, nmiSuffix: String): NmiMeterRegister?

    fun findAllByNmi(nmi: String): Collection<NmiMeterRegister>

    fun findAll(): Collection<NmiMeterRegister>

    fun save(toSave: NmiMeterRegister): NmiMeterRegister?

}
