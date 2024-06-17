package com.adrcanfer.queue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.adrcanfer.queue.consumer.Consumer;
import com.adrcanfer.queue.publisher.Publisher;

public class Main {
	
	private static final Logger LOGGER = LogManager.getLogger(Main.class);

	public static void main(String[] args) {
		
		LOGGER.info("START CONSUMER");
		Consumer.startConsumer();

		LOGGER.info("START CONSUMER MANUAL ACK");
		Consumer.startConsumerManualACK();
		
		LOGGER.info("PRODUCE MESSAGE 1");
		Publisher.publishMessage("Message 1");
		
		LOGGER.info("PRODUCE MESSAGE 2");
		Publisher.publishMessage("Message 2");
		
		LOGGER.info("PRODUCE MESSAGE 3");
		Publisher.publishMessage("Message 3");
		
		LOGGER.info("PRODUCE MESSAGE 4");
		Publisher.publishMessage("Message 4");

	}

}
