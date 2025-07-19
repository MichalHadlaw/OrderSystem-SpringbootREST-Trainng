package com.example.ordersystem.mapper;

import com.example.ordersystem.dto.CustomerRequest;
import com.example.ordersystem.dto.CustomerResponse;
import com.example.ordersystem.entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    Customer mapToEntity(CustomerRequest request);

    CustomerResponse mapToResponse(Customer customer);

    void updateEntity(@MappingTarget Customer customer, CustomerRequest request);
}