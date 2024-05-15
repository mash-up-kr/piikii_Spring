package mashup.mmz.api.port.input.dto

data class MemberJoinCommand(
    val loginId: String,
    val password: String,
)

data class MemberLoginCommand(
    val loginId: String,
    val password: String,
)

data class MemberRetrieveCommand(
    val id: Long,
)

data class MemberMultipleRetrieveCommand(
    val id: List<Long>,
)