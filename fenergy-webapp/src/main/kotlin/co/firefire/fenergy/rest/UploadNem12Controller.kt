// Tommy Li (tommy.li@firefire.co), 2017-09-26

package co.firefire.fenergy.rest

import co.firefire.fenergy.nem12.repository.LoginRepository
import co.firefire.fenergy.nem12.service.Nem12Service
import org.slf4j.LoggerFactory
import org.springframework.core.io.InputStreamResource
import org.springframework.core.io.Resource
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import java.security.Principal

@RestController
class UploadNem12Controller(
        val loginRepository: LoginRepository,
        val nem12Service: Nem12Service
) {

    companion object {
        var log = LoggerFactory.getLogger(this::class.java)
    }

    @PostMapping("/upload")
    fun upload(principal: Principal?, @RequestParam("file") file: MultipartFile, redirectAttributes: RedirectAttributes): Map<String, String> {
        if (principal != null) {
            val loginDetails = LoginController.parsePrincipal(principal)
            val login = loginRepository.findByEmailOrUsername(loginDetails.getOrDefault("email", ""), loginDetails.getOrDefault("username", ""))

            if (login != null) {
                val fileResource: Resource = InputStreamResource(file.inputStream)
                nem12Service.uploadNem12(login, fileResource)
            }
        }

        return mapOf("file" to "${file.originalFilename}")
    }
}
