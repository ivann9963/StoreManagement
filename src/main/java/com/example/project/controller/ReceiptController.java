package com.example.project.controller;

import com.example.project.model.Receipt;
import com.example.project.service.ReceiptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/receipts")
public class ReceiptController {

    @Autowired
    private ReceiptService receiptService;

    @GetMapping
    public Iterable<Receipt> getReceipts() {
        return receiptService.getAllReceipts();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addReceipt(@RequestBody Receipt receipt) {
        receiptService.saveReceipt(receipt);
    }

    @GetMapping("/{receiptId}")
    public Optional<Receipt> getReceiptById(@PathVariable Long receiptId) {
        return receiptService.getReceiptById(receiptId);
    }

    @DeleteMapping("/{receiptId}")
    public void deleteReceiptById(@PathVariable Long receiptId) {
        receiptService.deleteReceiptById(receiptId);
    }
}
