// Tommy Li (tommy.li@firefire.co), 2017-07-10

package co.firefire.fenergy;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.Http401AuthenticationEntryPoint;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.filter.CompositeFilter;

@Configuration
@EnableOAuth2Client
@EnableAuthorizationServer
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class SocialAuthServerSecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private OAuth2ClientContext oauth2ClientContext;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.antMatcher("/**").authorizeRequests().antMatchers("/", "/index.html", "/*.bundle.js").permitAll()
      .anyRequest().authenticated()
      .and().exceptionHandling()
      .authenticationEntryPoint(new Http401AuthenticationEntryPoint(""))
      .and().logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler()).permitAll()
      .and().csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
      .and().addFilterBefore(ssoFilter(), BasicAuthenticationFilter.class);
  }

  @Bean
  public FilterRegistrationBean oauth2ClientFilterRegistration(OAuth2ClientContextFilter filter) {
    FilterRegistrationBean registration = new FilterRegistrationBean();
    registration.setFilter(filter);
    registration.setOrder(-100);

    return registration;
  }

  @Bean
  @ConfigurationProperties("google")
  public OAuthClientResources google() {
    return new OAuthClientResources();
  }

  @Bean
  @ConfigurationProperties("github")
  public OAuthClientResources github() {
    return new OAuthClientResources();
  }

  private Filter ssoFilter() {
    CompositeFilter filter = new CompositeFilter();
    List<Filter> filters = new ArrayList<>();
    filters.add(ssoFilter(google(), "/signin/google"));
    filters.add(ssoFilter(github(), "/signin/github"));
    filter.setFilters(filters);

    return filter;
  }

  private Filter ssoFilter(OAuthClientResources client, String path) {
    OAuth2ClientAuthenticationProcessingFilter filter = new OAuth2ClientAuthenticationProcessingFilter(path);
    OAuth2RestTemplate template = new OAuth2RestTemplate(client.getClient(), oauth2ClientContext);
    filter.setRestTemplate(template);
    UserInfoTokenServices tokenServices = new UserInfoTokenServices(client.getResource().getUserInfoUri(), client.getClient().getClientId());
    tokenServices.setRestTemplate(template);
    filter.setTokenServices(tokenServices);

    return filter;
  }

  @Configuration
  @EnableResourceServer
  protected static class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
    @Override
    public void configure(HttpSecurity http) throws Exception {
      http.antMatcher("/me").authorizeRequests().anyRequest().authenticated();
    }
  }

  public static class OAuthClientResources {

    @NestedConfigurationProperty
    private AuthorizationCodeResourceDetails client = new AuthorizationCodeResourceDetails();

    @NestedConfigurationProperty
    private ResourceServerProperties resource = new ResourceServerProperties();

    public AuthorizationCodeResourceDetails getClient() {
      return client;
    }

    public ResourceServerProperties getResource() {
      return resource;
    }

  }
}
