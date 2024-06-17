package com.adrcanfer.kafka.producers;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MyKafkaProducer {

	private static final Logger LOGGER = LogManager.getLogger(MyKafkaProducer.class);
	private static final String KAFKA_TOPIC = "emails";

	private static Producer<String, String> producer;

	public static void produceMessages(int numberOfMessages) {
		MyKafkaProducer.initProducer();

		for (int i = 1; i <= numberOfMessages; i++) {
			MyKafkaProducer.sendMessageToQueue(String.valueOf(i), "Message " + i);
		}

		MyKafkaProducer.endProducer();

	}

	private static void initProducer() {
		LOGGER.info("Initializing producer");
		Properties props = new Properties();
		props.put("bootstrap.servers", "localhost:9092");
		props.put("acks", "1");
		props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

		if (MyKafkaProducer.producer == null) {
			try {
				MyKafkaProducer.producer = new KafkaProducer<>(props);
			} catch (Exception e) {
				LOGGER.error("Error initializing producer", e);
			}
		} else {
			LOGGER.info("Producer was initialized");
		}

	}

	private static void sendMessageToQueue(String key, String message) {
		LOGGER.info("Sending message to Kafka");
		producer.send(new ProducerRecord<>(KAFKA_TOPIC, key, message), (metadata, e) -> {
			if (e != null) {
				LOGGER.error("Error sending message to queue", e);
			} else {
				LOGGER.info("Offset = {}, Partition = {}, Topic = {}", metadata.offset(), metadata.partition(), metadata.topic());
			}
		});
	}

	private static void endProducer() {
		LOGGER.info("Closing producer");
		try {
			producer.close();
		} catch (Exception e) {
			LOGGER.error("Error closing connection with queue", e);
		}
	}

}
