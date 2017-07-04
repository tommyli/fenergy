// Tommy Li (tommy.li@firefire.co), 2017-07-03

package co.firefire.n12m.api.repository

import co.firefire.n12m.api.domain.Login
import org.springframework.data.repository.PagingAndSortingRepository

interface LoginRepository : PagingAndSortingRepository<Login, Long> {

    fun findByUsername(username: String): Login?

    fun findByEmail(email: String): Login?

}
