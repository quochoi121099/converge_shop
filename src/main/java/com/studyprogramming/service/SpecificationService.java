package com.studyprogramming.service;

import com.studyprogramming.entity.Specification;
import com.studyprogramming.repository.SpecificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SpecificationService {
    @Autowired
    private SpecificationRepository specificationRepository;

    public Specification getById(UUID uuid) {
        return specificationRepository.getReferenceById(uuid);
    }

}
