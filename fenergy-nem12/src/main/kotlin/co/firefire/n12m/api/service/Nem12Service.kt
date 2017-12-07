// Tommy Li (tommy.li@firefire.co), 2017-07-03

package co.firefire.n12m.api.service

import co.firefire.n12m.api.domain.Login
import co.firefire.n12m.api.domain.LoginNmi
import co.firefire.n12m.api.repository.LoginNmiRepository
import org.springframework.core.io.Resource
import org.springframework.stereotype.Service

interface Nem12Service {

    fun uploadNem12(login: Login, nem12Resource: Resource)

}

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
