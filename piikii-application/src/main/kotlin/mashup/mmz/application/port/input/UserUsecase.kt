package mashup.mmz.application.port.input

import mashup.mmz.application.domain.model.User

interface UserUsecase {
    fun save(loginId: String): User
}