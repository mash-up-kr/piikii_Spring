package mashup.mmz.api

import mashup.mmz.api.domain.MemberVO
import mashup.mmz.api.port.input.dto.MemberJoinCommand
import mashup.mmz.api.port.input.dto.MemberLoginCommand
import mashup.mmz.api.port.input.dto.MemberMultipleRetrieveCommand
import mashup.mmz.api.port.input.dto.MemberRetrieveCommand
import mashup.mmz.api.port.input.usecase.MemberUsecase
import mashup.mmz.api.swagger.MemberApi
import org.springframework.web.bind.annotation.RestController

@RestController
class MemberApiConcrete(
    private val memberUsecase: MemberUsecase
) : MemberApi {

    override fun join(memberJoinCommand: MemberJoinCommand): MemberVO {
        return memberUsecase.join(memberJoinCommand)
    }

    override fun login(memberLoginCommand: MemberLoginCommand): MemberVO {
        return memberUsecase.login(memberLoginCommand)
    }

    override fun retrieveMember(memberRetrieveCommand: MemberRetrieveCommand): MemberVO {
        return memberUsecase.retrieveMember(memberRetrieveCommand)
    }

    override fun retrieveMultipleMember(memberMultipleRetrieveCommand: MemberMultipleRetrieveCommand): List<MemberVO> {
        return memberUsecase.retrieveMultipleMember(memberMultipleRetrieveCommand)
    }
}