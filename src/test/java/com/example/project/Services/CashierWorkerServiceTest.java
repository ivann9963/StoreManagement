package com.example.project.Services;

import com.example.project.model.CashierWorker;
import com.example.project.repository.CashierWorkerRepository;
import com.example.project.service.CashierWorkerServiceImpl;
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
public class CashierWorkerServiceTest {

    @Mock
    private CashierWorkerRepository cashierWorkerRepository;

    @InjectMocks
    private CashierWorkerServiceImpl cashierWorkerService;

    private CashierWorker sampleCashierWorker;

    @BeforeEach
    public void setup() {
        sampleCashierWorker = new CashierWorker();
        // Set any sample attributes if necessary
        // sampleCashierWorker.setAttributeName(attributeValue);
    }

    @Test
    public void testGetAllCashierWorkers() {
        when(cashierWorkerRepository.findAll()).thenReturn(Arrays.asList(sampleCashierWorker));
        cashierWorkerService.getAllCashierWorkers();
        verify(cashierWorkerRepository).findAll();
    }

    @Test
    public void testGetCashierWorkerById() {
        Long id = 1L;
        when(cashierWorkerRepository.findById(id)).thenReturn(Optional.of(sampleCashierWorker));
        cashierWorkerService.getCashierWorkerById(id);
        verify(cashierWorkerRepository).findById(id);
    }

    @Test
    public void testSaveCashierWorker() {
        when(cashierWorkerRepository.save(sampleCashierWorker)).thenReturn(sampleCashierWorker);
        cashierWorkerService.saveCashierWorker(sampleCashierWorker);
        verify(cashierWorkerRepository).save(sampleCashierWorker);
    }

    @Test
    public void testSaveAllCashierWorkers() {
        when(cashierWorkerRepository.saveAll(Arrays.asList(sampleCashierWorker))).thenReturn(Arrays.asList(sampleCashierWorker));
        cashierWorkerService.saveAllCashierWorkers(Arrays.asList(sampleCashierWorker));
        verify(cashierWorkerRepository).saveAll(Arrays.asList(sampleCashierWorker));
    }

    @Test
    public void testDeleteCashierWorker() {
        Mockito.doNothing().when(cashierWorkerRepository).delete(sampleCashierWorker);
        cashierWorkerService.deleteCashierWorker(sampleCashierWorker);
        verify(cashierWorkerRepository).delete(sampleCashierWorker);
    }

    @Test
    public void testDeleteCashierWorkerById() {
        Long id = 1L;
        Mockito.doNothing().when(cashierWorkerRepository).deleteById(id);
        cashierWorkerService.deleteCashierWorkerById(id);
        verify(cashierWorkerRepository).deleteById(id);
    }
}
