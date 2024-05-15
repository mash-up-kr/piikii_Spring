package mashup.mmz.api.adapter

import mashup.mmz.api.domain.MemberVO
import mashup.mmz.api.port.input.dto.MemberJoinCommand
import mashup.mmz.api.port.input.dto.MemberLoginCommand
import mashup.mmz.api.port.input.dto.MemberMultipleRetrieveCommand
import mashup.mmz.api.port.input.dto.MemberRetrieveCommand
import mashup.mmz.api.port.input.usecase.MemberUsecase
import mashup.mmz.api.port.output.dbms.MemberRepository
import mashup.mmz.api.port.output.messagingsystem.MemberMessagingSystem
import org.springframework.stereotype.Service

@Service
class MemberUsecaseConcrete(
    private val memberRepository: MemberRepository,
    private val memberMessagingSystem: MemberMessagingSystem
) : MemberUsecase {

    override fun join(memberJoinCommand: MemberJoinCommand): MemberVO {
        return MemberVO(1L, "test", "test")
    }

    override fun login(memberLoginCommand: MemberLoginCommand): MemberVO {
        return MemberVO(1L, "test", "test")
    }

    override fun retrieveMember(memberRetrieveCommand: MemberRetrieveCommand): MemberVO {
        return MemberVO(1L, "test", "test")
    }

    override fun retrieveMultipleMember(memberMultipleRetrieveCommand: MemberMultipleRetrieveCommand): List<MemberVO> {
        return mutableListOf(
            MemberVO(1L, "test", "test"),
            MemberVO(2L, "test", "test"),
        )
    }
}