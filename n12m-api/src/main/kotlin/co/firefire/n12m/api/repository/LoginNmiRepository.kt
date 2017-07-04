// Tommy Li (tommy.li@firefire.co), 2017-07-04

package co.firefire.n12m.api.repository

import co.firefire.n12m.api.domain.Login
import co.firefire.n12m.api.domain.LoginNmi
import org.springframework.data.repository.PagingAndSortingRepository

interface LoginNmiRepository : PagingAndSortingRepository<LoginNmi, Long> {

    fun findByLoginUsernameAndNmi(username: String, nmi: String): LoginNmi?

    fun findByLoginAndNmi(login: Login, nmi: String): LoginNmi?

}
