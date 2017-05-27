// Tommy Li (tommy.li@firefire.co), 2017-05-27

package co.firefire.n12m.api

import org.springframework.core.io.Resource

interface Nem12Parser {

    fun parseNem12Resource(resource: Resource)

}

class Nem12ParserImpl : Nem12Parser {

    override fun parseNem12Resource(resource: Resource) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
