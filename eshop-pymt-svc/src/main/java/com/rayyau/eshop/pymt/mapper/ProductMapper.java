package com.rayyau.eshop.pymt.mapper;

import com.rayyau.eshop.payment.library.dto.ProductCatalogDto;
import com.rayyau.eshop.pymt.entity.ProductEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductCatalogDto productEntityToProductCatalogDto(ProductEntity entity);
}
