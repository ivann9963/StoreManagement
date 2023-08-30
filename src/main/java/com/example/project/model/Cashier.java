package com.example.project.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "cashier")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cashier {
    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid", updatable = false)
    private UUID cashierId;

    @OneToOne(fetch = FetchType.EAGER)
    private CashierWorker cashierWorker;

    @ManyToOne
    private Shop shop;
}
