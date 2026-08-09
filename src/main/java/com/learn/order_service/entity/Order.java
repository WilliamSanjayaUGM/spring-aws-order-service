package com.learn.order_service.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tbl_order")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {
	
	@Id
	@Column(name = "id", updatable = false, nullable = false)
	private UUID id;
	
	@Column(name = "transaction_id", unique = true)
	private String transactionId;
	
	@Column(name = "total_amount", nullable = false)
	private BigDecimal totalAmount;
	
	@CreationTimestamp
	@Column(name = "purchased_at", nullable = false, updatable = false)
	private LocalDateTime purchasedAt;
	
	@Column(name = "merchant_id", nullable = false)
	private String merchantId;
	
	@Column(name = "customer_id", nullable = false)
	private String customerId;
	
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<PurchasedItems> purchasedItems;
}
