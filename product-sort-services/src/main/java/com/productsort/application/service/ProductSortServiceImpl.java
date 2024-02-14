package com.productsort.application.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.productsort.adapters.service.ProductSortService;
import com.productsort.model.ProductDTO;
import com.productsort.model.utils.StringToNumberUtils;

import reactor.core.publisher.Flux;

@Service
public class ProductSortServiceImpl implements ProductSortService {

	/*
	 * Method that receives: A list of products with the relevant parameters and a
	 * map of weights of those parameters to consider in the sorting algorithm.
	 * 
	 * And returns a Flux of sorted products.
	 * 
	 * Why receiving a list instead of a flux: OpenApi already generated the request
	 * with a plain object (List) instead of Flux. Also sorting a flux (that may be
	 * infinite) is risky and has to be considered in different ways. (It's out of
	 * the purpose of this showcase).
	 */
	@Override
	public Flux<ProductDTO> sortProducts(List<ProductDTO> products, Map<String, Long> weights) {

		List<ProductDTO> productsorted = products.stream().sorted((firstProduct,
				secondProduct) -> evaluateScore(secondProduct, weights).compareTo(evaluateScore(firstProduct, weights)))
				.toList();
		return Flux.fromIterable(productsorted);
	}

	// Utility Method to evaluate the weights of the product, used in the comparator
	private static Long evaluateScore(ProductDTO product, Map<String, Long> weights) {
		return weights.keySet().stream().mapToLong(key -> {
			if (product.getProductParams().containsKey(key)
					&& StringToNumberUtils.isLong(product.getProductParams().get(key))) {
				return Long.valueOf(product.getProductParams().get(key)) * weights.get(key);
			}
			return Long.valueOf(0L);
		}).sum();

	}

}
