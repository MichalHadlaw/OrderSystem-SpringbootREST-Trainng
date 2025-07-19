package com.example.ordersystem.controller;

import com.example.ordersystem.dto.CustomerRequest;
import com.example.ordersystem.dto.CustomerResponse;
import com.example.ordersystem.entity.Customer;
import com.example.ordersystem.repository.CustomerRepository;
import com.example.ordersystem.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;
    public CustomerController(CustomerService customerService){this.customerService = customerService;}

    @PostMapping
    public CustomerResponse crearteCustomer(@Valid @RequestBody CustomerRequest customerRequest){
        return customerService.createCustomer(customerRequest);
    }

    @GetMapping("/{id}")
    public CustomerResponse getCustomerById(@PathVariable Long id){
        return customerService.getCustomerById(id);
    }

    @GetMapping
    public List<CustomerResponse>getAllCustomers(){
        return customerService.getAllCustomers();
    }

    @PutMapping("/{id}")
    public CustomerResponse updateCustomer(@PathVariable Long id,@RequestBody CustomerRequest request){
        return customerService.updateCustomer(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable Long id){
        customerService.deleteCustomer(id);
    }

}
