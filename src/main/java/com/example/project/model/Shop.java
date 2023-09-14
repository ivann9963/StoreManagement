package com.example.project.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "shop")
@Data
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "shopId")
@NoArgsConstructor
@AllArgsConstructor
public class Shop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long shopId;

    private String name;

    @OneToMany(/*mappedBy = "shop",*/ cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinTable(name = "shop_cashierWorkers",
            joinColumns = @JoinColumn(name = "shopId"),
            inverseJoinColumns = @JoinColumn(name = "cashierWorkerId"))
    private List<CashierWorker> cashierWorkers = new ArrayList<>();

    @OneToMany(/*mappedBy = "shop",*/ cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinTable(name = "shop_goods",
            joinColumns = @JoinColumn(name = "shopId"),
            inverseJoinColumns = @JoinColumn(name = "goodsId"))
    private List<Goods> goods = new ArrayList<>();

    @OneToMany(/*mappedBy = "shop",*/ cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinTable(name = "shop_receipts",
            joinColumns = @JoinColumn(name = "shopId"),
            inverseJoinColumns = @JoinColumn(name = "receiptId"))
    private List<Receipt> receipts = new ArrayList<>();

    @OneToMany(/*mappedBy = "shop",*/ cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinTable(name = "shop_soldGoods",
            joinColumns = @JoinColumn(name = "shopId"),
            inverseJoinColumns = @JoinColumn(name = "goodsId"))
    private List<Goods> soldGoods = new ArrayList<>();

    @OneToMany(/*mappedBy = "shop",*/ cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinTable(name = "shop_cashiers",
            joinColumns = @JoinColumn(name = "shopId"),
            inverseJoinColumns = @JoinColumn(name = "cashierId"))
    private List<Cashier> cashiers = new ArrayList<>();

    private Double startingFoodPercentage;

    private Double startingNonFoodPercentage;

    private Double percentageIncreaseBefore7Days;

}
