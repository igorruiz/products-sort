package com.productsort.application.model.entities;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductEntity{
	
	@Id
	private Long productId;
	
	private Long brandId;

	private String productName;
	
	private Long productSales;
	
	private Long productStock;
	

}