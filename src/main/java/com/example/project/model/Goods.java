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
    @GeneratedValue
    @Column(columnDefinition = "uuid", updatable = false)
    private UUID goodsId;

    //@Column(nullable = false)
    private String name;

    //@Column(nullable = false)
    private double basePrice;

    //@Column(nullable = false)
    private double actualPrice;

    @Enumerated(EnumType.STRING)
    //@Column(nullable = false)
    private Category category;

    //@Column(nullable = false)
    private LocalDate expirationDate;

    //@Column(nullable = false)
    private double markupPercentage;

    //@Column(nullable = false)
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