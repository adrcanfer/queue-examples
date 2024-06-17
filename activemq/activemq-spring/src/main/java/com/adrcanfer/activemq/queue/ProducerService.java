package com.adrcanfer.activemq.queue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import com.adrcanfer.activemq.User;

@Service
public class ProducerService {
	
	private static final Logger LOGGER = LogManager.getLogger(ProducerService.class);

	@Value("${spring.activemq.queue}")
	private String QUEUE;
	
    @Autowired
    private JmsTemplate jmsTemplate;
    
    public void sendToQueue(User user) {
        try {
        	LOGGER.info("Mensaje enviado: {}", user);
            jmsTemplate.convertAndSend(QUEUE, user);
        }
        catch (Exception e) {
            LOGGER.error("ERROR in sending message to queue", e);
        }
    }

}
