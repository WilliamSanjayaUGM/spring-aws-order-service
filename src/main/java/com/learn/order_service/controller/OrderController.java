package com.learn.order_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.learn.order_service.entity.Order;
import com.learn.order_service.model.OrderRequestDTO;
import com.learn.order_service.model.OrderResponseDTO;
import com.learn.order_service.service.OrderService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/order")
public class OrderController {
	
	private final OrderService orderService;
	
	@PostMapping
	public ResponseEntity<OrderResponseDTO> createOrder(@RequestBody OrderRequestDTO orderRequest) {
		return ResponseEntity.ok().body(orderService.createOrder(orderRequest));
	}
	
	@GetMapping
	public ResponseEntity<Order> getOrderDetails(@RequestParam("transactionId") String transactionId) {
		return ResponseEntity.ok().body(orderService.getOrderDetails(transactionId));
	}
}
