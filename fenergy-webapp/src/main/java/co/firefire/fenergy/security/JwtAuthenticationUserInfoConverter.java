package co.firefire.fenergy.security;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.util.StringUtils;

public class JwtAuthenticationUserInfoConverter implements Converter<Jwt, AbstractAuthenticationToken> {

  private static final String SCOPE_AUTHORITY_PREFIX = "SCOPE_";

  private static final Collection<String> WELL_KNOWN_SCOPE_ATTRIBUTE_NAMES =
    Arrays.asList("scope", "scp");

  private String userInfoUri;

  public JwtAuthenticationUserInfoConverter(String userInfoUri) {
    this.userInfoUri = userInfoUri;
  }

  public AbstractAuthenticationToken convert(Jwt jwt) {
    Collection<GrantedAuthority> authorities = extractAuthorities(jwt);
    return new JwtAuthenticationUserInfoToken(jwt, authorities, userInfoUri);
  }

  protected Collection<GrantedAuthority> extractAuthorities(Jwt jwt) {
    return this.getScopes(jwt)
      .stream()
      .map(authority -> SCOPE_AUTHORITY_PREFIX + authority)
      .map(SimpleGrantedAuthority::new)
      .collect(Collectors.toList());
  }

  private Collection<String> getScopes(Jwt jwt) {
    for (String attributeName : WELL_KNOWN_SCOPE_ATTRIBUTE_NAMES) {
      Object scopes = jwt.getClaims().get(attributeName);
      if (scopes instanceof String) {
        if (StringUtils.hasText((String) scopes)) {
          return Arrays.asList(((String) scopes).split(" "));
        }
        else {
          return Collections.emptyList();
        }
      }
      else if (scopes instanceof Collection) {
        return (Collection<String>) scopes;
      }
    }

    return Collections.emptyList();
  }

}
