package com.studyprogramming.repository;

import com.studyprogramming.entity.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, UUID> {
    List<Wishlist> findAllByProductId(UUID uuid);
    List<Wishlist> findAllByUserId(UUID uuid);
    Wishlist findByProductIdAndUserId(UUID productId, UUID userId);
}