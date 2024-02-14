package com.productsort.adapters.repository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductRepository<T> {
	
	Flux<T> findAll();
	
	Mono<T> findByProductId(Long productId);
	
}
