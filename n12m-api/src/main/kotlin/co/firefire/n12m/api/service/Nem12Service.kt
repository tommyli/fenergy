// Tommy Li (tommy.li@firefire.co), 2017-07-03

package co.firefire.n12m.api.service

import co.firefire.n12m.api.domain.Login
import co.firefire.n12m.api.domain.LoginNmi
import org.springframework.core.io.Resource

interface Nem12Service {

    fun uploadNem12(login: Login, nem12Resource: Resource): Collection<LoginNmi>

}

class Nem12ServiceImpl : Nem12Service {


    override fun uploadNem12(login: Login, nem12Resource: Resource): Collection<LoginNmi> {
        val nmiMeterRegisters = Nem12ParserImpl(login).parseNem12Resource(nem12Resource)

        return arrayListOf()
    }
}
