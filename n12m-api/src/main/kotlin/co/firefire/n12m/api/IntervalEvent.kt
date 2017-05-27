// Tommy Li (tommy.li@firefire.co), 2017-03-10

package co.firefire.n12m.api

import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.Parameter
import javax.persistence.Embedded
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Entity
data class IntervalEvent(

        @Id
        @GenericGenerator(
                name = "IntervalEventIdSeq",
                strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
                parameters = arrayOf(
                        Parameter(name = "sequence_name", value = "interval_event_id_seq"),
                        Parameter(name = "initial_value", value = "1000"),
                        Parameter(name = "increment_size", value = "1")
                )
        )
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "IntervalEventIdSeq")
        var id: Long = -1,

        @ManyToOne(optional = false)
        @JoinColumn(name = "interval_day", referencedColumnName = "id", nullable = false)
        var intervalDay: IntervalDay = IntervalDay(),

        var startInterval: Int = -1,
        var endInterval: Int = -1,

        @Embedded
        var intervalQuality: IntervalQuality = IntervalQuality(Quality.A)

) {

    override fun toString(): String {
        return "IntervalEvent(id=$id, intervalDay=$intervalDay, startInterval=$startInterval, endInterval=$endInterval, intervalQuality=$intervalQuality)"
    }
}
