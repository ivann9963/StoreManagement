package com.example.project.repository;

import com.example.project.model.Cashier;
import com.example.project.model.CashierWorker;
import com.example.project.model.Goods;
import com.example.project.model.Receipt;
import com.example.project.model.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Long> {

    @Query("SELECT cw FROM CashierWorker cw WHERE cw.shop.id = :shopId")
    Iterable<CashierWorker> findCashierWorkersByShopId(@Param("shopId") Long shopId);

    @Query("SELECT SUM(cw.monthlySalary) FROM CashierWorker cw WHERE cw.shop.id = :shopId")
    Double sumCashierWorkersSalaries(@Param("shopId") Long shopId);

    @Modifying
    @Transactional
    @Query("UPDATE Cashier c SET c.shop.id = :shopId WHERE c.cashierId = :cashierId")
    void addCashier(@Param("shopId") Long shopId, @Param("cashierId") Long cashierId);

    @Modifying
    @Transactional
    @Query("UPDATE Goods g SET g.shop.id = :shopId WHERE g.goodsId = :goodsId")
    void addGoods(@Param("shopId") Long shopId, @Param("goodsId") Long goodsId);

    @Modifying
    @Transactional
    @Query("UPDATE Receipt r SET r.shop.id = :shopId WHERE r.receiptId = :receiptId")
    void addReceipt(@Param("shopId") Long shopId, @Param("receiptId") Long receiptId);

    @Modifying
    @Transactional
    @Query("UPDATE CashierWorker cw SET cw.shop.id = :shopId WHERE cw.cashierWorkerId = :cashierWorkerId")
    void addCashierWorker(@Param("shopId") Long shopId, @Param("cashierWorkerId") Long cashierWorkerId);

    @Modifying
    @Transactional
    @Query("SELECT r FROM Receipt r WHERE r.shop.id = :shopId")
    List<Receipt> getAllReceipts(Long shopId);
}
