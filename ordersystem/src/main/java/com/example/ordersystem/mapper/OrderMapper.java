package com.example.ordersystem.mapper;

import com.example.ordersystem.dto.OrderRequest;
import com.example.ordersystem.dto.OrderResponse;
import com.example.ordersystem.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mappings({
            @Mapping(target = "customerId", source = "customer.id"),
            @Mapping(target = "status", expression = "java(order.getStatus().name())")
    })
    OrderResponse toDto(Order order);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "customer", ignore = true),
            @Mapping(target = "status", ignore = true)
    })
    Order toEntity(OrderRequest request);
}
