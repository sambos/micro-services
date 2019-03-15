package org.rsol.microapp.repository.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import org.rsol.microapp.model.OrderCriteriaModel;
import org.rsol.microapp.model.OrderModel;
import org.rsol.microapp.repository.OrderRepositoryCriteria;

import lombok.extern.log4j.Log4j2;

@Repository
@Log4j2
public class OrderRepositoryImpl implements OrderRepositoryCriteria {

	@Autowired
	private MongoTemplate template;

	@Override
	public List<OrderModel> findByCriteria(OrderCriteriaModel criteria) throws Exception {
		List<OrderModel> list = null;
		try {
			list = template.find(getWhereClause(criteria), OrderModel.class);
		} catch (Exception e) {
			log.error("Exception occurred while processing the request!" + e);
		}
		return list;
	}

	/**
	 * Method used for Preparing the Where Clause
	 * 
	 * @param criteria
	 * @param builder
	 * @param model
	 * @return
	 */
	private Query getWhereClause(OrderCriteriaModel criteria) {
		Query whereClause = new Query();
		if (!StringUtils.isEmpty(criteria.getType())) {
			whereClause.addCriteria(Criteria.where("type").is(criteria.getType()));
		}		
		if (criteria.getStatus() != null && criteria.getStatus() > 0) {
			whereClause.addCriteria(Criteria.where("status").is(criteria.getStatus()));
		}
		if (!StringUtils.isEmpty(criteria.getId())) {
			whereClause.addCriteria(Criteria.where("Id").is(criteria.getId()));
		}
		if (!StringUtils.isEmpty(criteria.getClientId())) {
			whereClause.addCriteria(Criteria.where("clientId").is(criteria.getClientId()));
		}
		return whereClause;
	}

}
