// Tommy Li (tommy.li@firefire.co), 2019-02-09

package co.firefire.fenergy;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@PropertySource(value = {"classpath:/fenergy-service.properties"})
@ComponentScan(basePackages = {"co.firefire.fenergy"})
@EnableJpaRepositories(basePackages = {"co.firefire.fenergy"})
@EntityScan(basePackages = {"co.firefire.fenergy"})
@EnableCaching
@EnableTransactionManagement
public class FenergyServiceConfig {
}
