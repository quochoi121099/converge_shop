package com.studyprogramming.service;

import com.studyprogramming.entity.Product;
import com.studyprogramming.entity.Rating;
import com.studyprogramming.payload.ProductPayload;
import com.studyprogramming.repository.ProductRepository;
import com.studyprogramming.service.impl.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.studyprogramming.constant.ConstantImage.LINK_IMAGES_PRODUCT;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private FileStorageService fileStorageService;

    public Product createOrUpdate(ProductPayload product) throws IOException {
        Product existingProduct = productRepository.findByName(product.getName());

        // image
        String[] fileName = fileStorageService.storeProductFiles(product.getImages()).toArray(new String[0]);

        if(existingProduct == null){
            Product productEntity = new Product();
            productEntity.setImageNames(fileName);

            productEntity.setName(product.getName());
            productEntity.setDescription(product.getDescription());
            productEntity.setPrice(product.getPrice());
            productEntity.setQuantity(product.getQuantity());
            productEntity.setVendor(product.getVendor());
            productEntity.setTags(product.getTags());
            productEntity.setCategory(product.getCategory());
            return productRepository.save(productEntity);
        }else{
            existingProduct.setImageNames(fileName);

            existingProduct.setName(product.getName());
            existingProduct.setDescription(product.getDescription());
            existingProduct.setPrice(product.getPrice());
            existingProduct.setQuantity(product.getQuantity());
            existingProduct.setVendor(product.getVendor());
            existingProduct.setTags(product.getTags());
            existingProduct.setCategory(product.getCategory());
            return productRepository.save(existingProduct);
        }
    }

    public List<Product> findAll(){
        return productRepository.findAll();
    }

    public List<Product> getImageLink(List<Product> products) {
        return products.stream().map(this::addImageUrl).collect(Collectors.toList());
    }

    public Product addImageUrl(Product product) {
        String[] updatedImageNames = Arrays.stream(product.getImageNames())
                .map(imageName -> LINK_IMAGES_PRODUCT + imageName)
                .toArray(String[]::new);
        product.setImageNames(updatedImageNames);
        return product;
    }

    public List<Product> findByQuantityInStock(List<Product> productList) {
        return productList.stream()
                .filter(product -> product.getQuantity() > 0)
                .collect(Collectors.toList());
    }

    public List<Product> findByQuantityOutOfStock(List<Product> productList) {
        return productList.stream()
                .filter(product -> product.getQuantity() == 0)
                .collect(Collectors.toList());
    }

    public Product getById(UUID uuid){
        return productRepository.getReferenceById(uuid);
    }

    public List<Product> sortByUpdatedAt(List<Product> productList) {
        // Kiểm tra nếu danh sách sản phẩm không null và không rỗng
        if (productList != null && !productList.isEmpty()) {
            // Sử dụng comparator để sắp xếp theo thuộc tính updatedAt
            productList.sort(Comparator.comparing(Product::getUpdatedAt).reversed());
        }
        return productList;
    }

    public double getAverageRating(List<Rating> ratings) {
        return ratings.stream().mapToInt(Rating::getScore).average().orElse(0.0);
    }
}
