package com.dms.driver.infrastructure.kafka

import com.dms.driver.messaging.consumer.DriverAssignmentConsumer
import io.ktor.server.application.*
import org.koin.core.error.InstanceCreationException
import org.koin.ktor.ext.inject

fun Application.installKafkaConsumers() {
    try {
        val assignmentConsumer by inject<DriverAssignmentConsumer>()

        environment.monitor.subscribe(ApplicationStarted) {
            assignmentConsumer.start()
        }

        environment.monitor.subscribe(ApplicationStopped) {
            assignmentConsumer.stop()
        }
    } catch (e: InstanceCreationException) {
        // Kafka consumers are optional - skip if dependencies are not available
        // This typically happens in test environments
        println("⚠️ Kafka consumers not initialized: ${e.message}")
    }
}
