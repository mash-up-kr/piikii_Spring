package com.piikii.output.persistence.postgresql.adapter

import com.piikii.application.domain.generic.LongTypeId
import com.piikii.application.domain.generic.UuidTypeId
import com.piikii.application.domain.vote.Vote
import com.piikii.application.domain.vote.VoteResult
import com.piikii.application.port.output.persistence.VoteCommandPort
import com.piikii.application.port.output.persistence.VoteQueryPort
import com.piikii.output.persistence.postgresql.persistence.entity.VoteEntity
import com.piikii.output.persistence.postgresql.persistence.repository.VoteRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional(readOnly = true)
class VoteAdapter(
    private val voteRepository: VoteRepository,
) : VoteCommandPort, VoteQueryPort {
    @Transactional
    override fun save(votes: List<Vote>) {
        voteRepository.saveAll(votes.map(VoteEntity::from))
    }

    override fun findAllBySchedulePlaceIds(schedulePlaceIds: List<LongTypeId>): List<Vote> {
        return voteRepository.findAllBySchedulePlaceIdIn(schedulePlaceIds.map { it.getValue() }).map { it.toDomain() }
    }

    override fun findAllByUserUid(userUid: UuidTypeId): List<Vote> {
        return voteRepository.findAllByUserUid(userUid.getValue()).map { it.toDomain() }
    }

    override fun findAgreeCountBySchedulePlaceId(votes: List<Vote>): Map<Long, Int> {
        return votes
            .filter { it.result == VoteResult.AGREE }
            .groupingBy { it.schedulePlaceId.getValue() }
            .eachCount()
    }
}
