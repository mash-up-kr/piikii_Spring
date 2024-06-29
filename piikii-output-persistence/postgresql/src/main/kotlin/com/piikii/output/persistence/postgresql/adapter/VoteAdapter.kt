package com.piikii.output.persistence.postgresql.adapter

import com.piikii.application.domain.vote.Vote
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
    override fun vote(votes: List<Vote>) {
        voteRepository.saveAll(votes.map(VoteEntity::from))
    }
}
