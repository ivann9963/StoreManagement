package com.example.project.service;

import com.example.project.model.Receipt;

import java.util.Optional;
import java.util.UUID;

public interface ReceiptService {
    Iterable<Receipt> getAllReceipts();
    Optional<Receipt> getReceiptById(UUID receiptId);
    Receipt saveReceipt(Receipt receipt);
    Iterable<Receipt> saveAllReceipts(Iterable<Receipt> receipts);
    void deleteReceipt(Receipt receipt);
    void deleteReceiptById(UUID receiptId);
}
