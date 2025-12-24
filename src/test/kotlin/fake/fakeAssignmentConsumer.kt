package com.dms.driver.test.fakes

import com.dms.driver.messaging.domain.AssignmentEventConsumer

class FakeAssignmentConsumer : AssignmentEventConsumer {
    override fun start() {}
    override fun stop() {}
}
