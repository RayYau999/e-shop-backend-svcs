package com.rayyau.eshop.pymt.mapper;

import com.rayyau.eshop.pymt.dto.PaymentStatusDto;
import com.rayyau.eshop.pymt.entity.PaymentStatusEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentStatusMapper {
    PaymentStatusEntity paymentStatusDtoToPaymentStatusEntity(PaymentStatusDto paymentStatusDto);
}
