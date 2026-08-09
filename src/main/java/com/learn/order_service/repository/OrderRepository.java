package com.learn.order_service.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.learn.order_service.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID>{
	public Optional<Order> findByTransactionId(String transactionId);
}
