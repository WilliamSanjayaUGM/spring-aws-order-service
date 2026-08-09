package com.learn.order_service.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import com.learn.order_service.client.model.ProductDTO;

@FeignClient(name = "cloud-gateway")
public interface ProductFeignClient {
	
	@GetMapping("/product/all")
    ResponseEntity<List<ProductDTO>> findAll();
}
