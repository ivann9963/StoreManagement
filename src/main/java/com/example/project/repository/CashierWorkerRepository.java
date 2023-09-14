package com.example.project.repository;

import com.example.project.model.CashierWorker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CashierWorkerRepository extends JpaRepository<CashierWorker, Long> {
}