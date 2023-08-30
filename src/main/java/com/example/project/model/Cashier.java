package com.example.project.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "cashier")
@Data
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "cashierId")
@NoArgsConstructor
@AllArgsConstructor
public class Cashier {
    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid", updatable = false)
    private UUID cashierId;

    @OneToOne(fetch = FetchType.EAGER)
    private CashierWorker cashierWorker;


    /**
     * TO-DO: check if i can change this name to anything
     */
    @ManyToOne
    @JoinColumn(name = "shopId")
    private Shop shop;
}
