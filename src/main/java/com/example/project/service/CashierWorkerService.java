package com.example.project.service;

import com.example.project.model.CashierWorker;

import java.util.Optional;
import java.util.UUID;

public interface CashierWorkerService {
    Iterable<CashierWorker> getAllCashierWorkers();
    Optional<CashierWorker> getCashierWorkerById(Long cashierWorkerId);
    CashierWorker saveCashierWorker(CashierWorker cashierWorker);
    Iterable<CashierWorker> saveAllCashierWorkers(Iterable<CashierWorker> cashiersWorker);
    void deleteCashierWorker(CashierWorker cashierWorker);
    void deleteCashierWorkerById(Long cashierWorkerId);
}
