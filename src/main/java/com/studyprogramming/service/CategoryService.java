package com.studyprogramming.service;

import com.studyprogramming.entity.Category;
import com.studyprogramming.entity.Product;
import com.studyprogramming.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public Category createOrUpdate(Category category){
        Category existingCategory = categoryRepository.findByName(category.getName());

        if(existingCategory == null){
            Category categoryEntity = new Category();
            categoryEntity.setName(category.getName());
            categoryEntity.setDescription(category.getDescription());
            return categoryRepository.save(categoryEntity);
        }else{
            existingCategory.setName(category.getName());
            existingCategory.setDescription(category.getDescription());
            return categoryRepository.save(existingCategory);
        }
    }

    public Category addSubcategory(String name, String description, UUID parentId) {
        Category parentCategory = categoryRepository.findById(parentId).orElse(null);
        if (parentCategory == null) {
            throw new IllegalArgumentException("Parent category not found");
        }

        Category subcategory = new Category();
        subcategory.setName(name);
        subcategory.setDescription(description);
        subcategory.setParentCategory(parentCategory);

        return categoryRepository.save(subcategory);
    }

    public List<Category> findAll(){
        return categoryRepository.findAll();
    }

    public Category getById(UUID uuid){
        if (uuid == null) return null;
        return categoryRepository.getById(uuid);
    }

    public List<Product> findAllProductsByCategoryList(List<Category> categoryList) {
        List<Product> products = new ArrayList<>();
        for (Category category : categoryList) {
            products.addAll(category.getProducts());
        }
        return products;
    }
    public Category findByName(String name){
        return categoryRepository.findByName(name);
    }

    public List<Category> findAllParentCategories(){
        return categoryRepository.findAllParentCategories();
    }


    @Transactional
    public void addCategoriesAndSubcategories() {
        // Tìm danh mục cha đã tồn tại
        Category smartphone = categoryRepository.findByName("Smartphone");
        Category computer = categoryRepository.findByName("Computer");

        // Tạo danh mục mới: Laptop
        Category laptop = new Category();
        laptop.setName("Laptop");
        laptop.setDescription("Various brands of laptops");
        categoryRepository.save(laptop);

        // Tạo danh mục con cho Laptop
        Category msiLaptop = new Category();
        msiLaptop.setName("MSI");
        msiLaptop.setDescription("MSI laptops");
        msiLaptop.setParentCategory(laptop);

        Category asusLaptop = new Category();
        asusLaptop.setName("ASUS");
        asusLaptop.setDescription("ASUS laptops");
        asusLaptop.setParentCategory(laptop);

        Category dellLaptop = new Category();
        dellLaptop.setName("DELL");
        dellLaptop.setDescription("DELL laptops");
        dellLaptop.setParentCategory(laptop);

        Category lgLaptop = new Category();
        lgLaptop.setName("LG");
        lgLaptop.setDescription("LG laptops");
        lgLaptop.setParentCategory(laptop);

        // Tạo danh mục con cho Smartphone
        Category iphone = new Category();
        iphone.setName("iPhone");
        iphone.setDescription("iPhone smartphones");
        iphone.setParentCategory(smartphone);

        Category samsung = new Category();
        samsung.setName("Samsung");
        samsung.setDescription("Samsung smartphones");
        samsung.setParentCategory(smartphone);

        // Tạo danh mục con cho Computer
        Category msiComputer = new Category();
        msiComputer.setName("MSI");
        msiComputer.setDescription("MSI computers");
        msiComputer.setParentCategory(computer);

        Category asusComputer = new Category();
        asusComputer.setName("ASUS");
        asusComputer.setDescription("ASUS computers");
        asusComputer.setParentCategory(computer);

        // Lưu tất cả các danh mục con vào cơ sở dữ liệu
        categoryRepository.save(msiLaptop);
        categoryRepository.save(asusLaptop);
        categoryRepository.save(dellLaptop);
        categoryRepository.save(lgLaptop);
        categoryRepository.save(iphone);
        categoryRepository.save(samsung);
        categoryRepository.save(msiComputer);
        categoryRepository.save(asusComputer);
    }

}
