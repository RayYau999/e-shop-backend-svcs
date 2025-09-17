package com.rayyau.eshop.pymt.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import com.rayyau.eshop.payment.library.dto.OrderDto;
import java.util.List;

@AllArgsConstructor
@Data
public class OrderReqDto {
    private List<OrderDto> orderList;
}
