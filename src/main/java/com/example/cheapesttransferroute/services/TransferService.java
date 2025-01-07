package com.example.cheapesttransferroute.services;

import com.example.cheapesttransferroute.models.Transfer;
import com.example.cheapesttransferroute.models.TransferRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TransferService {

    private List<Transfer> getSelectedTransfers(int[][] dp, int maxWeight, List<Transfer> availableTransfers){
        List<Transfer> res = new ArrayList<>();
        int w = maxWeight;
        for (int i = availableTransfers.size(); i > 0 && w > 0; i--) {
            if (dp[i][w] != dp[i - 1][w]) {
                Transfer transfer = availableTransfers.get(i - 1);
                res.add(transfer);
                w -= transfer.getWeight();
            }
        }
        return res;
    }

    public Map<String, Object> findCheapestRoute(TransferRequest request) {
        List<Transfer> availableTransfers = request.getAvailableTransfers();
        int maxWeight = request.getMaxWeight();

        int[][] dp = new int[availableTransfers.size() + 1][maxWeight + 1];

        for (int i = 1; i <= availableTransfers.size(); i++) {
            Transfer transfer = availableTransfers.get(i - 1);
            for (int w = 0; w <= maxWeight; w++) {
                if (transfer.getWeight() <= w) {
                    dp[i][w] = Math.max(dp[i - 1][w], dp[i - 1][w - transfer.getWeight()] + transfer.getCost());
                } else {
                    dp[i][w] = dp[i - 1][w];
                }
            }
        }

        List<Transfer> selectedTransfers = getSelectedTransfers(dp, maxWeight, availableTransfers);

        int totalCost = dp[availableTransfers.size()][maxWeight];
        int totalWeight = selectedTransfers.stream().mapToInt(Transfer::getWeight).sum();
        Map<String, Object> response = new HashMap<>();
        response.put("selectedTransfers", selectedTransfers);
        response.put("totalCost", totalCost);
        response.put("totalWeight", totalWeight);

        return response;
    }
}

