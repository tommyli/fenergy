// Tommy Li (tommy.li@firefire.co), 2017-05-20

package co.firefire.fenergy.nem12.repository

import co.firefire.fenergy.nem12.domain.NmiMeterRegister
import co.firefire.fenergy.shared.domain.LoginNmi
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.transaction.annotation.Transactional

@Transactional
interface NmiMeterRegisterRepository : PagingAndSortingRepository<NmiMeterRegister, Long> {

    fun findByLoginNmiAndMeterSerialAndRegisterIdAndNmiSuffix(loginNmi: LoginNmi, meterSerial: String, registerId: String, nmiSuffix: String): NmiMeterRegister?

    fun findAllByLoginNmi(loginNmi: LoginNmi): Collection<NmiMeterRegister>

}
