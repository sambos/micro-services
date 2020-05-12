package org.rsol.microapp.listener;

import java.util.Map;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.rsol.microapp.model.OrderModel;
import org.rsol.microapp.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class OrderEventListener {

	@Autowired
	private OrderService service;

	@Autowired
	private ObjectMapper mapper;

	
	/**
	 * This method will receive the event from a Queue.
	 * 
	 * @param message
	 */
	
	/*
	 * @RabbitListener(bindings = @QueueBinding( value = @Queue(value =
	 * "${order.event.queue}"), exchange = @Exchange(value = "order.exchange"),
	 * key = "order.event" ))
	 */
	//@RabbitListener(queues = "${order.event.queue}")
	//@RabbitListener(queues = "#{queue.name}")
	public void receiveEvent(Message message) {
		System.out.println("Received Message!" + message);
		String body = new String(message.getBody());
		try {
			if (body != null && body.length() > 0) {
				service.saveOrder(getModel(body));
			}
		} catch (Exception e) {
			log.info("Error occurred while processing! ", e.getMessage());
			throw new RuntimeException("Message could not be read ", e);
		}
	}
	
	public void receiveEvent2(Object message) {
		System.out.println("Received Message 2!" + message);
		String body = new String("");
		try {
			if (body != null && body.length() > 0) {
				service.saveOrder(getModel(body));
			}
		} catch (Exception e) {
			log.info("Error occurred while processing! ", e.getMessage());
			throw new RuntimeException("Message could not be read ", e);
		}
	}	
	
	private OrderModel getModel(String json) throws Exception {
		System.out.println(parseJson(json));
		OrderModel model = new OrderModel();
		
		return model;
	}
	
	public Map<String, Object> parseJson(String json) throws Exception {
		Map map = (Map) mapper.readValue(json, Object.class);
	return map;

	}
	

}
