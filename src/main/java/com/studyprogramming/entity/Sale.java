package com.studyprogramming.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "sale")
public class Sale extends BaseEntity {
    private String name;

    private String[] imageName;

    private BigDecimal discountPercentage;

    private LocalDate startDate;

    private LocalDate endDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "saleConditionId")
    private SaleCondition saleCondition;

    @OneToMany(mappedBy = "sale", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UsedSalesList> usedSalesLists;


    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getImageName() {
        return imageName;
    }

    public void setImageName(String[] imageName) {
        this.imageName = imageName;
    }

    public BigDecimal getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(BigDecimal discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public SaleCondition getSaleCondition() {
        return saleCondition;
    }

    public void setSaleCondition(SaleCondition saleCondition) {
        this.saleCondition = saleCondition;
    }

    public List<UsedSalesList> getUsedSalesLists() {
        return usedSalesLists;
    }

    public void setUsedSalesLists(List<UsedSalesList> usedSalesLists) {
        this.usedSalesLists = usedSalesLists;
    }
}


