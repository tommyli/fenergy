// Tommy Li (tommy.li@firefire.co), 2017-06-29

package co.firefire.n12m.api;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@Configuration
@EnableOAuth2Sso
@EnableResourceServer
public class ResourceServerSecurityConfig {

//  @Override
//  public void configure(HttpSecurity http) throws Exception {
//    http
//      .authorizeRequests().anyRequest().authenticated()
////      .and().csrf().disable()
//      .and().logout().logoutUrl("/auth/logout").logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler()).permitAll()
//      .and().csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
//    ;
//  }
}
