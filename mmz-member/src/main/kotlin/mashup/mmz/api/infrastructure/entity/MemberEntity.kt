package mashup.mmz.api.infrastructure.entity

import jakarta.persistence.Entity
import mashup.mmz.mysql.BaseEntity

@Entity(name = "MEMBER")
class MemberEntity(
    var loginId: String,
    var password: String
) : BaseEntity() {
}