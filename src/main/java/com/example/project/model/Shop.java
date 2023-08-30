package com.example.project.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "shop")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Shop {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid", updatable = false)
    private UUID shopId;

    private String name;

    @OneToMany(fetch = FetchType.EAGER)
    private List<CashierWorker> cashierWorkers;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Goods> goods;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Receipt> receipts;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Goods> soldGoods;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Cashier> cashiers;

    private Double startingFoodPercentage;

    private Double startingNonFoodPercentage;

    private Double percentageIncreaseBefore7Days;

}
