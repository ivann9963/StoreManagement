package com.example.project.service;

import com.example.project.model.Cashier;
import com.example.project.model.Goods;
import com.example.project.model.Receipt;
import com.example.project.model.Shop;
import com.example.project.repository.CashierRepository;
import com.example.project.repository.GoodsRepository;
import com.example.project.repository.ReceiptRepository;
import com.example.project.repository.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
public class ShopServiceImpl implements ShopService {

    private final ShopRepository shopRepository;

    private final CashierRepository cashierRepository;

    private final GoodsRepository goodsRepository;

    private final ReceiptRepository receiptRepository;

    @Autowired
    public ShopServiceImpl(ShopRepository shopRepository, CashierRepository cashierRepository,
                           GoodsRepository goodsRepository, ReceiptRepository receiptRepository) {
        this.shopRepository = shopRepository;
        this.cashierRepository = cashierRepository;
        this.goodsRepository = goodsRepository;
        this.receiptRepository = receiptRepository;
    }

    @Override
    public Iterable<Shop> getAllShops() {
        return shopRepository.findAll();
    }

    @Override
    public Optional<Shop> getShopById(Long shopId) {
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
    public void deleteShopById(Long shopId) {
        shopRepository.deleteById(shopId);
    }

    @Override
    public void addCashier(Long shopId, Long cashierId) throws Exception {
        try{
            Shop existingShop = shopRepository.findById(shopId).get();
            Cashier cashier = cashierRepository.findById(cashierId).get();
            if(cashier != null ){
                cashier.setShop(existingShop);
                cashierRepository.save(cashier);
                existingShop.getCashiers().add(cashier);
            } else {
                throw new Exception("Cashier with the provided id does not exist!");
            }
            shopRepository.save(existingShop);


        } catch (Exception e) {
            throw new Exception("Error adding the shop with the provided id!");
        }
    }

    @Override
    public void addGoods(Long shopId, Long goodsId) throws Exception {
        try{
            Shop existingShop = shopRepository.findById(shopId).get();
            Goods goods = goodsRepository.findById(goodsId).get();
            if(goods != null){
                goods.setShop(existingShop);
                goodsRepository.save(goods);
                existingShop.getGoods().add(goods);
            } else {
                throw new Exception("Goods with the provided id does not exist!");
            }
            shopRepository.save(existingShop);

        } catch (Exception e) {
            throw new Exception("Error adding the goods with the provided id!");
        }
    }

    @Override
    public void addReceipt(Long shopId, Long receiptId) throws Exception {
        try{
            Shop existingShop = shopRepository.findById(shopId).get();
            Receipt receipt = receiptRepository.findById(receiptId).get();
            if(receipt != null){
                receipt.setShop(existingShop);
                receiptRepository.save(receipt);
                existingShop.getReceipts().add(receipt);
            } else {
                throw new Exception("Receipt with the provided id does not exist!");
            }
            shopRepository.save(existingShop);
        } catch (Exception e) {
            throw new Exception("Error adding the goods with the provided id!");
        }
    }

}
