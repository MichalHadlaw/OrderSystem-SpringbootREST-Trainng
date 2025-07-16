package com.example.ordersystem.controller;

import com.example.ordersystem.entity.Customer;
import com.example.ordersystem.repository.CustomerRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerRepository customerRepository;

    public CustomerController(CustomerRepository customerRepository){
    this.customerRepository = customerRepository;
}

    @PostMapping
    public Customer addCustomer(@RequestBody Customer customer){
        return customerRepository.save(customer);
    }

    @GetMapping
    public List<Customer> getAllCustomers(){
        return customerRepository.findAll();
    }




}
