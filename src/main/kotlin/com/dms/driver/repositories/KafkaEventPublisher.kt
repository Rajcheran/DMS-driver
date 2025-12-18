package com.dms.driver.repositories

import com.dms.driver.repositories.EventPublisher
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerRecord
import java.util.Properties
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString

class KafkaEventPublisher(bootstrapServers: String) : EventPublisher {

    private val producer: KafkaProducer<String, String>

    init {
        val config: Map<String, Any> = mapOf(
            "bootstrap.servers" to bootstrapServers,
            "key.serializer" to "org.apache.kafka.common.serialization.StringSerializer",
            "value.serializer" to "org.apache.kafka.common.serialization.StringSerializer",
            "acks" to "1",
            "linger.ms" to 5
        )

        producer = KafkaProducer(config)
    }

    override fun publish(topic: String, key: String?, payload: String) {
        val record = ProducerRecord(topic, key, payload)
        producer.send(record) { meta, ex ->
            if (ex != null) {
                System.err.println("Kafka publish error: ${ex.message}")
            } else {
                println("Published to ${meta.topic()} @ ${meta.offset()}")
            }
        }
    }

    override fun close() {
        producer.flush()
        producer.close()
    }
}