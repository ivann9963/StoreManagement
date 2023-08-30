package com.example.project.service;

import com.example.project.model.Cashier;
import com.example.project.model.Goods;
import com.example.project.model.Receipt;
import com.example.project.model.Shop;
import com.example.project.repository.CashierRepository;
import com.example.project.repository.GoodsRepository;
import com.example.project.repository.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Service
public class ShopServiceImpl implements ShopService {

    private final ShopRepository shopRepository;

    private final CashierRepository cashierRepository;

    private final GoodsRepository goodsRepository;

    @Autowired
    public ShopServiceImpl(ShopRepository shopRepository, CashierRepository cashierRepository, GoodsRepository goodsRepository) {
        this.shopRepository = shopRepository;
        this.cashierRepository = cashierRepository;
        this.goodsRepository = goodsRepository;
    }

    @Override
    public Iterable<Shop> getAllShops() {
        return shopRepository.findAll();
    }

    @Override
    public Optional<Shop> getShopById(UUID shopId) {
        return shopRepository.findById(shopId);
    }

    @Override
    public Shop saveShop(Shop shop) {
        return shopRepository.save(shop);
    }

    @Override
    public Iterable<Shop> saveAllShops(Iterable<Shop> shops) {
        return shopRepository.saveAll(shops);
    }

    @Override
    public void deleteShop(Shop shop) {
        shopRepository.delete(shop);
    }

    @Override
    public void deleteShopById(UUID shopId) {
        shopRepository.deleteById(shopId);
    }

    @Override
    public void addCashier(UUID shopId, Cashier cashier) throws Exception {
        try{
            Shop existingShop = shopRepository.findById(shopId).get();
            cashier.setShop(existingShop);
            cashierRepository.save(cashier);
            existingShop.getCashiers().add(cashier);
            shopRepository.save(existingShop);


        } catch (Exception e) {
            throw new Exception("Error adding the shop with the provided id!");
        }
    }

    @Override
    public void addGoods(UUID shopId, Goods goods) throws Exception {
        try{
            Shop existingShop = shopRepository.findById(shopId).get();
            goods.setShop(existingShop);
            goodsRepository.save(goods);
            existingShop.getGoods().add(goods);
            shopRepository.save(existingShop);

        } catch (Exception e) {
            throw new Exception("Error adding the goods with the provided id!");
        }
    }

}
