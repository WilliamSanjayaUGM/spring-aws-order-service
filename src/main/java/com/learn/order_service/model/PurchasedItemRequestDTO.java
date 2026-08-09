package com.learn.order_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchasedItemRequestDTO {
	
	private String productSku;
	
	private int amount;
	
	private String discountCode;
}
