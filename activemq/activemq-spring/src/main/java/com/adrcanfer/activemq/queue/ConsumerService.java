package com.adrcanfer.activemq.queue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import com.adrcanfer.activemq.User;

@Service
public class ConsumerService {
	
	private static final Logger LOGGER = LogManager.getLogger(ConsumerService.class);

	@JmsListener(destination = "${spring.activemq.queue}")
	public void listener(User user) {
    	LOGGER.info("Mensaje recibido: {}", user);
	}
}
