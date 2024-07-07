package com.studyprogramming.repository;

import com.studyprogramming.entity.Sale;
import com.studyprogramming.entity.enums.EObjectName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface SaleRepository extends JpaRepository<Sale, UUID> {

    @Query("SELECT s FROM Sale s WHERE s.endDate > :currentDate AND s.saleCondition.objectName = :objectName")
    List<Sale> findAllByEndDateAfterAndSaleConditionObjectName(@Param("currentDate") LocalDate currentDate, @Param("objectName") EObjectName objectName);

    List<Sale> findByEndDateAfter(LocalDate date);
}
