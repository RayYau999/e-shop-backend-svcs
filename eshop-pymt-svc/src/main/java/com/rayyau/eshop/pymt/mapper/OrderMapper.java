package com.rayyau.eshop.pymt.mapper;

import com.rayyau.eshop.pymt.dto.Order;
import com.rayyau.eshop.pymt.dto.OrderDto;
import com.rayyau.eshop.pymt.entity.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    // Maps OrderEntity to Order
    Order orderEntityToOrder(OrderEntity entity);

    OrderEntity orderDtoToOrderEntity(OrderDto orderDto);

}
