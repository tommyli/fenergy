// Tommy Li (tommy.li@firefire.co), 2017-07-10

package co.firefire.fenergy;

import co.firefire.fenergy.security.JwtAuthenticationUserInfoConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Value("${fenergy.security.oauth2.resource.user-info-uri}")
  String userInfoUri;

  @Bean
  public UserDetailsService userDetailsService() {
    return new InMemoryUserDetailsManager();
  }

  @Override
  public void configure(HttpSecurity http) throws Exception {
    http
      .httpBasic().disable()
      .authorizeRequests()
      .antMatchers("/public/**", "/index.html", "/actuator/health", "/auth/me").permitAll()
      .antMatchers("/**").fullyAuthenticated()
      .and()
      .oauth2ResourceServer().jwt().jwtAuthenticationConverter(new JwtAuthenticationUserInfoConverter(userInfoUri))
    ;
  }
}
