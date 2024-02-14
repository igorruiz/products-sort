package com.productsort.application.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.igor.openapi.model.Product;
import com.igor.openapi.model.ProductSortRequest;
import com.igor.openapi.model.Weights;
import com.productsort.adapters.service.ProductSortService;
import com.productsort.application.mapper.ProductSortMapper;
import com.productsort.model.ProductDTO;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = ProductSortController.class)
class ProductSortControllerTest {

	@Autowired
	private WebTestClient webTestClient;

	@MockBean
	private ProductSortService productSortService;

	@MockBean
	private ProductSortMapper productSortMapper;

	@Test
	void givenValidRequest_whenInvokingCausingNullPointerException_thenReturnInternalServerCode() throws Exception {
		this.webTestClient.post().uri("/product/sort").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).body(Mono.justOrEmpty(buildValidRequest()), ProductSortRequest.class)
				.exchange().expectStatus().is5xxServerError();
	}
	
	@Test
	void givenRequest_whenInvokingWithoutBody_thenReturnBadRequest() throws Exception {
		this.webTestClient.post().uri("/product/sort").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).body(Mono.justOrEmpty(null), ProductSortRequest.class)
				.exchange().expectStatus().isBadRequest();
	}

	@Test
	void givenValidRequest_whenInvoking_thenReturnOkWithSortedProducts() throws Exception {

		List<ProductDTO> productList = new ArrayList<>();
		Flux<ProductDTO> productFlux = Flux.just(new ProductDTO());

		given(this.productSortMapper.productListToProductDTOList(anyList())).willReturn(productList);
		given(this.productSortService.sortProducts(anyList(), anyMap())).willReturn(productFlux);
		given(this.productSortMapper.productDTOtoProduct(any(ProductDTO.class))).willReturn(new Product());
		this.webTestClient.post().uri("/product/sort").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.body(Mono.justOrEmpty(buildValidRequest()), ProductSortRequest.class).exchange().expectStatus().isOk();
		verify(this.productSortMapper, times(1)).productListToProductDTOList(anyList());
		verify(this.productSortMapper, times(1)).productDTOtoProduct(any(ProductDTO.class));
		verify(this.productSortService, times(1)).sortProducts(anyList(), anyMap());
	}

	private static ProductSortRequest buildValidRequest() {
		ProductSortRequest request = new ProductSortRequest();

		request.addProductsItem(buildProductItem(1L, "V-NECH BASIC SHIRT", "1", "100", "4"));
		request.addProductsItem(buildProductItem(2L, "CONTRASTING FABRIC T-SHIRT", "1", "50", "35"));
		request.addProductsItem(buildProductItem(3L, "RAISED PRINT T-SHIRT", "1", "80", "20"));
		request.addProductsItem(buildProductItem(4L, "PLEATED T-SHIRT", "1", "3", "25"));
		request.addProductsItem(buildProductItem(5L, "CONTRASTING LACE T-SHIRT", "1", "650", "0"));
		request.addProductsItem(buildProductItem(6L, "SLOGAN T-SHIRT", "1", "20", "9"));

		request.addWeightsItem(new Weights().name("sales").weight(80L));
		request.addWeightsItem(new Weights().name("stock").weight(20L));

		return request;
	}

	private static Product buildProductItem(Long productId, String productName, String brandId, String sales,
			String stock) {
		Product product = new Product();

		product.setProductId(productId);
		product.setProductName(productName);
		product.setProductParams(new HashMap<>());
		product.getProductParams().put("brandId", brandId);
		product.getProductParams().put("sales", sales);
		product.getProductParams().put("stock", stock);

		return product;
	}
}
