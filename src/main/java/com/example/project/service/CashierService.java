package com.example.project.service;

import com.example.project.model.Cashier;
import com.example.project.model.CashierWorker;

import java.util.Optional;
import java.util.UUID;

public interface CashierService {
    Iterable<Cashier> getAllCashiers();
    Optional<Cashier> getCashierById(Long cashierId);
    Cashier saveCashier(Cashier cashierWorker);
    Iterable<Cashier> saveAllCashiers(Iterable<Cashier> cashiers);
    void deleteCashier(Cashier cashierWorker);
    void deleteCashierById(Long cashierId);
}
