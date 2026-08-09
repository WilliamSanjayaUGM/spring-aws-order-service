package com.learn.order_service.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDTO {
	
	private String transactionId;
	
	private BigDecimal totalAmount;
	
	private LocalDateTime purchasedAt;
	
	private String merchantId;
	
	private String customerId;
	
	private int totalItems;
}
