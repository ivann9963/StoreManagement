package com.example.project.service;

import com.example.project.model.Goods;
import com.example.project.model.Shop;

import java.math.BigDecimal;

public interface PriceCalculationService {
    void calculatePrice(Shop shop, Goods goods);
}