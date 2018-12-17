// Tommy Li (tommy.li@firefire.co), 2018-03-12

package co.firefire.fenergy.shared.service

import co.firefire.fenergy.shared.domain.Login
import co.firefire.fenergy.shared.domain.LoginNmi
import co.firefire.fenergy.shared.repository.LoginNmiRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

interface LoginNmiService {

    fun findAllByLogin(login: Login): Collection<LoginNmi>

}

@Transactional
@Service
class LoginNmiServiceImpl(val loginNmiRepo: LoginNmiRepository) : LoginNmiService {

    override fun findAllByLogin(login: Login): Collection<LoginNmi> {
        return loginNmiRepo.findAllByLogin(login)
    }

}
