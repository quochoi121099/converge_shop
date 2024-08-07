package com.studyprogramming.repository;

import com.studyprogramming.entity.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SpecificationRepository extends JpaRepository<Specification, UUID> {
}
