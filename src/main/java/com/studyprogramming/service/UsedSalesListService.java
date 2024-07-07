package com.studyprogramming.service;

import com.studyprogramming.entity.UsedSalesList;
import com.studyprogramming.entity.enums.EObjectName;
import com.studyprogramming.repository.UsedSalesListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class UsedSalesListService {
    @Autowired
    private UsedSalesListRepository usedSalesListRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    public UsedSalesList saveUsedSalesList(UsedSalesList usedSalesList) {
        return usedSalesListRepository.save(usedSalesList);
    }

    public List<UsedSalesList> getAllUsedSalesLists() {
        return usedSalesListRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
    }

    public UsedSalesList getUsedSalesListById(UUID uuid) {
        return usedSalesListRepository.findById(uuid).orElse(null);
    }

    public void deleteUsedSalesList(UUID uuid) {
        usedSalesListRepository.deleteById(uuid);
    }

    public Map<UUID, String> getObjectNamesMap(List<UsedSalesList> usedSalesLists) {
        Map<UUID, String> objectsMap = new HashMap<>();
        for (UsedSalesList usedSalesList : usedSalesLists) {
            UUID idOfTheObject = usedSalesList.getIdOfTheObject();
            EObjectName objectName = usedSalesList.getSale().getSaleCondition().getObjectName();
            String name = "";

            switch (objectName) {
                case USER:
                    name = userService.getById(idOfTheObject).getUserName();
                    break;
                case PRODUCT:
                    name = productService.getById(idOfTheObject).getName();
                    break;
                case CATEGORY:
                    name = categoryService.getById(idOfTheObject).getName();
                    break;
                default:
                    name = "Unknown";
            }
            objectsMap.put(idOfTheObject, name);
        }
        return objectsMap;
    }
}