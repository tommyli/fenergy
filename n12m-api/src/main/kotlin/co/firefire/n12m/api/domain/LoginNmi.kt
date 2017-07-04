// Tommy Li (tommy.li@firefire.co), 2017-07-03

package co.firefire.n12m.api.domain

import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.Parameter
import java.util.*
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
import javax.persistence.OrderBy

@Entity
data class LoginNmi(

        @ManyToOne(optional = false)
        @JoinColumn(name = "login", referencedColumnName = "id", nullable = false)
        var login: Login = Login(),

        var nmi: String = ""

) : Comparable<LoginNmi> {

    @Id
    @GenericGenerator(
            name = "LoginNmiIdSeq",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = arrayOf(
                    Parameter(name = "sequence_name", value = "login_nmi_id_seq"),
                    Parameter(name = "initial_value", value = "1000"),
                    Parameter(name = "increment_size", value = "1")
            )
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LoginNmiIdSeq")
    var id: Long? = null

    var label: String? = null

    @OneToMany(mappedBy = "loginNmi", cascade = arrayOf(CascadeType.ALL), orphanRemoval = true)
    @OrderBy("meterSerial, registerId, nmiSuffix")
    var nmiMeterRegisters: SortedSet<NmiMeterRegister> = TreeSet()

    override fun compareTo(other: LoginNmi) = compareValuesBy(this, other, { it.login }, { it.nmi })

    override fun toString() = "LoginNmi(login=$login, nmi='$nmi', id=$id, label=$label)"

}
