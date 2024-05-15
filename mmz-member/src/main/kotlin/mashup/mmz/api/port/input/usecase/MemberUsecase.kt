package mashup.mmz.api.port.input.usecase

import mashup.mmz.api.domain.MemberVO
import mashup.mmz.api.port.input.dto.MemberJoinCommand
import mashup.mmz.api.port.input.dto.MemberLoginCommand
import mashup.mmz.api.port.input.dto.MemberMultipleRetrieveCommand
import mashup.mmz.api.port.input.dto.MemberRetrieveCommand

interface MemberUsecase {
    fun join(memberJoinCommand: MemberJoinCommand) : MemberVO
    fun login(memberLoginCommand: MemberLoginCommand) : MemberVO
    fun retrieveMember(memberRetrieveCommand: MemberRetrieveCommand): MemberVO
    fun retrieveMultipleMember(memberMultipleRetrieveCommand: MemberMultipleRetrieveCommand): List<MemberVO>
}