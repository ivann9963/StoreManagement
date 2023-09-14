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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Optional;

@Service
public class ShopServiceImpl implements ShopService {

    private final ShopRepository shopRepository;

    private final CashierRepository cashierRepository;

    private final GoodsRepository goodsRepository;

    private final ReceiptRepository receiptRepository;
    private final CashierWorkerRepository cashierWorkerRepository;

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
        Optional<Shop> existingShopOpt = shopRepository.findById(shopId);
        Optional<Cashier> cashierOpt = cashierRepository.findById(cashierId);

        if(!existingShopOpt.isPresent()) {
            throw new Exception("Shop with the provided id does not exist!");
        }

        if(!cashierOpt.isPresent()) {
            throw new Exception("Cashier with the provided id does not exist!");
        }

        Cashier cashier = cashierOpt.get();
        Shop existingShop = existingShopOpt.get();

        cashier.setShop(existingShop);
        cashierRepository.save(cashier);
        existingShop.getCashiers().add(cashier);
        shopRepository.save(existingShop);
    }

    @Override
    public void addGoods(Long shopId, Long goodsId) throws Exception {
        Optional<Shop> existingShopOpt = shopRepository.findById(shopId);
        Optional<Goods> goodsOpt = goodsRepository.findById(goodsId);

        if(!existingShopOpt.isPresent()) {
            throw new Exception("Shop with the provided id does not exist!");
        }

        if(!goodsOpt.isPresent()) {
            throw new Exception("Goods with the provided id does not exist!");
        }

        Goods goods = goodsOpt.get();
        Shop existingShop = existingShopOpt.get();

        goods.setShop(existingShop);
        goodsRepository.save(goods);
        existingShop.getGoods().add(goods);
        shopRepository.save(existingShop);
    }

    @Override
    public void addReceipt(Long shopId, Long receiptId) throws Exception {
        Optional<Shop> existingShopOpt = shopRepository.findById(shopId);
        Optional<Receipt> receiptOpt = receiptRepository.findById(receiptId);

        if(!existingShopOpt.isPresent()) {
            throw new Exception("Shop with the provided id does not exist!");
        }

        if(!receiptOpt.isPresent()) {
            throw new Exception("Receipt with the provided id does not exist!");
        }

        Receipt receipt = receiptOpt.get();
        Shop existingShop = existingShopOpt.get();

        receipt.setShop(existingShop);
        receiptRepository.save(receipt);
        existingShop.getReceipts().add(receipt);
        shopRepository.save(existingShop);
    }

    @Override
    public void addCashierWorker(Long shopId, Long cashierWorkerId) throws Exception {
        Optional<Shop> existingShopOpt = shopRepository.findById(shopId);
        Optional<CashierWorker> cashierWorkerOpt = cashierWorkerRepository.findById(cashierWorkerId);

        if(!existingShopOpt.isPresent()) {
            throw new Exception("Shop with the provided id does not exist!");
        }

        if(!cashierWorkerOpt.isPresent()) {
            throw new Exception("CashierWorker with the provided id does not exist!");
        }

        CashierWorker cashierWorker = cashierWorkerOpt.get();
        Shop existingShop = existingShopOpt.get();

        cashierWorker.setShop(existingShop);
        cashierWorkerRepository.save(cashierWorker);
        existingShop.getCashierWorkers().add(cashierWorker);
        shopRepository.save(existingShop);
    }

    @Override
    public double sumCashierWorkersSalaries(Long shopId) throws Exception {
        Optional<Shop> optionalShop = shopRepository.findById(shopId);

        if (!optionalShop.isPresent()) {
            throw new Exception("Shop with the provided id does not exist!");
        }

        Shop existingShop = optionalShop.get();
        return existingShop.getCashierWorkers()
                .stream()
                .mapToDouble(cashierWorker -> cashierWorker.getMonthlySalary())
                .sum();
    }

    @Transactional
    @Override
    public Receipt makeSale(Long shopId, Long cashierWorkerId, Long goodsId, int quantity) {
        // Fetch the entities
        Shop shop = shopRepository.findById(shopId).orElseThrow(() -> new RuntimeException("Shop not found"));
        CashierWorker cashierWorker = shop.getCashierWorkers().stream()
                .filter(cw -> cw.getCashierWorkerId().equals(cashierWorkerId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("CashierWorker not found in the shop"));
        Goods goods = goodsRepository.findById(goodsId).orElseThrow(() -> new RuntimeException("Goods not found"));

        // Check if there's enough stock
        if (goods.getQuantity() < quantity) {
            throw new RuntimeException("Not enough stock for " + goods.getName());
        }

        // Calculate total price
        double totalPrice = goods.getActualPrice() * quantity;

        // Update the quantity of the goods in the inventory
        goods.setQuantity(goods.getQuantity() - quantity);

        // Generate a new receipt
        Receipt receipt = new Receipt();
        receipt.setGoods(goods);
        receipt.setQuantity(quantity);
        receipt.setTotalPrice(totalPrice);
        receipt.setCashierWorker(cashierWorker);
        receipt.setShop(shop);

        return receiptRepository.save(receipt);
    }

}
