package org.synrgy.setara.transaction.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.synrgy.setara.common.dto.BaseResponse;
import org.synrgy.setara.transaction.dto.TransferRequestDTO;
import org.synrgy.setara.transaction.dto.TransactionRequest;
import org.synrgy.setara.transaction.dto.TransactionResponse;
import org.synrgy.setara.transaction.dto.TransferResponseDTO;
import org.synrgy.setara.transaction.service.TransactionService;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
@Slf4j
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping("/topup")
    public ResponseEntity<BaseResponse<TransactionResponse>> topUp(@RequestBody TransactionRequest request, @RequestHeader("Authorization") String token) {
        try {
            String authToken = token.substring(7);
            TransactionResponse transactionResponse = transactionService.topUp(request, authToken);
            return ResponseEntity.ok(BaseResponse.success(HttpStatus.OK, transactionResponse, "Top-up successful"));
        } catch (Exception e) {
            log.error("Error in topUp: {}", e.getMessage());
            throw e;
        }
    }

    @PostMapping("/bca-transfer")
    public ResponseEntity<BaseResponse<TransferResponseDTO>> bcaTransfer(@RequestBody TransferRequestDTO request, @RequestHeader("Authorization") String token) {
        try {
            String authToken = token.substring(7);
            TransferResponseDTO response = transactionService.transferWithinBCA(request, authToken);
            return ResponseEntity.ok(BaseResponse.success(HttpStatus.OK, response, "Transfer successful"));
        } catch (Exception e) {
            log.error("Error in bcaTransfer: {}", e.getMessage());
            throw e;
        }
    }
}
