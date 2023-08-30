package com.example.project.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Entity
@Table(name = "receipts")
@Data
@AllArgsConstructor
public class Receipt {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid", updatable = false)
    private UUID receiptId;

    @ManyToOne
    @JoinColumn(name = "goodsId")
    private Goods goods;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private double totalPrice;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cashierId")
    private CashierWorker cashierWorker;

}
