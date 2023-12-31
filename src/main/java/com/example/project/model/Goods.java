package com.example.project.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Entity
@Table(name = "goods")
@Data
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "goodsId")
@NoArgsConstructor
@AllArgsConstructor
public class Goods {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long goodsId;

    private String name;

    private double basePrice;

    private double actualPrice;

    @Enumerated(EnumType.STRING)
    private Category category;

    private LocalDate expirationDate;

    private double markupPercentage;

    private int quantity;

    /**
     * TO-DO: check if i can change this name to anything
     */
    @ManyToOne
    @JoinColumn(name = "shopId")
    private Shop shop;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Receipt> receipts;

}