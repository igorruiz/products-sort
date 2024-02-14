package com.productsort.application.mapper;

import java.util.HashMap;
import java.util.Map;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.productsort.application.model.entities.ProductEntity;
import com.productsort.model.ProductDTO;

@Mapper(componentModel = "spring")
public interface ProductEntityMapper {

	static final String PRODUCT_SALES = "productSales";
	static final String PRODUCT_STOCK = "productStock";
	static final String PRODUCT_BRAND_ID = "brandId";
	
	@Mapping(target = "productParams", source = "productEntity", qualifiedByName = "productColumsToProductParams")
	ProductDTO productEntityToProductDTO(ProductEntity productEntity);
	
	@Named("productColumsToProductParams")
	default Map<String,String> map(ProductEntity productEntity) {
		Map<String, String> paramsMap = new HashMap<>();

		paramsMap.put(PRODUCT_SALES, productEntity.getProductSales().toString());
		paramsMap.put(PRODUCT_STOCK, productEntity.getProductStock().toString());
		paramsMap.put(PRODUCT_BRAND_ID, productEntity.getBrandId().toString());
		
		return paramsMap;
	}
	
}
