package com.learn.order_service.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestBody;

import com.learn.order_service.client.ProductFeignClient;
import com.learn.order_service.client.model.ProductDTO;
import com.learn.order_service.entity.Order;
import com.learn.order_service.entity.PurchasedItems;
import com.learn.order_service.mapper.OrderMapper;
import com.learn.order_service.mapper.PurchasedItemMapper;
import com.learn.order_service.model.OrderRequestDTO;
import com.learn.order_service.model.OrderResponseDTO;
import com.learn.order_service.model.PurchasedItemRequestDTO;
import com.learn.order_service.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {
	
	private final ProductFeignClient productFeignCLient;
	private final PurchasedItemMapper purchasedItemMapper;
	private final OrderMapper orderMapper;
	private final OrderRepository orderRepository;
	
	@Transactional
	public OrderResponseDTO createOrder(@RequestBody OrderRequestDTO orderRequest) {
		ResponseEntity<List<ProductDTO>> productDTOResponse = productFeignCLient.findAll();
		List<ProductDTO> productDTOList = new ArrayList<>();
		if(!ObjectUtils.isEmpty(productDTOResponse) && !ObjectUtils.isEmpty(productDTOResponse.getBody()) ) {
			productDTOList.addAll(productDTOResponse.getBody());
		}
		
		Order order = Order.builder()
				.merchantId(orderRequest.getMerchantId())
				.customerId(orderRequest.getCustomerId())
				.build();
		if(!ObjectUtils.isEmpty(orderRequest.getOrderId()) ) {
			order = orderRepository.findById(orderRequest.getOrderId()).orElseThrow(
					() -> new RuntimeException("Order Id is not correct"));
		}
		
		generateTransactionId(order);
		
		List<PurchasedItems> purchasedItemList = 
				processAndMapPurchasedItems(productDTOList, orderRequest.getPurchasedItems());
		
		order.getPurchasedItems().clear();
		order.getPurchasedItems().addAll(purchasedItemList);
		order.setTotalAmount(countTotalAmountPurchasedItems(purchasedItemList));
		orderRepository.save(order);
		return orderMapper.orderEntityToOrderResponseDTO(order);
	}
	
	public Order getOrderDetails(String transactionId) {
		return orderRepository.findByTransactionId(transactionId).orElseThrow(
				() -> new RuntimeException("Your requested transactionId is not available"));
	}
	
	public List<PurchasedItems> processAndMapPurchasedItems(List<ProductDTO> productDTOList, List<PurchasedItemRequestDTO> purchasedItems) {
		Map<String, ProductDTO> productMap = productDTOList.stream()
	            .collect(Collectors.toMap(ProductDTO::getSku, product -> product));
		
		boolean anyInvalidRequest = purchasedItems.stream()
	            .anyMatch(request -> {
	                ProductDTO product = productMap.get(request.getProductSku());
	                // not valid logic: SKU not found or stock is not enough
	                return product == null || request.getAmount() > product.getQty();
	            });
		
		if(anyInvalidRequest) {
			throw new IllegalArgumentException("Sku and amount is not valid");
		}
		
		return purchasedItemMapper.combineRequestAndDTOToEntity(purchasedItems, productMap);
	}
	
	public void generateTransactionId(Order order) {
		if(ObjectUtils.isEmpty(order.getId())) {
			order.setId(UUID.randomUUID());
			order.setTransactionId("TRX-" + order.getId().toString());
		}
	}
	
	public BigDecimal countTotalAmountPurchasedItems(List<PurchasedItems> purchasedItemList) {
		if(ObjectUtils.isEmpty(purchasedItemList)) {
			return BigDecimal.ZERO;
		}
		
		return purchasedItemList.stream()
			    .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getAmount())))
			    .reduce(BigDecimal.ZERO, BigDecimal::add);
	}
}
