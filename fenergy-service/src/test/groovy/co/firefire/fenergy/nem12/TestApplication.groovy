// Tommy Li (tommy.li@firefire.co), 2017-12-25

package co.firefire.fenergy.nem12

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.transaction.annotation.EnableTransactionManagement

@SpringBootApplication
@EntityScan(basePackages = "co.firefire.fenergy")
@EnableJpaRepositories(basePackages = "co.firefire.fenergy")
@EnableTransactionManagement
class TestApplication {

  public static void main(String[] args) {
    SpringApplication.run(TestApplication.class);
  }
}
