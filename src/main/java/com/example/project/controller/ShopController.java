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
    public Optional<Shop> getShopById(@PathVariable UUID shopId) {
        return shopService.getShopById(shopId);
    }

    @DeleteMapping("/{shopId}")
    public void deleteShopByID(@PathVariable UUID shopId) {
        shopService.deleteShopById(shopId);
    }

    @PostMapping("/{shopId}/cashier")
    public void addCashier(@PathVariable UUID shopId, @RequestBody Cashier cashier) throws Exception { shopService.addCashier(shopId, cashier);}

    @PostMapping("/{shopId}/goods")
    public void addGoods(@PathVariable UUID shopId, @RequestBody Goods goods) throws Exception { shopService.addGoods(shopId, goods); }
}