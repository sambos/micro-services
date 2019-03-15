package org.rsol.microapp.service;

import java.util.List;

import org.rsol.microapp.model.OrderCriteriaModel;
import org.rsol.microapp.model.OrderModel;

public interface OrderService {

	public OrderModel saveOrder(OrderModel model);
	public List<OrderModel> getAllOrders();
	public List<OrderModel> getOrdersByCriteria(OrderCriteriaModel criteria);
}
