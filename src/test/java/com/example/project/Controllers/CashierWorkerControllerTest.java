package com.example.project.Controllers;

import com.example.project.controller.CashierWorkerController;
import com.example.project.model.CashierWorker;
import com.example.project.service.CashierWorkerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CashierWorkerControllerTest {

    @Mock
    private CashierWorkerService cashierWorkerService;

    @InjectMocks
    private CashierWorkerController cashierWorkerController;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private CashierWorker sampleCashierWorker;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(cashierWorkerController).build();

        sampleCashierWorker = new CashierWorker();
        // Assuming the CashierWorker model has these setters.
        sampleCashierWorker.setName("John Doe");
        sampleCashierWorker.setIdentificationNumber(12345678);
        sampleCashierWorker.setMonthlySalary(5000.00);
        // Add more initialization if needed.
    }

    @Test
    public void testGetCashiers() throws Exception {
        when(cashierWorkerService.getAllCashierWorkers()).thenReturn(Collections.singletonList(sampleCashierWorker));

        mockMvc.perform(get("/cashier_worker")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void testAddCashierWorker() throws Exception {
        when(cashierWorkerService.saveCashierWorker(any())).thenReturn(sampleCashierWorker);

        mockMvc.perform(post("/cashier_worker")
                        .content(objectMapper.writeValueAsString(sampleCashierWorker))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void testGetCashierWorkerById() throws Exception {
        when(cashierWorkerService.getCashierWorkerById(anyLong())).thenReturn(Optional.of(sampleCashierWorker));

        mockMvc.perform(get("/cashier_worker/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void testDeleteCashierWorkerById() throws Exception {
        doNothing().when(cashierWorkerService).deleteCashierWorkerById(anyLong());

        mockMvc.perform(delete("/cashier_worker/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
