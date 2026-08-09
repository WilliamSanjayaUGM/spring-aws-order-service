package com.learn.order_service.model;

import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestDTO {
	
	private UUID orderId;
	
	private String merchantId;
	
	private String customerId;
	
	private List<PurchasedItemRequestDTO> purchasedItems;
}
