package com.example.ordersystem.service;

import com.example.ordersystem.dto.CustomerRequest;
import com.example.ordersystem.dto.CustomerResponse;
import com.example.ordersystem.entity.Customer;
import com.example.ordersystem.exceptions.CustomerNotFoundException;
import com.example.ordersystem.mapper.CustomerMapper;
import com.example.ordersystem.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public CustomerService(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    public CustomerResponse createCustomer(CustomerRequest request) {
        Customer customer = customerMapper.mapToEntity(request);
        Customer saved = customerRepository.save(customer);
        return customerMapper.mapToResponse(saved);
    }

    public CustomerResponse getCustomerById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));
        return customerMapper.mapToResponse(customer);
    }

    public List<CustomerResponse> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        return customers.stream()
                .map(customerMapper::mapToResponse)
                .collect(Collectors.toList());
    }

    public CustomerResponse updateCustomer(Long id, CustomerRequest request) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));

        customerMapper.updateEntity(customer, request);

        Customer updated = customerRepository.save(customer);
        return customerMapper.mapToResponse(updated);
    }

    public void deleteCustomer(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));
        customerRepository.delete(customer);
    }
}
