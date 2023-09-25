package com.example.project.Controllers;

import com.example.project.controller.CashierController;
import com.example.project.model.Cashier;
import com.example.project.model.CashierWorker;
import com.example.project.model.Shop;
import com.example.project.service.CashierService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
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

@SpringBootTest
@AutoConfigureMockMvc
public class CashierControllerTest {

    @Mock
    private CashierService cashierService;

    @InjectMocks
    private CashierController cashierController;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private Cashier sampleCashier;
    private CashierWorker sampleCashierWorker;
    private Shop sampleShop;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(cashierController).build();

        sampleShop = new Shop();
        sampleShop.setName("Test Shop");

        sampleCashierWorker = new CashierWorker();
        sampleCashierWorker.setName("John Doe");
        sampleCashierWorker.setIdentificationNumber(12345678);
        sampleCashierWorker.setMonthlySalary(5000.00);
        sampleCashierWorker.setShop(sampleShop);

        sampleCashier = new Cashier();
        sampleCashier.setCashierWorker(sampleCashierWorker);
        sampleCashier.setShop(sampleShop);
    }

    @Test
    public void testGetCashiers() throws Exception {
        Mockito.when(cashierService.getAllCashiers()).thenReturn(Collections.singletonList(sampleCashier));

        mockMvc.perform(get("/cashier")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void testAddCashier() throws Exception {
        Mockito.when(cashierService.saveCashier(Mockito.any())).thenReturn(sampleCashier);

        mockMvc.perform(post("/cashier")
                        .content(objectMapper.writeValueAsString(sampleCashierWorker))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void testGetCashierById() throws Exception {
        Mockito.when(cashierService.getCashierById(Mockito.anyLong())).thenReturn(Optional.of(sampleCashier));

        mockMvc.perform(get("/cashier/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void testDeleteCashierById() throws Exception {
        Mockito.doNothing().when(cashierService).deleteCashierById(Mockito.any());
        mockMvc.perform(delete("/cashier/1")
                        .content(objectMapper.writeValueAsString(sampleCashier))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
