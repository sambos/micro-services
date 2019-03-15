package org.rsol.microapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.rsol.microapp.model.OrderCriteriaModel;
import org.rsol.microapp.model.OrderModel;
import org.rsol.microapp.service.OrderService;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/api-service/order/v1")
@Log4j2
@ApiModel(value = "Order Service API")
@CrossOrigin
public class OrderServiceController {

	@Autowired
	private OrderService service;

	/**
	 * This method is used for getting the Orders by Criteria!
	 * 
	 * @param criteria
	 * @return
	 */
	@PostMapping(value = "/criteria", produces = "application/json")
	@ApiOperation(httpMethod = "POST", value = "This method is used for getting the Order by Specified Criteria! ", produces = "application/json", response = OrderModel.class)
	public ResponseEntity<?> getOrdersByCriteria(@RequestBody(required = true) OrderCriteriaModel criteria) {
		ResponseEntity<?> response = null;
		try {
			response = new ResponseEntity<>(service.getOrdersByCriteria(criteria), HttpStatus.OK);

		} catch (Exception e) {
			log.info("Error occurred while processing!", e.getMessage());
			response = new ResponseEntity<>("Error occurred while processing!", HttpStatus.BAD_REQUEST);
		}
		return response;
	}

	/**
	 * This method is used for saving Order !
	 * 
	 * @param criteria
	 * @return
	 */
	
	@PutMapping(value = "/save", produces = "application/json")
	@ApiOperation(httpMethod = "PUT", value = "This method is used for persisting a Order Object!.", produces = "application/json", response = OrderModel.class)
	public ResponseEntity<?> saveOrder(@RequestBody(required = true) OrderModel model) {
		ResponseEntity<?> response = null;
		try {
			response = new ResponseEntity<>(service.saveOrder(model), HttpStatus.OK);

		} catch (Exception e) {
			log.info("Error occurred while processing!", e.getMessage());
			response = new ResponseEntity<>("Error occurred while processing!", HttpStatus.BAD_REQUEST);
		}
		return response;
	}
	 

	/**
	 * This method is used for getting Orders!
	 * 
	 * @param criteria
	 * @return
	 */
	@GetMapping(value = "/all", produces = "application/json")
	@ApiOperation(httpMethod = "GET", value = "This method is used for getting all the Orders for a given type !.", produces = "application/json", response = OrderModel.class)
	public ResponseEntity<?> getAllOrders() {
		ResponseEntity<?> response = null;
		try {
			response = new ResponseEntity<>(service.getAllOrders(), HttpStatus.OK);

		} catch (Exception e) {
			log.info("Error occurred while processing!", e.getMessage());
			response = new ResponseEntity<>("Error occurred while processing!", HttpStatus.BAD_REQUEST);
		}
		return response;
	}

	/**
	 * This method is used for getting Orders!
	 * 
	 * @param criteria
	 * @return
	 */
	@GetMapping(value = "/{type}/all", produces = "application/json")
	@ApiOperation(httpMethod = "GET", value = "This method is used for getting all the Orders for a given type !.", produces = "application/json", response = OrderModel.class)
	public ResponseEntity<?> getOrdersByType(@PathVariable  String type) {
		ResponseEntity<?> response = null;
		try {
			OrderCriteriaModel criteria = new OrderCriteriaModel();
			criteria.setType(type);
			criteria.setStatus(1);
			response = new ResponseEntity<>(service.getOrdersByCriteria(criteria), HttpStatus.OK);

		} catch (Exception e) {
			log.info("Error occurred while processing!", e.getMessage());
			response = new ResponseEntity<>("Error occurred while processing getOrdersByType !", HttpStatus.BAD_REQUEST);
		}
		return response;
	}	
}
