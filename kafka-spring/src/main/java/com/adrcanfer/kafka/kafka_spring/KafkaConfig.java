package com.adrcanfer.kafka.kafka_spring;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.MicrometerProducerListener;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.prometheusmetrics.PrometheusConfig;
import io.micrometer.prometheusmetrics.PrometheusMeterRegistry;

@Configuration
public class KafkaConfig {
	
	@Value("${kafka.url}")
	private String url;
	
	@Value("${kafka.consumerGroup}")
	private String consumerGroup;
	
	
	
	// Consumer bean	
	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, String> listenerContainerFactory() {
		ConsumerFactory<String, String> consumerFactory = new DefaultKafkaConsumerFactory<>(consumerProps());
		
		ConcurrentKafkaListenerContainerFactory<String, String> listenerContainerFactory = new ConcurrentKafkaListenerContainerFactory<>();
		listenerContainerFactory.setConsumerFactory(consumerFactory);
		listenerContainerFactory.setConcurrency(3);
		
		return listenerContainerFactory;
	}
	
	//Producer bean
	@Bean
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public KafkaTemplate<String, String> kafkaTemplate() {
		DefaultKafkaProducerFactory<String, String> producerFactory = new DefaultKafkaProducerFactory<>(producerProps());
		
		//Add metrics with Micrometer
		producerFactory.addListener(new MicrometerProducerListener(meterRegistry()));
		KafkaTemplate<String, String> template = new KafkaTemplate<>(producerFactory);
		
		return template;
	}
	
	@Bean
	public MeterRegistry meterRegistry() {
		PrometheusMeterRegistry meterRegistry = new PrometheusMeterRegistry(PrometheusConfig.DEFAULT);
		return meterRegistry;
	}
	
	// Config
	private Map<String, Object> consumerProps() {
		Map<String, Object> props = new HashMap<>();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, url);
		props.put(ConsumerConfig.GROUP_ID_CONFIG, consumerGroup);
		props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		return props;
	}
	
	private Map<String, Object> producerProps() {
		Map<String, Object> props = new HashMap<>();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, url);
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		return props;
	}
}
