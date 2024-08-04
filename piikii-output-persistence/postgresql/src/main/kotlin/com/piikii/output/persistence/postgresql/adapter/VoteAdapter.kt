package com.piikii.output.persistence.postgresql.adapter

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

    override fun findAllByPlaceIds(placeIds: List<Long>): List<Vote> {
        return voteRepository.findAllByPlaceIdIn(placeIds).map { it.toDomain() }
    }

    override fun findAgreeCountByPlaceId(votes: List<Vote>): Map<Long, Int> {
        return votes
            .filter { it.result == VoteResult.AGREE }
            .groupingBy { it.placeId }
            .eachCount()
    }
}
