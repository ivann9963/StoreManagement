package com.example.project.Services;

import com.example.project.model.Receipt;
import com.example.project.repository.ReceiptRepository;
import com.example.project.service.ReceiptServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class ReceiptServiceTest {

    @InjectMocks
    private ReceiptServiceImpl receiptService;

    @Mock
    private ReceiptRepository receiptRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllReceipts() {
        Receipt receipt1 = new Receipt();
        Receipt receipt2 = new Receipt();
        when(receiptRepository.findAll()).thenReturn(Arrays.asList(receipt1, receipt2));

        Iterable<Receipt> receipts = receiptService.getAllReceipts();

        assertNotNull(receipts);
        assertEquals(2, ((List<Receipt>) receipts).size());
        verify(receiptRepository, times(1)).findAll();
    }

    @Test
    public void testGetReceiptById() {
        Receipt receipt = new Receipt();
        when(receiptRepository.findById(1L)).thenReturn(Optional.of(receipt));

        Optional<Receipt> result = receiptService.getReceiptById(1L);

        assertTrue(result.isPresent());
        assertEquals(receipt, result.get());
        verify(receiptRepository, times(1)).findById(1L);
    }

    @Test
    public void testSaveReceipt() {
        Receipt receipt = new Receipt();
        when(receiptRepository.save(receipt)).thenReturn(receipt);

        Receipt result = receiptService.saveReceipt(receipt);

        assertNotNull(result);
        assertEquals(receipt, result);
        verify(receiptRepository, times(1)).save(receipt);
    }

    @Test
    public void testSaveAllReceipts() {
        Receipt receipt1 = new Receipt();
        Receipt receipt2 = new Receipt();
        when(receiptRepository.saveAll(Arrays.asList(receipt1, receipt2))).thenReturn(Arrays.asList(receipt1, receipt2));

        Iterable<Receipt> receipts = receiptService.saveAllReceipts(Arrays.asList(receipt1, receipt2));

        assertNotNull(receipts);
        assertEquals(2, ((List<Receipt>) receipts).size());
        verify(receiptRepository, times(1)).saveAll(Arrays.asList(receipt1, receipt2));
    }

    @Test
    public void testDeleteReceipt() {
        Receipt receipt = new Receipt();

        doNothing().when(receiptRepository).delete(receipt);

        receiptService.deleteReceipt(receipt);

        verify(receiptRepository, times(1)).delete(receipt);
    }

    @Test
    public void testDeleteReceiptById() {
        doNothing().when(receiptRepository).deleteById(1L);

        receiptService.deleteReceiptById(1L);

        verify(receiptRepository, times(1)).deleteById(1L);
    }

    // Additional edge-case scenarios or specific requirements can be added as needed
}
