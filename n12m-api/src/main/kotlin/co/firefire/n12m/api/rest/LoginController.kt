// Tommy Li (tommy.li@firefire.co), 2017-06-29

package co.firefire.n12m.api.rest

import org.slf4j.LoggerFactory
import org.springframework.security.oauth2.provider.OAuth2Authentication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
class LoginController {

    companion object {
        var log = LoggerFactory.getLogger(this::class.java)
    }

    @RequestMapping("/login/current")
    fun login(principal: Principal?): Principal? {
        log.info("In login: ${principal}")
        return principal
    }

    @GetMapping("/user")
    fun user(principal: Principal?): Map<String, String> {
        val defaultResult = mapOf("name" to (principal?.name ?: ""))
        return if (principal is OAuth2Authentication) {
            val details = principal.userAuthentication.details
            if (details is Map<*, *>) {
                defaultResult.plus("email" to details["email"] as String)
            } else {
                defaultResult
            }
        } else {
            defaultResult
        }
    }
}
