package com.example.project.controller;

import com.example.project.model.CashierWorker;
import com.example.project.service.CashierWorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:3000")
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

    @GetMapping("/{cashierWorkerId}")
    @ResponseStatus(HttpStatus.CREATED)
    public Optional<CashierWorker> getCashierById(@PathVariable Long cashierWorkerId) { return cashierWorkerService.getCashierWorkerById(cashierWorkerId); }

    @DeleteMapping("/{cashierWorkerId}")
    public void deleteCashier(@PathVariable Long cashierWorkerId) {
        cashierWorkerService.deleteCashierWorkerById(cashierWorkerId);
    }
}
