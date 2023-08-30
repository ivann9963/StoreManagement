package com.example.project.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;


@Entity
@Table(name = "cashier_worker")
@Getter
@Setter
//@JsonIdentityInfo(
//        generator = ObjectIdGenerators.PropertyGenerator.class,
//        property = "cashierId")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CashierWorker {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid", updatable = false)
    private UUID cashierWorkerId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int identificationNumber;

    @Column(nullable = false)
    private double monthlySalary;

    @Column(nullable = false)
    @OneToMany(cascade = CascadeType.ALL)
    private Set<Receipt> receipts = new HashSet<>();

    @OneToOne(fetch = FetchType.EAGER)
    private Cashier cashier;

    // Other methods remain the same
}
