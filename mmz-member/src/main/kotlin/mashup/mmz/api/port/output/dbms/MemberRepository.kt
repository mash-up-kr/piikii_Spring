package mashup.mmz.api.port.output.dbms

import mashup.mmz.api.domain.MemberVO

interface MemberRepository {
    fun create(): MemberVO
    fun createAll(): List<MemberVO>
    fun retrieve(): MemberVO
    fun retrieveAll(): List<MemberVO>
    fun update(): MemberVO
    fun updateAll(): List<MemberVO>
    fun delete(): Void
    fun deleteAll(): Void
}