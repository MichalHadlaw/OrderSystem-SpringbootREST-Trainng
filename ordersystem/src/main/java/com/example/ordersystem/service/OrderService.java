package com.example.ordersystem.service;

import com.example.ordersystem.dto.OrderRequest;
import com.example.ordersystem.dto.OrderResponse;
import com.example.ordersystem.entity.Customer;
import com.example.ordersystem.entity.Order;
import com.example.ordersystem.entity.OrderStatus;
import com.example.ordersystem.exceptions.CustomerNotFoundException;
import com.example.ordersystem.exceptions.OrderNotFoundException;
import com.example.ordersystem.mapper.OrderMapper;
import com.example.ordersystem.repository.CustomerRepository;
import com.example.ordersystem.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final OrderMapper orderMapper;

    public OrderService(OrderRepository orderRepository, CustomerRepository customerRepository, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.orderMapper = orderMapper;
    }

    public OrderResponse createOrder(OrderRequest orderRequest) {
        Customer customer = customerRepository.findById(orderRequest.getCustomerId())
                .orElseThrow(() -> new CustomerNotFoundException(orderRequest.getCustomerId()));

        Order order = orderMapper.toEntity(orderRequest);
        order.setCustomer(customer);
        order.setStatus(OrderStatus.valueOf(orderRequest.getStatus()));

        return orderMapper.toDto(orderRepository.save(order));
    }
    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(orderMapper::toDto)
                .toList();
    }

    public OrderResponse getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
        return orderMapper.toDto(order);
    }

    public OrderResponse updateOrder(Long id, OrderRequest orderRequest) {
        Order existingOrder = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));

        Customer customer = customerRepository.findById(orderRequest.getCustomerId())
                .orElseThrow(() -> new CustomerNotFoundException(orderRequest.getCustomerId()));

        existingOrder.setOrderDate(orderRequest.getOrderDate());
        existingOrder.setTotalAmount(orderRequest.getTotalAmount());
        existingOrder.setStatus(OrderStatus.valueOf(orderRequest.getStatus()));
        existingOrder.setCustomer(customer);

        return orderMapper.toDto(orderRepository.save(existingOrder));
    }

    public void deleteOrder(Long id) {
        if (!orderRepository.existsById(id)) {
            throw new OrderNotFoundException(id);
        }
        orderRepository.deleteById(id);
    }
}
