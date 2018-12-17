// Tommy Li (tommy.li@firefire.co), 2017-07-03

package co.firefire.fenergy.shared.domain

import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.Parameter
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class Login(

        var username: String = "",
        var email: String = ""

) : Comparable<Login> {

    @Id
    @GenericGenerator(
            name = "LoginIdSeq",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = arrayOf(
                    Parameter(name = "sequence_name", value = "login_id_seq"),
                    Parameter(name = "initial_value", value = "1000"),
                    Parameter(name = "increment_size", value = "1")
            )
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LoginIdSeq")
    var id: Long? = null

    var name: String? = null
    var pictureUrl: String? = null
    var locale: String? = null
    var familyName: String? = null
    var givenName: String? = null

    override fun compareTo(other: Login) = compareValuesBy(this, other, { it.username })

    override fun toString() = "Login(username='$username', email='$email', id=$id)"

}
