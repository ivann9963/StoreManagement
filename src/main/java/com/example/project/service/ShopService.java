package com.example.project.service;

import com.example.project.model.Receipt;
import com.example.project.model.Shop;

import java.util.Optional;

public interface ShopService {
    Iterable<Shop> getAllShops();
    Optional<Shop> getShopById(Long shopId);
    Shop saveShop(Shop shop);
    Iterable<Shop> saveAllShops(Iterable<Shop> shops);
    void deleteShop(Shop shop);
    void deleteShopById(Long shopId);
    void addCashier(Long shopId, Long cashierId) throws Exception;
    void addGoods(Long shopId, Long goodsId) throws Exception;
    void addReceipt(Long shopId, Long receiptId) throws Exception;
    void addCashierWorker(Long shopId, Long cashierWorkerId) throws Exception;
    double sumCashierWorkersSalaries(Long shopId) throws Exception;
    Receipt makeSale(Long shopId, Long cashierWorkerId, Long goodsId, int quantity);
}
