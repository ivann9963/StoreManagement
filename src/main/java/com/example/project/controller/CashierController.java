package com.example.project.controller;

import com.example.project.model.Cashier;
import com.example.project.model.Goods;
import com.example.project.service.CashierService;
import com.example.project.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/cashier")
public class CashierController {

    @Autowired
    private CashierService cashierService;

    @GetMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Iterable<Cashier> getCashiers() { return cashierService.getAllCashiers(); }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addCashier(@RequestBody Cashier goods) { cashierService.saveCashier(goods)  ; }

    @GetMapping("/{cashierId}")
    @ResponseStatus(HttpStatus.CREATED)
    public Optional<Cashier> getCashierById(@PathVariable Long cashierId) { return cashierService.getCashierById(cashierId); }

    @DeleteMapping("/{cashierId}")
    public void deleteCashierById(@PathVariable Long cashierId) {
        cashierService.deleteCashierById(cashierId);
    }
}

