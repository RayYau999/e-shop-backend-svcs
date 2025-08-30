package com.rayyau.eshop.pymt.mapper;

import com.rayyau.eshop.pymt.dto.Order;
import com.rayyau.eshop.pymt.dto.OrderEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    // Maps OrderEntity to Order
    Order orderEntytyToOrder(OrderEntity entity);
}
