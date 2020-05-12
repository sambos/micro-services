package org.rsol.microapp.service.impl;

import java.util.List;

import org.rsol.microapp.model.OrderCriteriaModel;
import org.rsol.microapp.model.OrderModel;
import org.rsol.microapp.repository.OrderRepository;
import org.rsol.microapp.service.OrderService;
import org.rsol.microapp.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepository repository;

	/**
	 * This Method is used for Saving the Order Object into Mongo!.
	 * 
	 * @param model
	 * @return OrderModel
	 */
	public OrderModel saveOrder(OrderModel model) {
		try {
			model = repository.save(prepareObject(model));
		} catch (Exception e) {
			log.info("Error occured while processing saveOrder ! ", e.getMessage());
		}
		return model;
	}

	/**
	 * Method used for getting all the Orders
	 * 
	 * @param model
	 * @return
	 */
	public List<OrderModel> getAllOrders() {
		List<OrderModel> list = null;
		try {
			list = repository.findAll();
		} catch (Exception e) {
			log.info("Error occured while processing getAllOrders ! ", e.getMessage());
		}
		return list;
	}

	/**
	 * Method used for searching Orders by Criteria!.
	 * 
	 * @param criteria
	 * @return
	 */
	public List<OrderModel> getOrdersByCriteria(OrderCriteriaModel criteria) {
		List<OrderModel> list = null;
		try {
			list = repository.findByCriteria(criteria);
		} catch (Exception e) {
			log.info("Error occured while processing getOrdersByCriteria ! ", e.getMessage());
		}
		return list;
	}
	/**
	 * Method used for preparing the Object!.
	 * @param model
	 * @return
	 */
	private OrderModel prepareObject(OrderModel model)
	{
		if(!StringUtils.isEmpty(model.getId()))
		{
			model.setUpdatedDate(CommonUtil.getCurrentDateInString());
		}else{
			model.setCreatedDate(CommonUtil.getCurrentDateInString());
		}
		return model;
}	
}
