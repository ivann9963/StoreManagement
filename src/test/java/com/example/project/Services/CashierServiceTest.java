package com.example.project.Services;

import com.example.project.model.Cashier;
import com.example.project.repository.CashierRepository;
import com.example.project.service.CashierServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CashierServiceTest {

    @Mock
    private CashierRepository cashierRepository;

    @InjectMocks
    private CashierServiceImpl cashierService;

    private Cashier sampleCashier;

    @BeforeEach
    public void setup() {
        sampleCashier = new Cashier();
        // Set any sample attributes if necessary
        // sampleCashier.setAttributeName(attributeValue);
    }

    @Test
    public void testGetAllCashiers() {
        when(cashierRepository.findAll()).thenReturn(Arrays.asList(sampleCashier));
        cashierService.getAllCashiers();
        verify(cashierRepository).findAll();
    }

    @Test
    public void testGetCashierById() {
        Long id = 1L;
        when(cashierRepository.findById(id)).thenReturn(Optional.of(sampleCashier));
        cashierService.getCashierById(id);
        verify(cashierRepository).findById(id);
    }

    @Test
    public void testSaveCashier() {
        when(cashierRepository.save(sampleCashier)).thenReturn(sampleCashier);
        cashierService.saveCashier(sampleCashier);
        verify(cashierRepository).save(sampleCashier);
    }

    @Test
    public void testSaveAllCashiers() {
        when(cashierRepository.saveAll(Arrays.asList(sampleCashier))).thenReturn(Arrays.asList(sampleCashier));
        cashierService.saveAllCashiers(Arrays.asList(sampleCashier));
        verify(cashierRepository).saveAll(Arrays.asList(sampleCashier));
    }

    @Test
    public void testDeleteCashier() {
        Mockito.doNothing().when(cashierRepository).delete(sampleCashier);
        cashierService.deleteCashier(sampleCashier);
        verify(cashierRepository).delete(sampleCashier);
    }

    @Test
    public void testDeleteCashierById() {
        Long id = 1L;
        Mockito.doNothing().when(cashierRepository).deleteById(id);
        cashierService.deleteCashierById(id);
        verify(cashierRepository).deleteById(id);
    }
}
