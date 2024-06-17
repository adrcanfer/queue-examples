package com.adrcanfer.queue.publisher;

import org.apache.activemq.ActiveMQConnectionFactory;

import com.adrcanfer.utils.Constants;

import jakarta.jms.Connection;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.Destination;
import jakarta.jms.JMSException;
import jakarta.jms.MessageProducer;
import jakarta.jms.Session;
import jakarta.jms.TextMessage;

public class Publisher {
	
	
	public static void publishMessage(String message) {
		
		try {
			ConnectionFactory factory = new ActiveMQConnectionFactory(Constants.USERNAME, Constants.PASSWORD, Constants.BROKER_URL);
			Connection connection = factory.createConnection();
			
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Destination destination = session.createQueue(Constants.QUEUE);
			TextMessage textMessage = session.createTextMessage(message);
			MessageProducer producer = session.createProducer(destination);
			producer.send(textMessage);
			
			session.close();
			connection.close();
			
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

}
