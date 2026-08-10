package com.learn.order_service.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import com.learn.order_service.client.model.ProductDTO;

@FeignClient(
	    name = "product-service",
	    url = "${product.service.url}"
)
public interface ProductFeignClient {
	
	@GetMapping("/v1/product/all")
    ResponseEntity<List<ProductDTO>> findAll();
}
