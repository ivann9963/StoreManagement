package com.example.project.controller;

import com.example.project.model.Cashier;
import com.example.project.model.Goods;
import com.example.project.model.Receipt;
import com.example.project.model.Shop;
import com.example.project.service.ReceiptService;
import com.example.project.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/shop")
public class ShopController {

    @Autowired
    private ShopService shopService;

    @GetMapping
    public Iterable<Shop> getAllShops() {
        return shopService.getAllShops();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addReceipt(@RequestBody Shop shop) {
        shopService.saveShop(shop);
    }

    @GetMapping("/{shopId}")
    public Optional<Shop> getShopById(@PathVariable Long shopId) {
        return shopService.getShopById(shopId);
    }

    @DeleteMapping("/{shopId}")
    public void deleteShopByID(@PathVariable Long shopId) {
        shopService.deleteShopById(shopId);
    }

    @GetMapping("/{shopId}/totalSalaries")
    public void sumCashierWorkersSalaries(@PathVariable Long shopId) throws Exception { shopService.sumCashierWorkersSalaries(shopId); }

    @PostMapping("/{shopId}/cashier/{cashierId}")
    public void addCashier(@PathVariable Long shopId, @PathVariable Long cashierId) throws Exception { shopService.addCashier(shopId, cashierId);}

    @PostMapping("/{shopId}/goods/{goodsId}")
    public void addGoods(@PathVariable Long shopId, @PathVariable Long goodsId) throws Exception { shopService.addGoods(shopId, goodsId); }

    @PostMapping("/{shopId}/receipt/{receiptId}")
    public void addReceipt(@PathVariable Long shopId, @PathVariable Long receiptId) throws Exception { shopService.addReceipt(shopId, receiptId); }
    @PostMapping("/{shopId}/cashierWorker/{cashierWorkerId}")
    public void addCashierWorker(@PathVariable Long shopId, @PathVariable Long cashierWorkerId) throws Exception { shopService.addCashierWorker(shopId, cashierWorkerId); }
}
