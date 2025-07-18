package com.example.ordersystem.service;

import com.example.ordersystem.dto.OrderRequest;
import com.example.ordersystem.dto.OrderResponse;
import com.example.ordersystem.entity.Customer;
import com.example.ordersystem.entity.Order;
import com.example.ordersystem.entity.OrderStatus;
import com.example.ordersystem.repository.CustomerRepository;
import com.example.ordersystem.repository.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;

    public OrderService(OrderRepository orderRepository, CustomerRepository customerRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
    }

    public OrderResponse createOrder(OrderRequest orderRequest) {


        Customer customer = customerRepository.findById(orderRequest.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));


        OrderStatus status = OrderStatus.valueOf(orderRequest.getStatus());


        Order order = new Order();
        order.setOrderDate(orderRequest.getOrderDate());
        order.setTotalAmount(orderRequest.getTotalAmount());
        order.setStatus(status);
        order.setCustomer(customer);


        Order savedOrder = orderRepository.save(order);


        return mapToOrderResponse(savedOrder);
    }

    public OrderResponse getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        return mapToOrderResponse(order);
    }


    private OrderResponse mapToOrderResponse(Order order) {
        OrderResponse response = new OrderResponse();
        response.setId(order.getId());
        response.setOrderDate(order.getOrderDate());
        response.setTotalAmount(order.getTotalAmount());
        response.setStatus(order.getStatus().name());
        response.setCustomerId(order.getCustomer().getId());
        return response;
    }

    public OrderResponse updateOrder(Long id, OrderRequest orderRequest) {
        Order existingOrder = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        Customer customer = customerRepository.findById(orderRequest.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        existingOrder.setOrderDate(orderRequest.getOrderDate());
        existingOrder.setTotalAmount(orderRequest.getTotalAmount());
        existingOrder.setStatus(OrderStatus.valueOf(orderRequest.getStatus()));
        existingOrder.setCustomer(customer);

        Order updatedOrder = orderRepository.save(existingOrder);
        return mapToOrderResponse(updatedOrder);
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}
