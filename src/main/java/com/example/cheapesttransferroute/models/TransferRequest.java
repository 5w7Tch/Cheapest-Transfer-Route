package com.example.cheapesttransferroute.models;

import java.util.List;

public class TransferRequest {
    private int maxWeight;
    private List<Transfer> availableTransfers;

    public int getMaxWeight() {
        return maxWeight;
    }

    public void setMaxWeight(int maxWeight) {
        this.maxWeight = maxWeight;
    }

    public List<Transfer> getAvailableTransfers() {
        return availableTransfers;
    }

    public void setAvailableTransfers(List<Transfer> availableTransfers) {
        this.availableTransfers = availableTransfers;
    }
}
