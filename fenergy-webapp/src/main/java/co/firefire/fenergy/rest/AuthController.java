// Tommy Li (tommy.li@firefire.co), 2017-07-28

package co.firefire.fenergy.rest;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/auth")
public class AuthController {

  @GetMapping("/me")
  public Map<String, String> me(Principal principal) {
    if (principal == null) {
      return new HashMap<>();
    }

    Map<String, String> result = new HashMap<>();
    result.put("name", principal.getName());

    return result;
  }

  @GetMapping("/me2")
  public Object me2(@AuthenticationPrincipal Object principal) {
    if (principal == null) {
      return new HashMap<>();
    }

    return principal;
  }
}
