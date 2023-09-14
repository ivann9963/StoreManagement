package com.example.project.service;

import com.example.project.model.Receipt;
import com.example.project.repository.ReceiptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class ReceiptServiceImpl implements ReceiptService {

    private final ReceiptRepository receiptRepository;

    @Autowired
    public ReceiptServiceImpl(ReceiptRepository receiptRepository) {
        this.receiptRepository = receiptRepository;
    }

    @Override
    public Iterable<Receipt> getAllReceipts() {
        return receiptRepository.findAll();
    }

    @Override
    public Optional<Receipt> getReceiptById(Long receiptId) {
        return receiptRepository.findById(receiptId);
    }

    @Override
    public Receipt saveReceipt(Receipt receipt) {
        return receiptRepository.save(receipt);
    }

    @Override
    public Iterable<Receipt> saveAllReceipts(Iterable<Receipt> receipts) {
        return receiptRepository.saveAll(receipts);
    }

    @Override
    public void deleteReceipt(Receipt receipt) {
        receiptRepository.delete(receipt);
    }

    @Override
    public void deleteReceiptById(Long receiptId) {
        receiptRepository.deleteById(receiptId);
    }
}
