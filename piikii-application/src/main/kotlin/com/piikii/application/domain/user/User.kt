package com.piikii.application.domain.user

import com.piikii.application.domain.generic.LongTypeId
import com.piikii.application.domain.generic.UuidTypeId

class User(
    val userUid: UuidTypeId,
    val roomId: LongTypeId,
)
