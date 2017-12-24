// Tommy Li (tommy.li@firefire.co), 2017-07-03

package co.firefire.fenergy.nem12.repository

import co.firefire.fenergy.nem12.domain.Login
import org.springframework.data.repository.PagingAndSortingRepository

interface LoginRepository : PagingAndSortingRepository<Login, Long> {

    fun findByUsername(username: String): Login?

    fun findByEmail(email: String): Login?

    fun findByEmailOrUsername(email: String, username: String): Login?

}
