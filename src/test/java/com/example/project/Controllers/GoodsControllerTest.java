package com.example.project.Controllers;

import com.example.project.controller.GoodsController;
import com.example.project.model.Goods;
import com.example.project.service.GoodsService;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
public class GoodsControllerTest {

    @Mock
    private GoodsService goodsService;

    @InjectMocks
    private GoodsController goodsController;

    private MockMvc mockMvc;
    private Goods sampleGoods;
    private final ObjectMapper objectMapper = new ObjectMapper();


    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(goodsController).build();
        sampleGoods = new Goods();
    }

    @Test
    public void testGetGoods() throws Exception {
        when(goodsService.getAllGoods()).thenReturn(Collections.singletonList(new Goods()));
        mockMvc.perform(get("/goods"))
                .andExpect(status().isCreated());
    }

    @Test
    public void testAddGoods() throws Exception {
        when(goodsService.saveGoods(any())).thenReturn(sampleGoods);
        mockMvc.perform(post("/goods")
                        .content(objectMapper.writeValueAsString(sampleGoods))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }


}
