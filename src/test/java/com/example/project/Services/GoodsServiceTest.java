package com.example.project.Services;

import com.example.project.model.Goods;
import com.example.project.repository.GoodsRepository;
import com.example.project.service.GoodsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GoodsServiceTest {

    @Mock
    private GoodsRepository goodsRepository;

    @InjectMocks
    private GoodsServiceImpl goodsService;

    private Goods sampleGoods;

    @BeforeEach
    public void setup() {
        sampleGoods = new Goods();
        // Set any sample attributes if necessary
        // sampleGoods.setAttributeName(attributeValue);
    }

    @Test
    public void testGetAllGoods() {
        when(goodsRepository.findAll()).thenReturn(Arrays.asList(sampleGoods));
        goodsService.getAllGoods();
        verify(goodsRepository).findAll();
    }

    @Test
    public void testGetGoodsById() {
        Long id = 1L;
        when(goodsRepository.findById(id)).thenReturn(Optional.of(sampleGoods));
        goodsService.getGoodsById(id);
        verify(goodsRepository).findById(id);
    }

    @Test
    public void testSaveGoods() {
        when(goodsRepository.save(sampleGoods)).thenReturn(sampleGoods);
        goodsService.saveGoods(sampleGoods);
        verify(goodsRepository).save(sampleGoods);
    }

    @Test
    public void testSaveAllGoods() {
        when(goodsRepository.saveAll(Arrays.asList(sampleGoods))).thenReturn(Arrays.asList(sampleGoods));
        goodsService.saveAllGoods(Arrays.asList(sampleGoods));
        verify(goodsRepository).saveAll(Arrays.asList(sampleGoods));
    }

    @Test
    public void testDeleteGoods() {
        Mockito.doNothing().when(goodsRepository).delete(sampleGoods);
        goodsService.deleteGoods(sampleGoods);
        verify(goodsRepository).delete(sampleGoods);
    }

    @Test
    public void testDeleteGoodsById() {
        Long id = 1L;
        Mockito.doNothing().when(goodsRepository).deleteById(id);
        goodsService.deleteGoodsById(id);
        verify(goodsRepository).deleteById(id);
    }
}
