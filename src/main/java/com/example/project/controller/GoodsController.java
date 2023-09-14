package com.example.project.controller;

import com.example.project.model.Goods;
import com.example.project.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @GetMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Iterable<Goods> getGoods() { return goodsService.getAllGoods(); }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addCashier(@RequestBody Goods goods) { goodsService.saveGoods(goods)  ; }

    @GetMapping("/{goodsId}")
    @ResponseStatus(HttpStatus.CREATED)
    public Optional<Goods> getGoodsById(@PathVariable Long goodsId) { return goodsService.getGoodsById(goodsId); }

    @DeleteMapping("/{goodsId}")
    public void deleteGoodsById(@PathVariable Long goodsId) {
        goodsService.deleteGoodsById(goodsId);
    }
}
