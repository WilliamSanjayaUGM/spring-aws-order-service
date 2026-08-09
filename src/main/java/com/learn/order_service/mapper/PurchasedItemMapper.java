package com.learn.order_service.mapper;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.learn.order_service.client.model.ProductDTO;
import com.learn.order_service.entity.PurchasedItems;
import com.learn.order_service.model.PurchasedItemRequestDTO;

@Component
public class PurchasedItemMapper {
	
	public List<PurchasedItems> combineRequestAndDTOToEntity(List<PurchasedItemRequestDTO> purchasedItems, Map<String, ProductDTO> productMap) {
		return purchasedItems.stream()
				.map(request -> {
					ProductDTO product = productMap.get(request.getProductSku());
					
					return PurchasedItems.builder()
							.productId(product.getId())
							.amount(request.getAmount())
							.price(product.getPrice())
							.build();
				}).collect(Collectors.toList());
	}
}
