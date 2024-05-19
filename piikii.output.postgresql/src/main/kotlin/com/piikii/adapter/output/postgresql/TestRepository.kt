package com.piikii.adapter.output.postgresql

import com.piikii.application.port.output.TestOutPort
import org.springframework.stereotype.Repository

@Repository
class TestRepository : TestOutPort {
    override fun output(): String {
        return "test"
    }
}
