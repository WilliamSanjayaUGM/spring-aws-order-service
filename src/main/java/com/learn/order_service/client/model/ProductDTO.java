package com.learn.order_service.client.model;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
	
	private UUID id;
	
	private String sku;
	
	private String productName;
	
	private String productCategory;
	
	private int qty;
	
	private BigDecimal price;
	
	private boolean fromApproval = false;
}
