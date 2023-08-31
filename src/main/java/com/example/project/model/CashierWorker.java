package com.example.project.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;


@Entity
@Table(name = "cashier_worker")
@Getter
@Setter
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "cashierWorkerId")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CashierWorker {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid", updatable = false)
    private UUID cashierWorkerId;

    //@Column(nullable = false)
    private String name;

    //@Column(nullable = false)
    private int identificationNumber;

    //@Column(nullable = false)
    private double monthlySalary;

    //@Column(nullable = false)
    @OneToMany(cascade = CascadeType.ALL)
    private Set<Receipt> receipts;

    @OneToOne(fetch = FetchType.EAGER)
    private Cashier cashier;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="shopId")
    private Shop shop;

    // Other methods remain the same
}
