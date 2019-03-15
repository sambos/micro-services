package org.rsol.microapp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import org.rsol.microapp.model.OrderModel;

@Repository
public interface OrderRepository extends MongoRepository<OrderModel, String>, OrderRepositoryCriteria{

}
