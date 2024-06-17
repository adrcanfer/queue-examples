package com.adrcanfer.kafka.consumers;

import java.time.Duration;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.errors.WakeupException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class KafkaThreadConsumer  extends Thread {
	private static final Logger LOGGER = LogManager.getLogger(KafkaThreadConsumer.class);
	private static final String KAFKA_TOPIC = "emails";
	
	private final Consumer<String, String> consumer;
	private final AtomicBoolean closed = new AtomicBoolean(false);

	public KafkaThreadConsumer(Consumer<String, String> consumer) {
		this.consumer = consumer;
	}
	
	@Override
	public void run() {
		consumer.subscribe(Arrays.asList(KAFKA_TOPIC));
		
			try {
				while(!closed.get()) {
					ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
					for (ConsumerRecord<String, String> record : records)
						LOGGER.info("offset = {}, key = {}, value ={}", record.offset(), record.key(), record.value());
				}
			} catch (WakeupException e) {
				if(!closed.get()) {
					throw e;
				}
			} finally {
				consumer.close();
			}
	}
	
	public void shutdown() {
		closed.set(true);
		consumer.wakeup();
		
	}
}
