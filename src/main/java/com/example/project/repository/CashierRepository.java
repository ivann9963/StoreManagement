package com.example.project.repository;

import com.example.project.model.Cashier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CashierRepository extends JpaRepository<Cashier, Long> {
}