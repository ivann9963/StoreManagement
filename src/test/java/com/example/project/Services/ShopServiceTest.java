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
    private CashierWorkerRepository cashierWorkerRepository;

    @Mock
    private GoodsRepository goodsRepository;

    @Mock
    private ReceiptRepository receiptRepository;

    @InjectMocks
    private ShopServiceImpl shopService;

    private Shop shop;
    private Cashier cashier;
    private CashierWorker cashierWorker;
    private Goods goods;
    private Receipt receipt;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);

        shop = new Shop();
        cashier = new Cashier();
        cashierWorker = new CashierWorker();
        goods = new Goods();
        receipt = new Receipt();

        when(shopRepository.findById(anyLong())).thenReturn(Optional.of(shop));
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

    @Test
    public void testAddCashier() throws Exception {
        // Given
        Long shopId = 1L;
        Long cashierId = 1L;

        when(shopRepository.findById(shopId)).thenReturn(Optional.of(shop));
        when(cashierRepository.findById(cashierId)).thenReturn(Optional.of(cashier));

        // Action
        shopService.addCashier(shopId, cashierId);

        // Then
        assertEquals(cashier.getShop(), shop);
        assertTrue(shop.getCashiers().contains(cashier));

        verify(cashierRepository, times(1)).save(cashier);
        verify(shopRepository, times(1)).save(shop);
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
        // Given
        Long shopId = 1L;
        Long goodsId = 1L;

        when(shopRepository.findById(shopId)).thenReturn(Optional.of(shop));
        when(goodsRepository.findById(goodsId)).thenReturn(Optional.of(goods));

        // Action
        shopService.addGoods(shopId, goodsId);

        // Then
        assertEquals(goods.getShop(), shop);
        assertTrue(shop.getGoods().contains(goods));

        verify(goodsRepository, times(1)).save(goods);
        verify(shopRepository, times(1)).save(shop);
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
    public void testAddCashierWorker() throws Exception {
        // Given
        Long shopId = 1L;
        Long cashierWorkerId = 2L;

        when(shopRepository.findById(shopId)).thenReturn(Optional.of(shop));
        when(cashierWorkerRepository.findById(cashierWorkerId)).thenReturn(Optional.of(cashierWorker));

        // Action
        shopService.addCashierWorker(shopId, cashierWorkerId);

        // Then
        assertEquals(cashierWorker.getShop(), shop);
        assertTrue(shop.getCashierWorkers().contains(cashierWorker));

        verify(shopRepository, times(1)).save(shop);
        verify(cashierWorkerRepository, times(1)).save(cashierWorker);
    }

    @Test
    public void testAddGoods_GoodsNotFound() {
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
        // Given
        Long shopId = 1L;
        Long receiptId = 1L;

        when(shopRepository.findById(shopId)).thenReturn(Optional.of(shop));
        when(receiptRepository.findById(receiptId)).thenReturn(Optional.of(receipt));

        // Action
        shopService.addReceipt(shopId, receiptId);

        // Then
        assertEquals(receipt.getShop(), shop);
        assertTrue(shop.getReceipts().contains(receipt));

        verify(receiptRepository, times(1)).save(receipt);
        verify(shopRepository, times(1)).save(shop);
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
        when(shopRepository.findById(1L)).thenReturn(Optional.of(shop));
        when(receiptRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(Exception.class, () -> {
            shopService.addReceipt(1L, 1L);
        });

        verify(receiptRepository, never()).save(any());
        verify(shopRepository, never()).save(any());
    }

    @Test
    public void testSumCashierWorkersTotalSalaries() throws Exception {
        // Given
        CashierWorker cashierWorker1 = new CashierWorker();
        cashierWorker1.setMonthlySalary(1000);

        CashierWorker cashierWorker2= new CashierWorker();
        cashierWorker2.setMonthlySalary(2000);

        Shop shop = new Shop();
        shop.setCashierWorkers(Arrays.asList(cashierWorker1, cashierWorker2));

        when(shopRepository.findById(1L)).thenReturn(Optional.of(shop));

        // When
        double totalSalaries = shopService.sumCashierWorkersSalaries(1L);

        // Then
        assertEquals(3000, totalSalaries);
    }

    @Test
    public void testSumCashierWorkersTotalSalaries_ShopNotFound() {
        // Given
        when(shopRepository.findById(1L)).thenReturn(Optional.empty());

        // Then
        assertThrows(Exception.class, () -> shopService.sumCashierWorkersSalaries(1L));
    }

    @Test
    public void testMakeSale_Success() {
        // Given
        Long shopId = 1L, cashierWorkerId = 2L, goodsId = 3L;
        int quantity = 5;
        goods.setQuantity(10); // Enough stock
        goods.setActualPrice(20.0);

        when(shopRepository.findById(shopId)).thenReturn(Optional.of(shop));
        when(goodsRepository.findById(goodsId)).thenReturn(Optional.of(goods));
        when(shop.getCashierWorkers()).thenReturn(Arrays.asList(cashierWorker));

        // Action
        Receipt resultReceipt = shopService.makeSale(shopId, cashierWorkerId, goodsId, quantity);

        // Then
        assertNotNull(resultReceipt);
        assertEquals(100.0, resultReceipt.getTotalPrice()); // 5 * 20.0
        assertEquals(goods, resultReceipt.getGoods());
        assertEquals(quantity, resultReceipt.getQuantity());
    }

    @Test
    public void testMakeSale_GoodsNotFound() {
        // Given
        Long shopId = 1L, cashierWorkerId = 2L, goodsId = 3L;
        int quantity = 5;

        when(shopRepository.findById(shopId)).thenReturn(Optional.of(shop));
        when(goodsRepository.findById(goodsId)).thenReturn(Optional.empty());

        // Action & Assert
        assertThrows(RuntimeException.class, () -> {
            shopService.makeSale(shopId, cashierWorkerId, goodsId, quantity);
        });
    }

    @Test
    public void testMakeSale_CashierWorkerNotFound() {
        // Given
        Long shopId = 1L, cashierWorkerId = 2L, goodsId = 3L;
        int quantity = 5;

        when(shopRepository.findById(shopId)).thenReturn(Optional.of(shop));
        when(goodsRepository.findById(goodsId)).thenReturn(Optional.of(goods));

        // Action & Assert
        assertThrows(RuntimeException.class, () -> {
            shopService.makeSale(shopId, cashierWorkerId, goodsId, quantity);
        });
    }

    @Test
    public void testMakeSale_InsufficientStock() {
        // Given
        Long shopId = 1L, cashierWorkerId = 2L, goodsId = 3L;
        int quantity = 15; // Request more than in stock

        goods.setQuantity(10); // Only 10 in stock
        goods.setActualPrice(20.0);

        when(shopRepository.findById(shopId)).thenReturn(Optional.of(shop));
        when(goodsRepository.findById(goodsId)).thenReturn(Optional.of(goods));
        when(shop.getCashierWorkers()).thenReturn(Arrays.asList(cashierWorker));

        // Action & Assert
        assertThrows(RuntimeException.class, () -> {
            shopService.makeSale(shopId, cashierWorkerId, goodsId, quantity);
        });
    }
}
