package com.studyprogramming.repository;

import com.studyprogramming.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductRepository  extends JpaRepository<Product, UUID> {
    Product findByName(String name);
}
