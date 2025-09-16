package com.rayyau.eshop.pymt.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class OrderReqDto {
    private List<OrderDto> orderList;
}
