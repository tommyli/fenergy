// Tommy Li (tommy.li@firefire.co), 2017-07-03

package co.firefire.n12m.api.service

import co.firefire.n12m.api.domain.Login
import co.firefire.n12m.api.domain.LoginNmi
import co.firefire.n12m.api.repository.LoginNmiRepository
import org.springframework.core.io.Resource
import org.springframework.stereotype.Service

interface Nem12Service {

    fun uploadNem12(login: Login, nem12Resource: Resource): Collection<LoginNmi>

}

@Service
class Nem12ServiceImpl(val loginNmiRepo: LoginNmiRepository) : Nem12Service {

    override fun uploadNem12(login: Login, nem12Resource: Resource): Collection<LoginNmi> {
        Nem12ParserImpl(login).parseNem12Resource(nem12Resource)

        TODO("Implement LoginNmi persistence")
    }
}
