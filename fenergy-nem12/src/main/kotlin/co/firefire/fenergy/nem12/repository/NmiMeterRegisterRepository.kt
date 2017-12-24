// Tommy Li (tommy.li@firefire.co), 2017-05-20

package co.firefire.fenergy.nem12.repository

import co.firefire.fenergy.nem12.domain.LoginNmi
import co.firefire.fenergy.nem12.domain.NmiMeterRegister
import org.springframework.data.repository.PagingAndSortingRepository

interface NmiMeterRegisterRepository : PagingAndSortingRepository<NmiMeterRegister, Long> {

    fun findByLoginNmiAndMeterSerialAndRegisterIdAndNmiSuffix(loginNmi: LoginNmi, meterSerial: String, registerId: String, nmiSuffix: String): NmiMeterRegister?

    fun findAllByLoginNmi(loginNmi: LoginNmi): Collection<NmiMeterRegister>

}
