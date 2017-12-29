// Tommy Li (tommy.li@firefire.co), 2017-07-10

package co.firefire.fenergy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
//import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableTransactionManagement
//@EnableZuulProxy
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class);
  }

}
