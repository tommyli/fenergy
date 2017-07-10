// Tommy Li (tommy.li@firefire.co), 2017-07-10

package co.firefire.fenergy;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@EnableOAuth2Client
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private OAuth2ClientContext oauth2ClientContext;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.antMatcher("/**").authorizeRequests().antMatchers("/", "/login**").permitAll().anyRequest().authenticated()
      .and().exceptionHandling()
      .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/"))
      .and().logout().logoutSuccessUrl("/").permitAll()
      .and().csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
      .and().addFilterBefore(ssoFilter(), BasicAuthenticationFilter.class);
  }

  private Filter ssoFilter() {
    OAuth2ClientAuthenticationProcessingFilter googleFilter = new OAuth2ClientAuthenticationProcessingFilter("/login/google");
    OAuth2RestTemplate googleTemplate = new OAuth2RestTemplate(googleClient(), oauth2ClientContext);
    googleFilter.setRestTemplate(googleTemplate);
    UserInfoTokenServices tokenServices = new UserInfoTokenServices(googleResource().getUserInfoUri(), googleClient().getClientId());
    tokenServices.setRestTemplate(googleTemplate);
    googleFilter.setTokenServices(tokenServices);

    return googleFilter;
  }

  @Bean
  public FilterRegistrationBean oauth2ClientFilterRegistration(OAuth2ClientContextFilter filter) {
    FilterRegistrationBean registration = new FilterRegistrationBean();
    registration.setFilter(filter);
    registration.setOrder(-100);

    return registration;
  }

  @Bean
  @ConfigurationProperties("google.client")
  public AuthorizationCodeResourceDetails googleClient() {
    return new AuthorizationCodeResourceDetails();
  }

  @Bean
  @ConfigurationProperties("google.resource")
  public ResourceServerProperties googleResource() {
    return new ResourceServerProperties();
  }

}
