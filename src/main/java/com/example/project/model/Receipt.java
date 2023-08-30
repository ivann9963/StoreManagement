package com.example.project.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Entity
@Table(name = "receipts")
@Data
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "receiptId")
@AllArgsConstructor
public class Receipt {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid", updatable = false)
    private UUID receiptId;

    @ManyToOne
    @JoinColumn(name = "goodsId")
    private Goods goods;

    //@Column(nullable = false)
    private int quantity;

    //@Column(nullable = false)
    private double totalPrice;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cashierId")
    private CashierWorker cashierWorker;

    /**
     * TO-DO: check if i can change this name to anything
     */
    @ManyToOne
    @JoinColumn(name = "shopId")
    private Shop shop;

}