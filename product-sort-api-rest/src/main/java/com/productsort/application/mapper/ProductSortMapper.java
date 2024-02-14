package com.productsort.application.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.igor.openapi.model.Product;
import com.productsort.model.ProductDTO;

@Mapper(componentModel = "spring")
public interface ProductSortMapper {

	Product productDTOtoProduct(ProductDTO productDTO);

	ProductDTO productToProductDTO(Product product);

	List<Product> productDTOListToProductList(List<ProductDTO> productDTOList);

	List<ProductDTO> productListToProductDTOList(List<Product> productList);
	


}
