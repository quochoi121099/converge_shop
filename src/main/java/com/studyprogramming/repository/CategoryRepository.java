package com.studyprogramming.repository;

import com.studyprogramming.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CategoryRepository  extends JpaRepository<Category, UUID> {
    Category findByName(String name);

    @Query("SELECT c FROM Category c WHERE c.parentCategory IS NULL")
    List<Category> findAllParentCategories();

    Category getById(UUID uuid);
}
