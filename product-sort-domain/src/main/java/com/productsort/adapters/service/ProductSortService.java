package com.productsort.adapters.service;

import java.util.List;
import java.util.Map;

import com.productsort.model.ProductDTO;

import reactor.core.publisher.Flux;

public interface ProductSortService {
	
	Flux<ProductDTO> sortProducts(List<ProductDTO> products, Map<String, Long> weights);

}
