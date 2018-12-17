// Tommy Li (tommy.li@firefire.co), 2017-07-03

package co.firefire.fenergy.shared.repository

import co.firefire.fenergy.shared.domain.Login
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.transaction.annotation.Transactional

@Transactional
interface LoginRepository : PagingAndSortingRepository<Login, Long> {

    fun findByUsername(username: String): Login?

    fun findByEmail(email: String): Login?

    fun findByEmailOrUsername(email: String, username: String): Login?

}
