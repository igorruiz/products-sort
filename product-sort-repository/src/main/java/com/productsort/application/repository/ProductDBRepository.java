package com.productsort.application.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.productsort.adapters.repository.ProductRepository;
import com.productsort.application.model.entities.ProductEntity;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ProductDBRepository extends ReactiveCrudRepository<ProductEntity, Long>, ProductRepository<ProductEntity> {

	Flux<ProductEntity> findAll();
	
	@Query("SELECT * FROM PRODUCTS WHERE ID = :productId")
	Mono<ProductEntity> findById(Long productId);	

}
