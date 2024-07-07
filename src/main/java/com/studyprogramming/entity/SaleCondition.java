package com.studyprogramming.entity;

import com.studyprogramming.entity.enums.EObjectName;
import com.studyprogramming.entity.enums.ESaleRule;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "saleCondition")
public class SaleCondition extends BaseEntity {
    @Enumerated(EnumType.STRING)
    @Column(name = "objectName")
    private EObjectName objectName;

    @Column(name = "minPrice")
    private double minPrice;

    @Enumerated(EnumType.STRING)
    @Column(name = "saleRule")
    private ESaleRule saleRule;

    @OneToMany(mappedBy = "saleCondition", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Sale> sales;

    // Getters and Setters
    public EObjectName getObjectName() {
        return objectName;
    }

    public void setObjectName(EObjectName objectName) {
        this.objectName = objectName;
    }

    public double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(double minPrice) {
        this.minPrice = minPrice;
    }

    public ESaleRule getSaleRule() {
        return saleRule;
    }

    public void setSaleRule(ESaleRule saleRule) {
        this.saleRule = saleRule;
    }

    public List<Sale> getSales() {
        return sales;
    }

    public void setSales(List<Sale> sales) {
        this.sales = sales;
    }
}
