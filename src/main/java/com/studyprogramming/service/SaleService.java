package com.studyprogramming.service;

import com.studyprogramming.entity.Sale;
import com.studyprogramming.entity.enums.EObjectName;
import com.studyprogramming.payload.SalePayload;
import com.studyprogramming.repository.SaleRepository;
import com.studyprogramming.service.impl.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class SaleService {

    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private FileStorageService fileStorageService;

    public Sale saveSale(SalePayload salePayload) throws IOException {
        Sale sale = new Sale();
        sale.setName(salePayload.getName());
        sale.setDiscountPercentage(salePayload.getDiscountPercentage());
        sale.setStartDate(salePayload.getStartDate());
        sale.setEndDate(salePayload.getEndDate());

        MultipartFile[] imageFiles = salePayload.getImageFiles();
        List<String> imageNames = fileStorageService.storeSaleFiles(imageFiles);
        sale.setImageName(imageNames.toArray(new String[0]));
        sale.setSaleCondition(salePayload.getSaleCondition());

        return saleRepository.save(sale);
    }

    public List<Sale> getAllSales() {
        return saleRepository.findAll();
    }

    public List<Sale> getAllSalesByEndDateAndSaleConditionObjectName(EObjectName objectName) {
        LocalDate currentDate = LocalDate.now();
        return saleRepository.findAllByEndDateAfterAndSaleConditionObjectName(currentDate, objectName);
    }

    public List<Sale> getAllSalesStillValid() {
        LocalDate today = LocalDate.now();
        return saleRepository.findByEndDateAfter(today);
    }

    public Sale findById(UUID uuid) {
        return saleRepository.getReferenceById(uuid);
    }
}



