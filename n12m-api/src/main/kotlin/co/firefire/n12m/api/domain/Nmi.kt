// Tommy Li (tommy.li@firefire.co), 2017-07-03

package co.firefire.n12m.api.domain

import javax.persistence.Entity

@Entity
data class LoginNmi(
        var login: Login = Login(),
        var nmi: String = ""
) {
    var label: String? = null
}
