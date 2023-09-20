package com.example.project.service;

import com.example.project.model.Cashier;
import com.example.project.model.CashierWorker;
import com.example.project.model.Goods;
import com.example.project.model.Receipt;
import com.example.project.model.Shop;
import com.example.project.repository.CashierRepository;
import com.example.project.repository.CashierWorkerRepository;
import com.example.project.repository.GoodsRepository;
import com.example.project.repository.ReceiptRepository;
import com.example.project.repository.ShopRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ShopServiceImpl implements ShopService {
    private final ShopRepository shopRepository;
    private final CashierRepository cashierRepository;
    private final GoodsRepository goodsRepository;
    private final ReceiptRepository receiptRepository;
    private final CashierWorkerRepository cashierWorkerRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    // Counters for receipts and turnover
    private int totalReceiptsIssued = 0;
    private double totalTurnover = 0;
    private PriceCalculationServiceImpl priceCalculationService = new PriceCalculationServiceImpl();

    @Autowired
    public ShopServiceImpl(ShopRepository shopRepository, CashierRepository cashierRepository,
                           GoodsRepository goodsRepository, ReceiptRepository receiptRepository,
                           CashierWorkerRepository cashierWorkerRepository) {
        this.shopRepository = shopRepository;
        this.cashierRepository = cashierRepository;
        this.goodsRepository = goodsRepository;
        this.receiptRepository = receiptRepository;
        this.cashierWorkerRepository = cashierWorkerRepository;
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
        Shop shop = fetchShop(shopId);
        Cashier cashier = fetchEntityById(cashierId, cashierRepository, "Cashier");

        cashier.setShop(shop);
        cashierRepository.save(cashier);
        shop.getCashiers().add(cashier);
        shopRepository.save(shop);
    }

    @Override
    public void addGoods(Long shopId, Long goodsId) throws Exception {
        Shop shop = fetchShop(shopId);
        Goods goods = fetchEntityById(goodsId, goodsRepository, "Goods");

        goods.setShop(shop);
        goodsRepository.save(goods);
        shop.getGoods().add(goods);
        shopRepository.save(shop);
    }

    @Override
    public void addReceipt(Long shopId, Long receiptId) throws Exception {
        Shop shop = fetchShop(shopId);
        Receipt receipt = fetchEntityById(receiptId, receiptRepository, "Receipt");

        receipt.setShop(shop);
        receiptRepository.save(receipt);
        shop.getReceipts().add(receipt);
        shopRepository.save(shop);
    }

    @Override
    public void addCashierWorker(Long shopId, Long cashierWorkerId) throws Exception {
        Shop shop = fetchShop(shopId);
        CashierWorker cashierWorker = fetchEntityById(cashierWorkerId, cashierWorkerRepository, "CashierWorker");

        cashierWorker.setShop(shop);
        cashierWorkerRepository.save(cashierWorker);
        shop.getCashierWorkers().add(cashierWorker);
        shopRepository.save(shop);
    }

    @Override
    public List<Receipt> getAllReceipts(Long shopId) throws Exception {
        Shop shop = fetchShop(shopId);
        return shop.getReceipts();
    }

    @Override
    public double sumCashierWorkersSalaries(Long shopId) throws Exception {
        Shop shop = fetchShop(shopId);
        return shop.getCashierWorkers()
                .stream()
                .mapToDouble(CashierWorker::getMonthlySalary)
                .sum();
    }
    @Transactional
    @Override
    public Receipt makeSale(Long shopId, Long cashierWorkerId, List<Long> goodsIds, List<Integer> quantities) {
        validateInputLists(goodsIds, quantities);

        Shop shop = fetchShop(shopId);
        CashierWorker cashierWorker = fetchCashierWorker(shop, cashierWorkerId);
        List<Goods> goodsList = fetchGoodsAndUpdateQuantity(goodsIds, quantities, shop);
        Receipt receipt = createAndSaveReceipt(shop, cashierWorker, goodsList, quantities);
        saveReceiptToFile(receipt);
        return receipt;
    }

    private void validateInputLists(List<Long> goodsIds, List<Integer> quantities) {
        if (goodsIds == null || quantities == null || goodsIds.size() != quantities.size()) {
            throw new IllegalArgumentException("Goods IDs and quantities lists must be of the same size and non-null");
        }
    }

    // This generic helper method fetches an entity based on its ID and throws an exception if it's not found
    private <T> T fetchEntityById(Long id, JpaRepository<T, Long> repository, String entityName) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException(entityName + " not found"));
    }

    private Shop fetchShop(Long shopId) {
        return shopRepository.findById(shopId).orElseThrow(() -> new RuntimeException("Shop not found"));
    }

    private CashierWorker fetchCashierWorker(Shop shop, Long cashierWorkerId) {
        return shop.getCashierWorkers().stream()
                .filter(cw -> cw.getCashierWorkerId().equals(cashierWorkerId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("CashierWorker not found in the shop"));
    }

    private List<Goods> fetchGoodsAndUpdateQuantity(List<Long> goodsIds, List<Integer> quantities, Shop shop) {
        List<Goods> goodsList = new ArrayList<>();
        for (int i = 0; i < goodsIds.size(); i++) {
            final Long currentGoodsId = goodsIds.get(i);
            Goods goods = goodsRepository.findById(currentGoodsId).orElseThrow(() ->
                    new RuntimeException("Goods with ID " + currentGoodsId + " not found"));

            // Update the actual price of the goods based on its expiration date
            priceCalculationService.calculatePrice(shop, goods, LocalDate.now());

            updateGoodsQuantity(goods, quantities.get(i));
            goodsList.add(goods);
        }
        return goodsList;
    }

    private void updateGoodsQuantity(Goods goods, int quantity) {
        if (goods.getQuantity() < quantity) {
            throw new RuntimeException("Not enough stock for " + goods.getName());
        }
        goods.setQuantity(goods.getQuantity() - quantity);
    }

    private Receipt createAndSaveReceipt(Shop shop, CashierWorker cashierWorker, List<Goods> goodsList, List<Integer> quantities) {
        double totalPrice = calculateTotalPrice(goodsList, quantities);

        Receipt receipt = new Receipt();
        receipt.setGoods(goodsList);  // Assuming you've changed the setGoods to setGoodsList in Receipt
        receipt.setQuantity(quantities);
        receipt.setTotalPrice(totalPrice);
        receipt.setCashierWorker(cashierWorker);
        receipt.setShop(shop);
        receipt.setDate(LocalDateTime.now()); // Assuming you've added this field in Receipt

        return receiptRepository.save(receipt);
    }

    private double calculateTotalPrice(List<Goods> goodsList, List<Integer> quantities) {
        double totalPrice = 0.0;
        for (int i = 0; i < goodsList.size(); i++) {
            totalPrice += goodsList.get(i).getActualPrice() * quantities.get(i);
        }
        return totalPrice;
    }

    @Override
    public void saveReceiptToFile(Receipt receipt) {
        totalReceiptsIssued++;
        totalTurnover += receipt.getTotalPrice();

        String fileName = String.format("receipt_%d_%s.json", totalReceiptsIssued, LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss")));
        File file = new File(fileName);

        try {
            objectMapper.writeValue(file, receipt);
        } catch (IOException e) {
            throw new RuntimeException("Error saving receipt to file", e);
        }
    }

}
