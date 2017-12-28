// Tommy Li (tommy.li@firefire.co), 2017-06-29

package co.firefire.fenergy.rest

import org.slf4j.LoggerFactory
import org.springframework.security.oauth2.provider.OAuth2Authentication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
class LoginController {

    companion object {
        var log = LoggerFactory.getLogger(this::class.java)

        fun parsePrincipal(principal: Principal?): Map<String, String> {
            val result = mapOf("name" to (principal?.name ?: ""))
            return if (principal is OAuth2Authentication) {
                val details = principal.userAuthentication.details
                if (details is Map<*, *>) {
                    result.plus(mapOf(
                            "email" to details["email"] as String,
                            "givenName" to details["given_name"] as String,
                            "familyName" to details["family_name"] as String,
                            "pictureUrl" to details["picture"] as String,
                            "locale" to details["locale"] as String
                    ))
                } else {
                    result
                }
            } else {
                result
            }
        }
    }

    @GetMapping("/user", "/currentlogin")
    fun currentlogin(principal: Principal?): Map<String, String> {
        assert(true)
        return parsePrincipal(principal)
    }
}
