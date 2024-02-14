package com.productsort.application.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.igor.openapi.model.Product;
import com.igor.openapi.model.ProductSortRequest;
import com.igor.openapi.model.Weights;

import reactor.core.publisher.Mono;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = { "spring.main.allow-bean-definition-overriding=true",
		"server.servlet.context-path=/" })
class ProductSortIntegrationTest {

	@Autowired
	private WebTestClient webTestClient;

	private static final String JSON_BODY = "{\"products\":[{\"productId\":1,\"productName\":\"V-NECH BASIC SHIRT\",\"productParams\":{\"brandId\":\"1\",\"sales\":\"100\",\"stock\":\"4\"}},{\"productId\":2,\"productName\":\"CONTRASTING FABRIC T-SHIRT\",\"productParams\":{\"brandId\":\"1\",\"sales\":\"50\",\"stock\":\"35\"}},{\"productId\":3,\"productName\":\"RAISED PRINT T-SHIRT\",\"productParams\":{\"brandId\":\"1\",\"sales\":\"80\",\"stock\":\"20\"}},{\"productId\":4,\"productName\":\"PLEATED T-SHIRT\",\"productParams\":{\"brandId\":\"1\",\"sales\":\"3\",\"stock\":\"25\"}},{\"productId\":5,\"productName\":\"CONTRASTING LACE T-SHIRT\",\"productParams\":{\"brandId\":\"1\",\"sales\":\"650\",\"stock\":\"0\"}},{\"productId\":6,\"productName\":\"SLOGAN T-SHIRT\",\"productParams\":{\"brandId\":\"1\",\"sales\":\"20\",\"stock\":\"9\"}}],\"weights\":[{\"name\":\"sales\",\"weight\":80},{\"name\":\"stock\",\"weight\":20}]}";

	private static final String JSON_RESPONSE = "[{\"productId\":5,\"productName\":\"CONTRASTING LACE T-SHIRT\",\"productParams\":{\"brandId\":\"1\",\"sales\":\"650\",\"stock\":\"0\"}},{\"productId\":1,\"productName\":\"V-NECH BASIC SHIRT\",\"productParams\":{\"brandId\":\"1\",\"sales\":\"100\",\"stock\":\"4\"}},{\"productId\":3,\"productName\":\"RAISED PRINT T-SHIRT\",\"productParams\":{\"brandId\":\"1\",\"sales\":\"80\",\"stock\":\"20\"}},{\"productId\":2,\"productName\":\"CONTRASTING FABRIC T-SHIRT\",\"productParams\":{\"brandId\":\"1\",\"sales\":\"50\",\"stock\":\"35\"}},{\"productId\":6,\"productName\":\"SLOGAN T-SHIRT\",\"productParams\":{\"brandId\":\"1\",\"sales\":\"20\",\"stock\":\"9\"}},{\"productId\":4,\"productName\":\"PLEATED T-SHIRT\",\"productParams\":{\"brandId\":\"1\",\"sales\":\"3\",\"stock\":\"25\"}}]";

	@Test
	void givenRequest_whenInvokingWithoutBody_thenReturnBadRequest() throws Exception {
		this.webTestClient.post().uri("/product/sort").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).body(Mono.justOrEmpty(null), ProductSortRequest.class)
				.exchange().expectStatus().isBadRequest();
	}

	@Test
	void givenValidRequestAsJson_whenInvoking_thenReturnOkWithSortedProducts() throws Exception {

		this.webTestClient.post().uri("/product/sort").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).body(Mono.justOrEmpty(JSON_BODY), String.class).exchange()
				.expectStatus().isOk().expectBody(String.class).isEqualTo(JSON_RESPONSE);
	}

	@Test
	void givenValidRequestAsDTO_whenInvoking_thenReturnOkWithSortedProducts() throws Exception {
		
		ProductSortRequest productSortRequest = buildValidRequest();
		List<Product> expectedSortedList = new ArrayList<>();
		expectedSortedList.add(productSortRequest.getProducts().get(4));
		expectedSortedList.add(productSortRequest.getProducts().get(0));
		expectedSortedList.add(productSortRequest.getProducts().get(2));
		expectedSortedList.add(productSortRequest.getProducts().get(1));
		expectedSortedList.add(productSortRequest.getProducts().get(5));
		expectedSortedList.add(productSortRequest.getProducts().get(3));
		
		ParameterizedTypeReference<List<Product>> typeRef = new ParameterizedTypeReference<List<Product>>() {
		};
		this.webTestClient.post().uri("/product/sort").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).body(Mono.just(productSortRequest), ProductSortRequest.class)
				.exchange().expectStatus().isOk().expectBody(typeRef).isEqualTo(expectedSortedList);
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
