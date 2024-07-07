package com.studyprogramming.repository;

import com.studyprogramming.entity.UsedSalesList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UsedSalesListRepository extends JpaRepository<UsedSalesList, UUID> {
}
