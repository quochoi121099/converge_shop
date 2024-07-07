package com.studyprogramming.service;

import com.studyprogramming.entity.Order;
import com.studyprogramming.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    public List<Order> findAll(){
        return orderRepository.findAll();
    }

    public Order findById(UUID uuid) {
        return orderRepository.getReferenceById(uuid);
    }

    public List<Order> getAllByUserId(UUID uuid) {
        return orderRepository.getAllByUserId(uuid);
    }

    public void save(Order order){
        orderRepository.save(order);
    }
}
