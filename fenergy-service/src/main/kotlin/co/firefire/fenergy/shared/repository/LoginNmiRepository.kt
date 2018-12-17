// Tommy Li (tommy.li@firefire.co), 2017-07-04

package co.firefire.fenergy.shared.repository

import co.firefire.fenergy.shared.domain.Login
import co.firefire.fenergy.shared.domain.LoginNmi
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.transaction.annotation.Transactional

@Transactional
interface LoginNmiRepository : PagingAndSortingRepository<LoginNmi, Long> {

    fun findByLoginUsernameAndNmi(username: String, nmi: String): LoginNmi?

    fun findByLoginAndNmi(login: Login, nmi: String): LoginNmi?

    fun findAllByLogin(login: Login): Collection<LoginNmi>

}
