package com.productsort.application.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.server.ServerWebExchange;

import com.igor.openapi.model.Product;
import com.igor.openapi.model.ProductSortRequest;
import com.igor.openapi.model.Weights;
import com.igor.openapi.productsort.ProductApi;
import com.productsort.adapters.service.ProductSortService;
import com.productsort.application.mapper.ProductSortMapper;
import com.productsort.model.ProductDTO;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
public class ProductSortController implements ProductApi {

	@Autowired
	ProductSortService productSortService;

	@Autowired
	ProductSortMapper productSortMapper;

	@Override
	public Mono<ResponseEntity<Flux<Product>>> productSortPost(Mono<ProductSortRequest> productSortRequest,
			ServerWebExchange exchange) {
		return productSortRequest.map(request -> {
			List<ProductDTO> products = this.productSortMapper.productListToProductDTOList(request.getProducts());
			Map<String, Long> weights = request.getWeights().stream()
					.collect(Collectors.toMap(Weights::getName, Weights::getWeight));
			Flux<ProductDTO> sortedProducts = this.productSortService.sortProducts(products, weights);
			return buildResponse(sortedProducts);
		});
	}

	private ResponseEntity<Flux<Product>> buildResponse(Flux<ProductDTO> productDTOList) {

		return new ResponseEntity<>(
				productDTOList.map(productDTO -> this.productSortMapper.productDTOtoProduct(productDTO)),
				HttpStatus.OK);
	}

}
