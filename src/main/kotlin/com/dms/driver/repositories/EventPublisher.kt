package com.dms.driver.repositories

interface EventPublisher {
    fun publish(topic: String, key: String?, payload: String)
    fun close()
}