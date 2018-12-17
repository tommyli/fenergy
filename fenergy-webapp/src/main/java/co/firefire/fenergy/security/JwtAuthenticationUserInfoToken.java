// Tommy Li (tommy.li@firefire.co), 2018-12-12
package co.firefire.fenergy.security;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.client.RestTemplate;

public class JwtAuthenticationUserInfoToken extends JwtAuthenticationToken {

  private String userInfoUrl;

  public JwtAuthenticationUserInfoToken(Jwt jwt, String userInfoUrl) {
    super(jwt);
    this.userInfoUrl = userInfoUrl;
  }

  public JwtAuthenticationUserInfoToken(Jwt jwt, Collection<? extends GrantedAuthority> authorities, String userInfoUrl) {
    super(jwt, authorities);
    this.userInfoUrl = userInfoUrl;
  }

  @Override
  public Map<String, Object> getTokenAttributes() {
    RestTemplate restTemplate = new RestTemplate();
    HttpHeaders headers = new HttpHeaders();
    headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + getToken().getTokenValue());
    HttpEntity<String> entity = new HttpEntity<String>("", headers);
    ResponseEntity<Map> response = restTemplate
      .exchange(userInfoUrl, HttpMethod.GET, entity, Map.class);
    Map<String, Object> result = new HashMap<>();
    result.putAll(getToken().getClaims());
    result.putAll(response.getBody());

    return super.getTokenAttributes();
  }
}
