package org.rsol.microapp.config;

import java.util.HashMap;
import java.util.Map;

import org.rsol.microapp.listener.OrderEventListener;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;

import lombok.extern.log4j.Log4j2;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@Log4j2
@EnableRabbit
@EnableSwagger2
public class AppConfig2 {

	@Value("${order.event.queue}")
	private String worklogQueueName;

	@Value("${order.dead-letter.queue}")
	private String deadLetterQueueName;

	@Value("${spring.rabbitmq.host}")
	private String host;

	@Bean
	public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
		return new RabbitTemplate(connectionFactory);
	}

	@Bean
	public CachingConnectionFactory connectionFactory() {
		CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory();
		cachingConnectionFactory.setHost(host);
		return cachingConnectionFactory;
	}

	/*
	 * @Bean DirectExchange exchange() { return new
	 * DirectExchange("order.exchange"); }
	 */

	@Bean
	public Queue queue() {
		return new Queue(worklogQueueName, true);

		/*
		 * return QueueBuilder.durable(worklogQueueName)
		 * .withArgument("x-dead-letter-exchange", "")
		 * .withArgument("x-dead-letter-routing-key", deadLetterQueueName) .build();
		 */
	}

	@Bean
	Queue deadLetterQueue() {
		return new Queue(deadLetterQueueName, true);
	}

	
	@Bean
	public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
		return new Jackson2JsonMessageConverter();
	}
	 

	/*
	 * @Bean Binding binding() { return
	 * BindingBuilder.bind(queue()).to(exchange()).with(worklogQueueName); }
	 */

    @Bean
    public MappingJackson2MessageConverter consumerJackson2MessageConverter() {
        return new MappingJackson2MessageConverter();
    }
    
	@Bean
	public SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
			MessageListenerAdapter listenerAdapter) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.setQueueNames(worklogQueueName);
		container.setMessageListener(listenerAdapter);
		return container;
	}

	@Bean
	public MessageListenerAdapter listenerAdapter(OrderEventListener receiver) {
		MessageListenerAdapter listener = new MessageListenerAdapter(receiver);
		Map<String, String> queueToMethodName = new HashMap<String, String>();
		queueToMethodName.put(this.worklogQueueName, "receiveEvent2");
		listener.setQueueOrTagToMethodName(queueToMethodName);
		listener.setMessageConverter(jackson2JsonMessageConverter());
		return listener;
	}

	/**
	 * Method used for Controller to be exposed on Swaggers!
	 * 
	 * @return
	 */
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any()).build();
	}

}
