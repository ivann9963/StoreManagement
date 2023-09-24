package com.example.project.Controllers;

import com.example.project.controller.ShopController;
import com.example.project.model.Receipt;
import com.example.project.model.Shop;
import com.example.project.service.ShopService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class ShopControllerTest {

    @Mock
    private ShopService shopService;

    @InjectMocks
    private ShopController shopController;

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(shopController).build();
    }

    @Test
    public void testGetAllShops() throws Exception {
        mockMvc.perform(get("/shop")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testAddShop() throws Exception {
        mockMvc.perform(post("/shop")
                        .content(objectMapper.writeValueAsString(new Shop()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void testGetShopById() throws Exception {
        mockMvc.perform(get("/shop/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteShopByID() throws Exception {
        mockMvc.perform(delete("/shop/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @Test
    public void testSumCashierWorkersSalaries() throws Exception {
        // Given
        Long shopId = 1L;
        when(shopService.sumCashierWorkersSalaries(shopId)).thenReturn(1000D); // Mocking to return a sum of 1000

        // When & Then
        mockMvc.perform(get("/shop/" + shopId + "/totalSalaries")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(shopService, times(1)).sumCashierWorkersSalaries(shopId);
    }

    @Test
    public void testAddCashier() throws Exception {
        mockMvc.perform(post("/shop/1/cashier/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testAddGoods() throws Exception {
        mockMvc.perform(post("/shop/1/goods/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testAddReceipt() throws Exception {
        mockMvc.perform(post("/shop/1/receipt/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @Test
    public void testSaveAllShops() throws Exception {
        mockMvc.perform(post("/shop/batch")
                        .content(objectMapper.writeValueAsString(List.of(new Shop())))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void testDeleteShop() throws Exception {
        mockMvc.perform(delete("/shop")
                        .content(objectMapper.writeValueAsString(new Shop()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetAllReceipts() throws Exception {
        mockMvc.perform(get("/shop/1/receipts")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testMakeSale() throws Exception {
        mockMvc.perform(post("/shop/1/sale")
                        .param("cashierWorkerId", "1")
                        .param("goodsIds", "1,2,3")
                        .param("quantities", "5,3,1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testSaveReceiptToFile() throws Exception {
        mockMvc.perform(post("/shop/receipt")
                        .content(objectMapper.writeValueAsString(new Receipt()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testReadReceiptFromFile() throws Exception {
        mockMvc.perform(get("/shop/receipt/filename.json")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
