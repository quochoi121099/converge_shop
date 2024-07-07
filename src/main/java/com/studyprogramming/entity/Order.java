package com.studyprogramming.entity;

import com.studyprogramming.entity.enums.EDelivery;
import com.studyprogramming.entity.enums.EFulfilStatus;
import com.studyprogramming.entity.enums.EPaymentMethod;
import com.studyprogramming.entity.enums.EPaymentStatus;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "orderBatch")
public class Order extends BaseEntity{
    @ManyToOne()
    @JoinColumn(name = "userId")
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderItem> orderItem;

    @Column()
    private BigDecimal totalAmount;

    @Enumerated(EnumType.STRING)
    @Column
    private EPaymentStatus paymentStatus;

    @Enumerated(EnumType.STRING)
    @Column
    private EFulfilStatus fulfilStatus;

    @Enumerated(EnumType.STRING)
    @Column
    private EDelivery deliveryType;

    @Enumerated(EnumType.STRING)
    @Column
    private EPaymentMethod paymentMethod;

    @Column
    private String shippingAddress;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<OrderItem> getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(List<OrderItem> orderItem) {
        this.orderItem = orderItem;
    }

    public BigDecimal calculateTotalAmount() {
        return orderItem.stream()
                .map(OrderItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getTotalAmount() {
        if (totalAmount == null) {
            totalAmount = calculateTotalAmount();
        }
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public EPaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(EPaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public EFulfilStatus getFulfilStatus() {
        return fulfilStatus;
    }

    public void setFulfilStatus(EFulfilStatus fulfilStatus) {
        this.fulfilStatus = fulfilStatus;
    }

    public EDelivery getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(EDelivery deliveryType) {
        this.deliveryType = deliveryType;
    }

    public EPaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(EPaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }
}
