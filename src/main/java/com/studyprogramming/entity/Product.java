package com.studyprogramming.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "product")
public class Product extends BaseEntity {
    @Column()
    private String name;

    @Column()
    private String description;

    @Column()
    private BigDecimal price;

    @Column()
    private int quantity;

    @Column()
    private String vendor;

    @Column()
    private String tags;

    @Column(length = 500)
    private String[] imageNames;

    @ManyToOne
    @JoinColumn(name = "categoryId")
    private Category category;

    @OneToOne()
    @JoinColumn(name = "specificationId")
    private Specification specification;

    @OneToMany(mappedBy = "product")
    private List<Rating> ratings;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String[] getImageNames() {
        return imageNames;
    }

    public void setImageNames(String[] imageNames) {
        this.imageNames = imageNames;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public UUID getCategoryId() {
        return category != null ? category.getId() : null;
    }

    public Specification getSpecification() {
        return specification;
    }

    public void setSpecification(Specification specification) {
        this.specification = specification;
    }

    public UUID getSpecificationId() {
        return specification != null ? specification.getId() : null;
    }

    public List<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }
}
