package com.example.project.service;

import com.example.project.model.Goods;

import java.util.Optional;
import java.util.UUID;

public interface GoodsService {

    Iterable<Goods> getAllGoods();

    Optional<Goods> getGoodsById(UUID goodsId);

    Goods saveGoods(Goods goods);

    Iterable<Goods> saveAllGoods(Iterable<Goods> goods);

    void deleteGoods(Goods goods);

    void deleteGoodsById(UUID goodsId);

}
