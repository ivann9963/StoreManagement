package com.example.project.controller;

import com.example.project.model.CashierWorker;
import com.example.project.service.CashierWorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/cashier_worker")
public class CashierWorkerController {

    @Autowired
    private CashierWorkerService cashierWorkerService;

    @GetMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Iterable<CashierWorker> getCashiers() { return cashierWorkerService.getAllCashierWorkers(); }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addCashier(@RequestBody CashierWorker cashierWorker) { cashierWorkerService.saveCashierWorker(cashierWorker)  ; }

    @GetMapping("/{cashierId}")
    @ResponseStatus(HttpStatus.CREATED)
    public Optional<CashierWorker> getCashierById(@PathVariable UUID cashierWorkerId) { return cashierWorkerService.getCashierWorkerById(cashierWorkerId); }

    @DeleteMapping("/{cashierId}")
    public void deleteCashier(@PathVariable UUID cashierWorkerId) {
        cashierWorkerService.deleteCashierWorkerById(cashierWorkerId);
    }
}
