package com.piikii.output.persistence.postgresql.adapter

import com.piikii.application.domain.generic.LongTypeId
import com.piikii.application.domain.generic.UuidTypeId
import com.piikii.application.domain.vote.Vote
import com.piikii.application.domain.vote.VoteResult
import com.piikii.output.persistence.postgresql.persistence.repository.VoteRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import java.util.UUID

@ExtendWith(MockitoExtension::class)
class VoteAdapterTest {
    @InjectMocks
    lateinit var voteAdapter: VoteAdapter

    @Mock
    lateinit var voteRepository: VoteRepository

    @Test
    fun `PlaceId 별로 AGREE 투표 수를 계산 및 매핑한다`() {
        // given
        val placeId1 = LongTypeId(1L)
        val placeId2 = LongTypeId(2L)
        val userUid = UuidTypeId(UUID.randomUUID())
        val votes =
            listOf(
                Vote(id = LongTypeId(1), userUid = userUid, schedulePlaceId = placeId1, result = VoteResult.AGREE),
                Vote(id = LongTypeId(2), userUid = userUid, schedulePlaceId = placeId1, result = VoteResult.AGREE),
                Vote(id = LongTypeId(3), userUid = userUid, schedulePlaceId = placeId2, result = VoteResult.AGREE),
                Vote(id = LongTypeId(4), userUid = userUid, schedulePlaceId = placeId1, result = VoteResult.DISAGREE),
                Vote(id = LongTypeId(5), userUid = userUid, schedulePlaceId = placeId2, result = VoteResult.DISAGREE),
            )

        // when
        val result = voteAdapter.findAgreeCountBySchedulePlaceId(votes)

        // then
        assertThat(result[placeId1.getValue()]).isEqualTo(2)
        assertThat(result[placeId2.getValue()]).isEqualTo(1)
    }
}
