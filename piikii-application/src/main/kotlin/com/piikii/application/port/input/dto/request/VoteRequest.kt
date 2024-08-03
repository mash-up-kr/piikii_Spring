package com.piikii.application.port.input.dto.request

import com.piikii.application.domain.vote.Vote
import com.piikii.application.domain.vote.VoteResult
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.Valid
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import java.util.UUID

data class VoteSaveRequest(
    @field:NotNull(message = "사용자 ID는 필수입니다.")
    @field:Schema(description = "투표를 진행하는 사용자의 ID", example = "123e4567-e89b-12d3-a456-426614174000")
    val userUid: UUID,
    @field:NotEmpty(message = "최소 하나 이상의 투표 결과가 필요합니다.")
    @field:Valid
    @field:Schema(description = "장소별 투표 결과 목록")
    val votes: List<PlaceVoteResult>,
) {
    fun toDomains(): List<Vote> {
        return this.votes
            .map {
                Vote(
                    userUid = userUid,
                    placeId = it.placeId,
                    result = it.voteResult,
                )
            }
    }
}

data class PlaceVoteResult(
    @field:NotNull(message = "장소 ID는 필수입니다.")
    @field:Schema(description = "투표 대상 장소의 ID", example = "1")
    val placeId: Long,
    @field:NotNull(message = "투표 결과는 필수입니다.")
    @field:Schema(
        description = "투표 결과 (찬성 또는 반대)",
        example = "AGREE",
    )
    val voteResult: VoteResult,
)
