// Tommy Li (tommy.li@firefire.co), 2017-07-03

package co.firefire.fenergy.nem12.service

import co.firefire.fenergy.shared.domain.Login
import co.firefire.fenergy.shared.domain.LoginNmi
import co.firefire.fenergy.shared.repository.LoginNmiRepository
import org.springframework.core.io.Resource
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

interface Nem12Service {

    fun uploadNem12(login: Login, nem12Resource: Resource)

}

@Transactional
@Service
class Nem12ServiceImpl(val loginNmiRepo: LoginNmiRepository) : Nem12Service {

    override fun uploadNem12(login: Login, nem12Resource: Resource) {
        val nmiMeterRegisters = Nem12ParserImpl(login).parseNem12Resource(nem12Resource)
        nmiMeterRegisters.forEach { nmr ->
            val existingLoginNmi = loginNmiRepo.findByLoginAndNmi(nmr.loginNmi.login, nmr.loginNmi.nmi)
            if (existingLoginNmi == null) {
                val newLoginNmi = loginNmiRepo.save(LoginNmi(login, nmr.loginNmi.nmi))
                newLoginNmi.addNmiMeterRegister(nmr)
            } else {
                existingLoginNmi.mergeNmiMeterRegister(nmr)
            }
        }
    }
}
