package org.rsol.microapp.repository;

import java.util.List;

import org.rsol.microapp.model.OrderCriteriaModel;
import org.rsol.microapp.model.OrderModel;

@FunctionalInterface
public interface OrderRepositoryCriteria {

	public List<OrderModel> findByCriteria(OrderCriteriaModel criteria) throws Exception;
}
