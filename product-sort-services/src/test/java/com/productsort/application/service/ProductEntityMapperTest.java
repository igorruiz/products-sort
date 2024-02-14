package com.productsort.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;

import org.junit.jupiter.api.Test;

import com.productsort.application.mapper.ProductEntityMapper;
import com.productsort.application.model.entities.ProductEntity;
import com.productsort.model.ProductDTO;

class ProductEntityMapperTest {

	private static final long PRODUCT_STOCK = 100L;
	private static final long PRODUCT_SALES = 10L;
	private static final String PRODUCT_NAME = "T-SHIRT";
	private static final long BRAND_ID = 1L;
	private static final long PRODUCT_ID = 123456789L;

	ProductEntityMapper productEntityMapper = new ProductEntityMapper() {
		
		@Override
		public ProductDTO productEntityToProductDTO(ProductEntity productEntity) {
			return null;
		}
	};

	@Test
	void givenAProductEntity_whenMapping_thenReturnAMapOfStringString() {

		// Prepare
		ProductEntity productEntity = new ProductEntity(PRODUCT_ID, BRAND_ID, PRODUCT_NAME, PRODUCT_SALES,
				PRODUCT_STOCK);

		// Act
		Map<String, String> result = this.productEntityMapper.map(productEntity);

		// Assert
		assertTrue(result.containsKey(ProductEntityMapper.PRODUCT_BRAND_ID));
		assertTrue(result.containsKey(ProductEntityMapper.PRODUCT_SALES));
		assertTrue(result.containsKey(ProductEntityMapper.PRODUCT_STOCK));
		assertEquals(String.valueOf(BRAND_ID), result.get(ProductEntityMapper.PRODUCT_BRAND_ID));
		assertEquals(String.valueOf(PRODUCT_SALES), result.get(ProductEntityMapper.PRODUCT_SALES));
		assertEquals(String.valueOf(PRODUCT_STOCK), result.get(ProductEntityMapper.PRODUCT_STOCK));

	}

}
