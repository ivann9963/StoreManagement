package com.example.project.Services;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.example.project.model.*;
import com.example.project.repository.*;
import com.example.project.service.PriceCalculationServiceImpl;
import com.example.project.service.ShopServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
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
    @InjectMocks
    private PriceCalculationServiceImpl priceCalculationService;

    private Shop shop;
    private Cashier cashier;
    private CashierWorker cashierWorker;
    private List<Goods> goodsList;
    private Goods singleGood;

    private Receipt receipt;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);

        shop = new Shop();
        shop.setStartingFoodPercentage(0.03);
        shop.setPercentageIncreaseBefore7Days(0.1);
        cashier = new Cashier();
        cashierWorker = new CashierWorker();
        singleGood = new Goods();
        goodsList = new ArrayList<>();
        receipt = new Receipt();

        initializeGoodsList();

        when(shopRepository.findById(anyLong())).thenReturn(Optional.of(shop));
        when(goodsRepository.findAll()).thenReturn(goodsList);
    }
    private List<Long> getGoodsIdsFromGoodsList() {
        List<Long> goodsIds = new ArrayList<>();
        for (Goods goods : goodsList) {
            goodsIds.add(goods.getGoodsId());
        }
        return goodsIds;
    }

    private void initializeGoodsList() {
        Goods foodItemExpiringSoon = new Goods();
        foodItemExpiringSoon.setQuantity(112);
        foodItemExpiringSoon.setName("Milk");
        foodItemExpiringSoon.setExpirationDate(LocalDate.now().plusDays(2));
        foodItemExpiringSoon.setCategory(Category.FOODGOODS);

        Goods nonFoodItem = new Goods();
        nonFoodItem.setQuantity(4);
        nonFoodItem.setName("Shampoo");
        nonFoodItem.setCategory(Category.NONFOODGOODS);

        Goods foodItemNotExpiringSoon = new Goods();
        foodItemNotExpiringSoon.setQuantity(4);
        foodItemNotExpiringSoon.setName("Canned Beans");
        foodItemNotExpiringSoon.setExpirationDate(LocalDate.now().plusMonths(6));
        foodItemNotExpiringSoon.setCategory(Category.FOODGOODS);

        Goods anotherFoodItemExpiringSoon = new Goods();
        anotherFoodItemExpiringSoon.setQuantity(4);
        anotherFoodItemExpiringSoon.setName("Cheese");
        anotherFoodItemExpiringSoon.setExpirationDate(LocalDate.now().plusDays(5));
        anotherFoodItemExpiringSoon.setCategory(Category.FOODGOODS);

        Goods anotherNonFoodItem = new Goods();
        anotherNonFoodItem.setQuantity(4);
        anotherNonFoodItem.setName("Detergent");
        anotherNonFoodItem.setCategory(Category.NONFOODGOODS);

        foodItemExpiringSoon.setGoodsId(1L);
        nonFoodItem.setGoodsId(2L);
        foodItemNotExpiringSoon.setGoodsId(3L);
        anotherFoodItemExpiringSoon.setGoodsId(4L);
        anotherNonFoodItem.setGoodsId(5L);

        goodsList = Arrays.asList(foodItemExpiringSoon, nonFoodItem, foodItemNotExpiringSoon, anotherFoodItemExpiringSoon, anotherNonFoodItem);
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
        when(goodsRepository.findById(goodsId)).thenReturn(Optional.of(singleGood));

        // Action
        shopService.addGoods(shopId, goodsId);

        // Then
        assertEquals(singleGood.getShop(), shop);
        assertTrue(shop.getGoods().contains(singleGood));

        verify(goodsRepository, times(1)).save(singleGood);
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
    public void testMakeSale_Success() throws Exception {
        // Given
        CashierWorker cashierWorker1 = new CashierWorker();
        cashierWorker1.setCashierWorkerId(1L);
        CashierWorker cashierWorker2 = new CashierWorker();
        cashierWorker2.setCashierWorkerId(2L); // Setting ID to match cashierWorkerId

        List<CashierWorker> cashierWorkers = Arrays.asList(cashierWorker1, cashierWorker2);

        Long shopId = 1L, cashierWorkerId = 2L;
        List<Long> goodsIds = getGoodsIdsFromGoodsList();
        List<Integer> quantities = Arrays.asList(15, 23, 18, 41, 20);

        singleGood.setQuantity(10); // Enough stock
        singleGood.setActualPrice(20.0);
        shop.setCashierWorkers(cashierWorkers);

        when(shopRepository.findById(shopId)).thenReturn(Optional.of(shop));
        for (Goods goods : goodsList) {
            when(goodsRepository.findById(goods.getGoodsId())).thenReturn(Optional.of(goods));
        } // Corresponding to the goodsId in the list
        when(receiptRepository.save(any(Receipt.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Action
        Receipt resultReceipt = shopService.makeSale(shopId, cashierWorkerId, goodsIds, quantities);

        List<Receipt> expectedReceipts = new ArrayList<>();
        expectedReceipts.add(resultReceipt);
        when(shopRepository.getAllReceipts(shopId)).thenReturn(expectedReceipts);

        // Then
        // Assert the receipt is added to the list of receipts
        List<Receipt> allReceipts = shopRepository.getAllReceipts(shopId); // Replace with your actual method to fetch receipts
        assertTrue(allReceipts.contains(resultReceipt));

        // Assert the quantity of singleGood is reduced
        assertEquals(5, singleGood.getQuantity());

        // Assert the total amount in the receipt
        assertEquals(100.0, resultReceipt.getTotalPrice()); // 5 * 20.0
        assertEquals(singleGood, resultReceipt.getGoods().get(0));
        assertEquals(5, resultReceipt.getQuantity().get(0).intValue());

        // Assert the date and time
        assertNotNull(resultReceipt.getDate());

        // Assert cashierWorker and other details
        assertEquals(cashierWorker2, resultReceipt.getCashierWorker());
    }

    @Test
    public void testMakeSale_GoodsNotFound() {
        // Given
        Long shopId = 1L, cashierWorkerId = 2L;
        List<Long> goodsIds = getGoodsIdsFromGoodsList();
        List<Integer> quantities = Arrays.asList(15, 23, 18, 41, 20);

        when(shopRepository.findById(shopId)).thenReturn(Optional.of(shop));
        when(goodsRepository.findById(3L)).thenReturn(Optional.empty());

        // Action & Assert
        assertThrows(RuntimeException.class, () -> {
            shopService.makeSale(shopId, cashierWorkerId, goodsIds, quantities);
        });
    }

    @Test
    public void testMakeSale_CashierWorkerNotFound() {
        // Given
        Long shopId = 1L, cashierWorkerId = 2L;
        List<Long> goodsIds = getGoodsIdsFromGoodsList();
        List<Integer> quantities = Arrays.asList(15, 23, 18, 41, 20);

        when(shopRepository.findById(shopId)).thenReturn(Optional.of(shop));
        when(goodsRepository.findById(3L)).thenReturn(Optional.of(singleGood));

        // Action & Assert
        assertThrows(RuntimeException.class, () -> {
            shopService.makeSale(shopId, cashierWorkerId, goodsIds, quantities);
        });
    }

    @Test
    public void testMakeSale_InsufficientStock() {
        // Given
        Long shopId = 1L, cashierWorkerId = 2L;
        List<Long> goodsIds = getGoodsIdsFromGoodsList();
        List<Integer> quantities = Arrays.asList(15, 23, 18, 41, 20); // Request more than in stock

        singleGood.setQuantity(10); // Only 10 in stock
        singleGood.setActualPrice(20.0);

        when(shopRepository.findById(shopId)).thenReturn(Optional.of(shop));
        when(goodsRepository.findById(3L)).thenReturn(Optional.of(singleGood));

        // Action & Assert
        assertThrows(RuntimeException.class, () -> {
            shopService.makeSale(shopId, cashierWorkerId, goodsIds, quantities);
        });
    }
}
