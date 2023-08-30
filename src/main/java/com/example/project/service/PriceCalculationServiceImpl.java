package com.example.project.service;

import com.example.project.model.Category;
import com.example.project.model.Goods;
import com.example.project.model.Shop;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
public class PriceCalculationServiceImpl {

    public void calculatePrice(Shop shop, Goods goods) {
        Double basePrice = goods.getBasePrice();
        Category category = goods.getCategory();
        LocalDate expirationDate = goods.getExpirationDate();

        // Calculate markup percentage based on category and expiration date
        Double markupPercentage = determineMarkupPercentage(category, expirationDate, shop.getStartingFoodPercentage(), shop.getStartingFoodPercentage(), shop.getPercentageIncreaseBefore7Days());

        // Apply markup percentage to base price
        Double markupAmount = basePrice * markupPercentage;

        goods.setActualPrice(basePrice + markupAmount);
    }

    private Double determineMarkupPercentage(Category category, LocalDate expirationDate,
                                             Double startingFoodPercentage, Double startingNonFoodPercentage, Double percentageIncreaseBefore7Days) {
        Double markupPercentage = 0.0;

        // Step 1: Determine base markup percentage based on category
        if (category == Category.FOODGOODS) {
            markupPercentage += startingFoodPercentage;
        } else if (category == Category.NONFOODGOODS) {
            markupPercentage += startingNonFoodPercentage;
        }

        // Step 2: Determine additional markup percentage based on expiration date
        if (expirationDate != null) {
            long daysUntilExpiration = ChronoUnit.DAYS.between(LocalDate.now(), expirationDate);

            // Calculate additional markup based on days until expiration
            if (daysUntilExpiration <= 7) {
                Double additionalMarkup = daysUntilExpiration * percentageIncreaseBefore7Days;
                markupPercentage += additionalMarkup;
            }
        }

        // Ensure the markup percentage is within reasonable bounds
        markupPercentage = Math.min(markupPercentage, 100.0);  // Limit to 100% maximum

        return markupPercentage / 100.0; // Convert to decimal (e.g., 0.05 for 5%)
    }


}