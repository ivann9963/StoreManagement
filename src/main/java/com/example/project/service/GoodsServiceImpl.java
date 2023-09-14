package com.example.project.service;

import com.example.project.model.Goods;
import com.example.project.repository.GoodsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class GoodsServiceImpl implements GoodsService {

    private final GoodsRepository goodsRepository;

    @Autowired
    public GoodsServiceImpl(GoodsRepository goodsRepository) {
        this.goodsRepository = goodsRepository;
    }

    @Override
    public Iterable<Goods> getAllGoods() {
        return goodsRepository.findAll();
    }

    @Override
    public Optional<Goods> getGoodsById(Long goodsId) {
        return goodsRepository.findById(goodsId);
    }

    @Override
    public Goods saveGoods(Goods goods) {
        return goodsRepository.save(goods);
    }

    @Override
    public Iterable<Goods> saveAllGoods(Iterable<Goods> goods) {
        return goodsRepository.saveAll(goods);
    }

    @Override
    public void deleteGoods(Goods goods) {
        goodsRepository.delete(goods);
    }

    @Override
    public void deleteGoodsById(Long goodsId) {
        goodsRepository.deleteById(goodsId);
    }


}
