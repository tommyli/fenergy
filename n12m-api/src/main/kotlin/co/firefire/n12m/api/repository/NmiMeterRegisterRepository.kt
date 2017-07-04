// Tommy Li (tommy.li@firefire.co), 2017-05-20

package co.firefire.n12m.api.repository

import co.firefire.n12m.api.domain.NmiMeterRegister
import org.springframework.data.repository.PagingAndSortingRepository

interface NmiMeterRegisterRepository : PagingAndSortingRepository<NmiMeterRegister, Long> {

    fun findByNmiAndMeterSerialAndRegisterIdAndNmiSuffix(nmi: String, meterSerial: String, registerId: String, nmiSuffix: String): NmiMeterRegister?

    fun findAllByNmi(nmi: String): Collection<NmiMeterRegister>

}
