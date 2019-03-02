// Tommy Li (tommy.li@firefire.co), 2017-12-25

package co.firefire.fenergy.nem12

import co.firefire.fenergy.FenergyServiceConfig
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Import

@SpringBootApplication
@Import([FenergyServiceConfig])
class ServiceTestApplication {

  public static void main(String[] args) {
    SpringApplication.run(ServiceTestApplication.class);
  }
}
