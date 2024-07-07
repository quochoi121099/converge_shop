package com.studyprogramming.repository;

import com.studyprogramming.entity.SaleCondition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SaleConditionRepository extends JpaRepository<SaleCondition, UUID> {
}
