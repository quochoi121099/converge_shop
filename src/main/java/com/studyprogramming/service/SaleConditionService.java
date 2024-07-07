package com.studyprogramming.service;

import com.studyprogramming.entity.SaleCondition;
import com.studyprogramming.repository.SaleConditionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SaleConditionService {
    @Autowired
    private SaleConditionRepository saleConditionRepository;

    public SaleCondition save(SaleCondition saleCondition) {
        return saleConditionRepository.save(saleCondition);
    }

    public List<SaleCondition> getAll() {
        return saleConditionRepository.findAll();
    }
}
