// Tommy Li (tommy.li@firefire.co), 2018-12-19

package co.firefire.fenergy.nem12

import groovy.transform.AnnotationCollector
import org.springframework.boot.test.autoconfigure.jdbc.TestDatabaseAutoConfiguration
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest as SpringDataJpaTest
import org.springframework.test.annotation.Rollback
import org.springframework.transaction.annotation.Transactional

@SpringDataJpaTest(excludeAutoConfiguration = [TestDatabaseAutoConfiguration])
@Transactional
@Rollback
@AnnotationCollector
@interface DataJpaTest {
}
