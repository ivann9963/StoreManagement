package com.example.project.service;

import com.example.project.model.Cashier;
import com.example.project.repository.CashierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;


@Service
public class CashierServiceImpl implements CashierService {

    private final CashierRepository cashierRepository;

    @Autowired
    public CashierServiceImpl(CashierRepository cashierRepository) {
        this.cashierRepository = cashierRepository;
    }

    @Override
    public Iterable<Cashier> getAllCashiers() {
        return cashierRepository.findAll();
    }

    @Override
    public Optional<Cashier> getCashierById(UUID cashierId) {
        return cashierRepository.findById(cashierId);
    }

    @Override
    public Cashier saveCashier(Cashier cashier) {
        return cashierRepository.save(cashier);
    }

    @Override
    public Iterable<Cashier> saveAllCashiers(Iterable<Cashier> cashiers) {
        return cashierRepository.saveAll(cashiers);
    }

    @Override
    public void deleteCashier(Cashier cashier) {
        // Perform any necessary cleanup or associated entity handling before deleting
        cashierRepository.delete(cashier);
    }

    @Override
    public void deleteCashierById(UUID cashierId) {
        cashierRepository.deleteById(cashierId);
    }
}