package com.studyprogramming.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "usedSalesList")
public class UsedSalesList extends BaseEntity{
    private UUID idOfTheObject;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sale_id")
    private Sale sale;

    public UUID getIdOfTheObject() {
        return idOfTheObject;
    }

    public void setIdOfTheObject(UUID idOfTheObject) {
        this.idOfTheObject = idOfTheObject;
    }

    public Sale getSale() {
        return sale;
    }

    public void setSale(Sale sale) {
        this.sale = sale;
    }
}
