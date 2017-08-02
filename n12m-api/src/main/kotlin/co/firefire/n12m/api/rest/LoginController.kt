// Tommy Li (tommy.li@firefire.co), 2017-06-29

package co.firefire.n12m.api.rest

import org.slf4j.LoggerFactory
import org.springframework.security.oauth2.provider.OAuth2Authentication
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
class LoginController {

    companion object {
        var log = LoggerFactory.getLogger(this::class.java)
    }

    @RequestMapping("/user", "/currentlogin")
    fun currentlogin(principal: Principal?): Map<String, String> {
        return parsePrincipal(principal)
    }

    private fun parsePrincipal(principal: Principal?): Map<String, String> {
        val result = mapOf("name" to (principal?.name ?: ""))
        return if (principal is OAuth2Authentication) {
            val details = principal.userAuthentication.details
            if (details is Map<*, *>) {
                result.plus(mapOf(
                        "email" to details["email"] as String,
                        "givenName" to details["givenName"] as String,
                        "familyName" to details["familyName"] as String,
                        "pictureUrl" to details["pictureUrl"] as String,
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
