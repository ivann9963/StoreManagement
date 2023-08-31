package com.example.project.service;

import com.example.project.model.Cashier;
import com.example.project.model.Goods;
import com.example.project.model.Receipt;
import com.example.project.model.Shop;

import java.util.Optional;
import java.util.UUID;

public interface ShopService {
    Iterable<Shop> getAllShops();
    Optional<Shop> getShopById(UUID shopId);
    Shop saveShop(Shop shop);
    Iterable<Shop> saveAllShops(Iterable<Shop> shops);
    void deleteShop(Shop shop);
    void deleteShopById(UUID shopId);

    void addCashier(UUID shopId, UUID cashierId) throws Exception;
    void addGoods(UUID shopId, UUID goodsId) throws Exception;

    void addReceipt(UUID shopId, UUID receiptId) throws Exception;
}
