package com.example.cheapesttransferroute;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.*;

@SpringBootTest
@AutoConfigureMockMvc
class CheapestTransferRouteApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testCalculateCheapestRoute() throws Exception {
        String requestJson = "{\"maxWeight\": 15, \"availableTransfers\": [{\"weight\": 5, \"cost\": 10}, {\"weight\": 10, \"cost\": 20}, {\"weight\": 3, \"cost\": 5}, {\"weight\": 8, \"cost\": 15}]}";

        mockMvc.perform(post("/api/transfers/calculate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalCost").value(30))
                .andExpect(jsonPath("$.totalWeight").value(15));
    }

    @Test
    void testNoTransfers() throws Exception {
        String requestJson = "{\"maxWeight\": 15, \"availableTransfers\": []}";

        mockMvc.perform(post("/api/transfers/calculate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalCost").value(0))
                .andExpect(jsonPath("$.totalWeight").value(0))
                .andExpect(jsonPath("$.selectedTransfers").isEmpty());
    }

    @Test
    void testWeightLimitExceeded() throws Exception {
        String requestJson = "{\"maxWeight\": 5, \"availableTransfers\": [{\"weight\": 10, \"cost\": 30}, {\"weight\": 20, \"cost\": 40}]}";

        mockMvc.perform(post("/api/transfers/calculate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalCost").value(0))
                .andExpect(jsonPath("$.totalWeight").value(0))
                .andExpect(jsonPath("$.selectedTransfers").isEmpty());
    }

    @Test
    void testSingleTransfer() throws Exception {
        String requestJson = "{\"maxWeight\": 10, \"availableTransfers\": [{\"weight\": 5, \"cost\": 10}]}";

        mockMvc.perform(post("/api/transfers/calculate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalCost").value(10))
                .andExpect(jsonPath("$.totalWeight").value(5))
                .andExpect(jsonPath("$.selectedTransfers[0].weight").value(5))
                .andExpect(jsonPath("$.selectedTransfers[0].cost").value(10));
    }

    @Test
    void testMaxWeightExactlyMet() throws Exception {
        String requestJson = "{\"maxWeight\": 15, \"availableTransfers\": [{\"weight\": 5, \"cost\": 10}, {\"weight\": 10, \"cost\": 20}]}";

        mockMvc.perform(post("/api/transfers/calculate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalCost").value(30))
                .andExpect(jsonPath("$.totalWeight").value(15))
                .andExpect(jsonPath("$.selectedTransfers[1].weight").value(5))
                .andExpect(jsonPath("$.selectedTransfers[1].cost").value(10))
                .andExpect(jsonPath("$.selectedTransfers[0].weight").value(10))
                .andExpect(jsonPath("$.selectedTransfers[0].cost").value(20));
    }

    @Test
    void testOptimalCombination() throws Exception {
        String requestJson = "{\"maxWeight\": 15, \"availableTransfers\": [{\"weight\": 5, \"cost\": 10}, {\"weight\": 10, \"cost\": 20}, {\"weight\": 3, \"cost\": 5}, {\"weight\": 8, \"cost\": 15}]}";

        mockMvc.perform(post("/api/transfers/calculate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalCost").value(30))
                .andExpect(jsonPath("$.totalWeight").value(15))
                .andExpect(jsonPath("$.selectedTransfers[1].weight").value(5))
                .andExpect(jsonPath("$.selectedTransfers[1].cost").value(10))
                .andExpect(jsonPath("$.selectedTransfers[0].weight").value(10))
                .andExpect(jsonPath("$.selectedTransfers[0].cost").value(20));
    }


    @Test
    void testExactWeightCombination() throws Exception {
        String requestJson = "{\"maxWeight\": 13, \"availableTransfers\": [{\"weight\": 5, \"cost\": 10}, {\"weight\": 8, \"cost\": 15}, {\"weight\": 3, \"cost\": 5}]}";

        mockMvc.perform(post("/api/transfers/calculate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalCost").value(25))
                .andExpect(jsonPath("$.totalWeight").value(13))
                .andExpect(jsonPath("$.selectedTransfers[1].weight").value(5))
                .andExpect(jsonPath("$.selectedTransfers[1].cost").value(10))
                .andExpect(jsonPath("$.selectedTransfers[0].weight").value(8))
                .andExpect(jsonPath("$.selectedTransfers[0].cost").value(15));
    }


}
