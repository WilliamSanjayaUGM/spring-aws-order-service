package com.learn.order_service.mapper;

import org.springframework.stereotype.Component;

import com.learn.order_service.entity.Order;
import com.learn.order_service.model.OrderResponseDTO;

@Component
public class OrderMapper {
	
	public OrderResponseDTO orderEntityToOrderResponseDTO(Order order) {
		int totalItems = order.getPurchasedItems().size();
		return OrderResponseDTO.builder()
				.transactionId(order.getTransactionId())
				.totalAmount(order.getTotalAmount())
				.purchasedAt(order.getPurchasedAt())
				.merchantId(order.getMerchantId())
				.customerId(order.getCustomerId())
				.totalItems(totalItems)
				.build();
	}
}
