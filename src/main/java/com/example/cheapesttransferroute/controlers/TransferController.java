package com.example.cheapesttransferroute.controlers;


import com.example.cheapesttransferroute.models.TransferRequest;
import com.example.cheapesttransferroute.services.TransferService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/transfers")
class TransferController {
    private final TransferService transferService;

    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @PostMapping("/calculate")
    public Map<String, Object> calculateCheapestRoute(@RequestBody TransferRequest request) {
        return transferService.findCheapestRoute(request);
    }
}
