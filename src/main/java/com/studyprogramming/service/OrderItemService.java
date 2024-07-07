package com.studyprogramming.service;

import com.studyprogramming.entity.OrderItem;
import com.studyprogramming.repository.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderItemService {
    @Autowired
    private OrderItemRepository orderItemRepository;

    public List<OrderItem> findAll(){
        return orderItemRepository.findAll();
    }

    public BigDecimal calculateSubtotal(List<OrderItem> orderItems) {
        BigDecimal subtotal = BigDecimal.ZERO;
        for (OrderItem orderItem : orderItems) {
            subtotal = subtotal.add(orderItem.getTotalPrice());
        }
        return subtotal;
    }

}
