package com.productsort.application.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.productsort.model.ProductDTO;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class ProductSortServiceImplTest {

	private static final String STOCK = "STOCK";
	private static final String SALES = "SALES";

	@InjectMocks
	ProductSortServiceImpl productSortService;

	@Test
	void givenAListofProductsAndWeights_whenSorting_returnTheSortedProducts() {

		// Prepare
		Map<String, Long> weights = buildWeights();
		List<ProductDTO> products = buildProductsList();

		// Act
		Flux<ProductDTO> result = this.productSortService.sortProducts(products, weights);

		// Assert
		StepVerifier.create(result)
			.expectNext(products.get(2))
			.expectNext(products.get(4))
			.expectNext(products.get(1))
			.expectNext(products.get(0))
			.expectNext(products.get(3))
			.verifyComplete();

	}
	
	@Test
	void givenAListofProductsAndWeightsWithInvalidWeight_whenSorting_returnTheSortedProductsIgnoringInvalidWeight() {

		// Prepare
		Map<String, Long> weights = buildWeights();
		weights.put("NEWONE", 10L);
		List<ProductDTO> products = buildProductsList();

		// Act
		Flux<ProductDTO> result = this.productSortService.sortProducts(products, weights);

		// Assert
		StepVerifier.create(result)
			.expectNext(products.get(2))
			.expectNext(products.get(4))
			.expectNext(products.get(1))
			.expectNext(products.get(0))
			.expectNext(products.get(3))
			.verifyComplete();

	}

	@Test
	void givenAListofProductsAndWeightsOneNotExistingInProductParams_whenSorting_returnTheSortedProductsIgnoringInvalidWeight() {

		// Prepare
		Map<String, Long> weights = buildWeights();
		List<ProductDTO> products = buildProductsList().stream().map(product -> {
			product.getProductParams().remove(SALES);
			return product;
		}).collect(Collectors.toList());

		// Act
		Flux<ProductDTO> result = this.productSortService.sortProducts(products, weights);

		// Assert
		StepVerifier.create(result)
			.expectNext(products.get(1))
			.expectNext(products.get(4))
			.expectNext(products.get(0))
			.expectNext(products.get(3))
			.expectNext(products.get(2))
			.verifyComplete();

	}

	private static Map<String, Long> buildWeights() {

		Map<String, Long> weights = new HashMap<>();

		weights.put(STOCK, 20L);
		weights.put(SALES, 80L);

		return weights;
	}

	private static List<ProductDTO> buildProductsList() {
		List<ProductDTO> productList = new ArrayList<>();

		productList.add(buildProduct(1L, "T-SHIRT", "10", "50")); // 1800 USING PREV WEIGHTS
		productList.add(buildProduct(2L, "JEANS", "2", "100")); // 2160 USING PREV WEIGHTS
		productList.add(buildProduct(3L, "SHOES", "50", "10")); // 4200 USING PREV WEIGHTS
		productList.add(buildProduct(4L, "NECKLACE", "1", "20")); // 480 USING PREV WEIGHTS
		productList.add(buildProduct(5L, "NECKLACE", "5", "90")); // 2200 USING PREV WEIGHTS

		return productList;

	}

	private static ProductDTO buildProduct(Long id, String productName, String sales, String stock) {

		Map<String, String> params = new HashMap<>();
		params.put(SALES, sales);
		params.put(STOCK, stock);
		return new ProductDTO(id, productName, params);
	}

}
