package com.dms.driver.messaging.consumer

import com.dms.driver.messaging.model.DriverAssignmentEvent
import com.dms.driver.services.DriverService
import com.dms.driver.infrastructure.kafka.KafkaConsumerFactory
import kotlinx.coroutines.*
import kotlinx.serialization.json.Json
import org.apache.kafka.clients.consumer.ConsumerRecords
import org.apache.kafka.common.errors.WakeupException
import java.time.Duration

class DriverAssignmentConsumer(
    private val service: DriverService
) {

    private val consumer = KafkaConsumerFactory.create("driver-service")
    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    fun start() {
        consumer.subscribe(listOf("driver.assignments"))

        scope.launch {
            try {
                while (isActive) {
                    val records: ConsumerRecords<String, String> =
                        consumer.poll(Duration.ofSeconds(1))

                    for (record in records) {
                        val rawValue = record.value()

                        if (rawValue == null || rawValue.isBlank()) {
                            println("⚠️ Skipping empty Kafka message at offset ${record.offset()}")
                            continue
                        }

                        try {
                            val event = Json.decodeFromString<DriverAssignmentEvent>(rawValue)

                            service.handleAssignment(
                                driverId = event.driverId,
                                assignmentId = event.assignmentId
                            )
                        } catch (e: Exception) {
                            println(
                                "❌ Failed to parse Kafka message at offset ${record.offset()}: [$rawValue]"
                            )
                        }

                    }
                }
            } catch (e: WakeupException) {
                // ignore on shutdown
            } finally {
                consumer.close()
            }
        }
    }

    fun stop() {
        scope.cancel()
        consumer.wakeup()
    }
}