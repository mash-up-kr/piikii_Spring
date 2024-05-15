package mashup.mmz.api.infrastructure.repository

import mashup.mmz.api.domain.MemberVO
import mashup.mmz.api.port.output.dbms.MemberRepository
import org.springframework.stereotype.Repository

@Repository
class MemberRestRepository(
    private val memberJpaRepository: MemberJpaRepository,
    private val memberBulkRepository: MemberBulkRepository
) : MemberRepository {

    override fun retrieve(): MemberVO {
        TODO("Not yet implemented")
    }

    override fun retrieveAll(): List<MemberVO> {
        TODO("Not yet implemented")
    }

    override fun create(): MemberVO {
        TODO("Not yet implemented")
    }

    override fun createAll(): List<MemberVO> {
        TODO("Not yet implemented")
    }

    override fun update(): MemberVO {
        TODO("Not yet implemented")
    }

    override fun updateAll(): List<MemberVO> {
        TODO("Not yet implemented")
    }

    override fun delete(): Void {
        TODO("Not yet implemented")
    }

    override fun deleteAll(): Void {
        TODO("Not yet implemented")
    }
}