package com.example.project.Services;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.example.project.model.*;
import com.example.project.repository.*;
import com.example.project.service.ShopServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ShopServiceTest {

    @Mock
    private ShopRepository shopRepository;

    @Mock
    private CashierRepository cashierRepository;

    @Mock
    private GoodsRepository goodsRepository;

    @Mock
    private ReceiptRepository receiptRepository;

    @InjectMocks
    private ShopServiceImpl shopService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllShops() {
        Shop shop1 = new Shop();
        Shop shop2 = new Shop();
        when(shopRepository.findAll()).thenReturn(Arrays.asList(shop1, shop2));

        Iterable<Shop> result = shopService.getAllShops();
        assertEquals(2, ((List<Shop>) result).size());
    }

    @Test
    public void testGetShopById() {
        Shop shop = new Shop();
        when(shopRepository.findById(1L)).thenReturn(Optional.of(shop));

        Optional<Shop> result = shopService.getShopById(1L);
        assertTrue(result.isPresent());
        assertEquals(shop, result.get());
    }

    @Test
    public void testGetShopById_NotFound() {
        when(shopRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Shop> result = shopService.getShopById(1L);
        assertFalse(result.isPresent());
    }

    // ... Additional test methods for other methods ...

    @Test
    public void testAddCashier() throws Exception {
        Shop shop = new Shop();
        Cashier cashier = new Cashier();
        when(shopRepository.findById(1L)).thenReturn(Optional.of(shop));
        when(cashierRepository.findById(1L)).thenReturn(Optional.of(cashier));

        shopService.addCashier(1L, 1L);

        verify(cashierRepository).save(cashier);
        verify(shopRepository).save(shop);
    }

    @Test
    public void testAddCashier_ShopNotFound() {
        when(shopRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(Exception.class, () -> {
            shopService.addCashier(1L, 1L);
        });

        verify(cashierRepository, never()).save(any());
        verify(shopRepository, never()).save(any());
    }

    @Test
    public void testAddCashier_CashierNotFound() {
        Shop shop = new Shop();
        when(shopRepository.findById(1L)).thenReturn(Optional.of(shop));
        when(cashierRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(Exception.class, () -> {
            shopService.addCashier(1L, 1L);
        });

        verify(cashierRepository, never()).save(any());
        verify(shopRepository, never()).save(any());
    }

    @Test
    public void testAddGoods() throws Exception {
        Shop shop = new Shop();
        Goods goods = new Goods();
        when(shopRepository.findById(1L)).thenReturn(Optional.of(shop));
        when(goodsRepository.findById(1L)).thenReturn(Optional.of(goods));

        shopService.addGoods(1L, 1L);

        verify(goodsRepository).save(goods);
        verify(shopRepository).save(shop);
    }

    @Test
    public void testAddGoods_ShopNotFound() {
        when(shopRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(Exception.class, () -> {
            shopService.addGoods(1L, 1L);
        });

        verify(goodsRepository, never()).save(any());
        verify(shopRepository, never()).save(any());
    }

    @Test
    public void testAddGoods_GoodsNotFound() {
        Shop shop = new Shop();
        when(shopRepository.findById(1L)).thenReturn(Optional.of(shop));
        when(goodsRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(Exception.class, () -> {
            shopService.addGoods(1L, 1L);
        });

        verify(goodsRepository, never()).save(any());
        verify(shopRepository, never()).save(any());
    }

    @Test
    public void testAddReceipt() throws Exception {
        Shop shop = new Shop();
        Receipt receipt = new Receipt();
        when(shopRepository.findById(1L)).thenReturn(Optional.of(shop));
        when(receiptRepository.findById(1L)).thenReturn(Optional.of(receipt));

        shopService.addReceipt(1L, 1L);

        verify(receiptRepository).save(receipt);
        verify(shopRepository).save(shop);
    }

    @Test
    public void testAddReceipt_ShopNotFound() {
        when(shopRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(Exception.class, () -> {
            shopService.addReceipt(1L, 1L);
        });

        verify(receiptRepository, never()).save(any());
        verify(shopRepository, never()).save(any());
    }

    @Test
    public void testAddReceipt_ReceiptNotFound() {
        Shop shop = new Shop();
        when(shopRepository.findById(1L)).thenReturn(Optional.of(shop));
        when(receiptRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(Exception.class, () -> {
            shopService.addReceipt(1L, 1L);
        });

        verify(receiptRepository, never()).save(any());
        verify(shopRepository, never()).save(any());
    }

}
