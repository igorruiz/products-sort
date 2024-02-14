package com.productsort.model;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductDTO {
	
	private Long productId;
	private String productName;
	private Map<String, String> productParams;
	
	

}
