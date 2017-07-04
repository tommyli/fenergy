// Tommy Li (tommy.li@firefire.co), 2017-07-03

package co.firefire.n12m.api.domain

import javax.persistence.Entity

@Entity
data class Login(
        var username: String = "",
        var email: String = ""
) {
    var name: String? = null
    var pictureUrl: String? = null
    var local: String? = null
    var familyName: String? = null
    var givenName: String? = null
}
