package com.example.project.service;

import com.example.project.model.CashierWorker;
import com.example.project.repository.CashierWorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class CashierWorkerServiceImpl implements CashierWorkerService {

    private final CashierWorkerRepository cashierWorkerRepository;

    @Autowired
    public CashierWorkerServiceImpl(CashierWorkerRepository cashierWorkerRepository) {
        this.cashierWorkerRepository = cashierWorkerRepository;
    }

    @Override
    public Iterable<CashierWorker> getAllCashierWorkers() {
        return cashierWorkerRepository.findAll();
    }

    @Override
    public Optional<CashierWorker> getCashierWorkerById(UUID cashierWorkerId) {
        return cashierWorkerRepository.findById(cashierWorkerId);
    }

    @Override
    public CashierWorker saveCashierWorker(CashierWorker cashierWorker) {
        return cashierWorkerRepository.save(cashierWorker);
    }

    @Override
    public Iterable<CashierWorker> saveAllCashierWorkers(Iterable<CashierWorker> cashiers) {
        return cashierWorkerRepository.saveAll(cashiers);
    }

    @Override
    public void deleteCashierWorker(CashierWorker cashierWorker) {
        cashierWorkerRepository.delete(cashierWorker);
    }

    @Override
    public void deleteCashierWorkerById(UUID cashierId) {
        cashierWorkerRepository.deleteById(cashierId);
    }
}
