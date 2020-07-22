package com.system.ordering.service;

import com.system.ordering.dto.OrderDto;
import com.system.ordering.model.Order;
import com.system.ordering.exceptions.OrderNotFoundException;
import com.system.ordering.repository.OrderRepository;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Cacheable(value = "orders", key = "#id")
    public Order findById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
    }

    public Order save(OrderDto orderDto) {
        ModelMapper modelMapper = new ModelMapper();
        Order order = modelMapper.map(orderDto, Order.class);
        return orderRepository.save(order);
    }

    public Order update(Long id, Order newOrder) {
        return orderRepository.findById(id)
                .map(order -> {
                    order.setName(newOrder.getName());
                    order.setCustomer(newOrder.getCustomer());
                    order.setDate(newOrder.getDate());
                    return orderRepository.save(order);
                })
                .orElseGet(() -> {
                    newOrder.setId(id);
                    return orderRepository.save(newOrder);
                });
    }

    public void deleteById(Long id) {
        orderRepository.deleteById(id);
    }
}
