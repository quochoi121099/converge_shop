package com.studyprogramming.service;

import com.studyprogramming.entity.Product;
import com.studyprogramming.entity.Wishlist;
import com.studyprogramming.repository.ProductRepository;
import com.studyprogramming.repository.WishlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class WishlistService {
    @Autowired
    private WishlistRepository wishlistRepository;

    @Autowired
    private ProductRepository productRepository;

    public List<Wishlist> findAllByProductId(UUID uuid){
        return wishlistRepository.findAllByProductId(uuid);
    }

    public List<Wishlist> findAllByUserId(UUID uuid){
        return wishlistRepository.findAllByUserId(uuid);
    }

    public List<Product> findAllByProductIdAndUserId(List<Wishlist> allWishlist) {
        List<UUID> productIds = allWishlist.stream()
                .map(Wishlist::getProductId)
                .collect(Collectors.toList());
        return productRepository.findAllById(productIds);
    }

    public void deactivateWishlistItem(UUID productId, UUID userId) {
        Wishlist wishlist = wishlistRepository.findByProductIdAndUserId(productId, userId);
        if(wishlist.isActive()){
            wishlist.setActive(false);
        }else{
            wishlist.setActive(true);
        }

        wishlistRepository.save(wishlist);
    }

    public void addToWishlist(UUID productId, UUID userId){
        Wishlist wishlist = new Wishlist();
        wishlist.setActive(true);
        wishlist.setProductId(productId);
        wishlist.setUserId(userId);
        wishlistRepository.save(wishlist);
    }

    public Wishlist findByProductIdAndUserId(UUID productId, UUID userId){
        return wishlistRepository.findByProductIdAndUserId(productId, userId);
    }
}
