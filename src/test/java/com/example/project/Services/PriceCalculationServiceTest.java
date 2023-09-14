package com.example.project.Services;

import com.example.project.model.*;
import com.example.project.service.PriceCalculationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PriceCalculationServiceTest {

    private PriceCalculationServiceImpl priceCalculationService;
    private Shop shop;

    @BeforeEach
    public void setup() {
        priceCalculationService = new PriceCalculationServiceImpl();
        shop = new Shop();
        shop.setStartingFoodPercentage(10.0);
        shop.setStartingNonFoodPercentage(5.0);
        shop.setPercentageIncreaseBefore7Days(2.0);
    }

    @Test
    public void testCalculatePriceForFoodGoodsWithin7Days() {
        Goods goods = new Goods();
        goods.setBasePrice(100.0);
        goods.setCategory(Category.FOODGOODS);
        goods.setExpirationDate(LocalDate.now().plusDays(3)); // 3 days from now

        priceCalculationService.calculatePrice(shop, goods);

        assertEquals(116.0, goods.getActualPrice()); // Base + 10% + 2% * 3 days
    }

    @Test
    public void testCalculatePriceForFoodGoodsBeyond7Days() {
        Goods goods = new Goods();
        goods.setBasePrice(100.0);
        goods.setCategory(Category.FOODGOODS);
        goods.setExpirationDate(LocalDate.now().plusDays(10)); // 10 days from now

        priceCalculationService.calculatePrice(shop, goods);

        assertEquals(110.0, goods.getActualPrice()); // Base + 10%
    }

    @Test
    public void testCalculatePriceForNonFoodGoodsWithin7Days() {
        Goods goods = new Goods();
        goods.setBasePrice(100.0);
        goods.setCategory(Category.NONFOODGOODS);
        goods.setExpirationDate(LocalDate.now().plusDays(5)); // 5 days from now

        priceCalculationService.calculatePrice(shop, goods);

        assertEquals(115.0, goods.getActualPrice()); // Base + 5% + 2% * 5 days
    }

    @Test
    public void testCalculatePriceForNonFoodGoodsWithoutExpiration() {
        Goods goods = new Goods();
        goods.setBasePrice(100.0);
        goods.setCategory(Category.NONFOODGOODS);

        priceCalculationService.calculatePrice(shop, goods);

        assertEquals(105.0, goods.getActualPrice()); // Base + 5%
    }
}
