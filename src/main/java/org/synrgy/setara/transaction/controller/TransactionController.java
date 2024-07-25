package org.synrgy.setara.transaction.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.synrgy.setara.common.dto.BaseResponse;
import org.synrgy.setara.transaction.dto.*;
import org.synrgy.setara.transaction.service.TransactionService;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
@Slf4j
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping("/topup")
    public ResponseEntity<BaseResponse<TransactionResponse>> topUp(@RequestBody TransactionRequest request, @RequestHeader("Authorization") String token) {
        String authToken = token.substring(7);
        TransactionResponse transactionResponse = transactionService.topUp(request, authToken);
        BaseResponse<TransactionResponse> response = BaseResponse.success(HttpStatus.OK, transactionResponse, "Top-up successful");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/bca-transfer")
    public ResponseEntity<BaseResponse<TransferResponseDTO>> bcaTransfer(@RequestBody TransferRequestDTO request, @RequestHeader("Authorization") String token) {
        String authToken = token.substring(7);
        TransferResponseDTO response = transactionService.transferWithinBCA(request, authToken);
        return ResponseEntity.ok(BaseResponse.success(HttpStatus.OK, response,"Transfer successful"));
    }

    @GetMapping("/getMonthlyReport")
    public ResponseEntity<BaseResponse<GetMonthlyReportResponse>> getMonthlyReport(@RequestHeader("Authorization") String token, @RequestParam(name = "month") int month, @RequestParam(name = "year") int year) {
        String authToken = token.substring(7);
        GetMonthlyReportResponse getMonthlyReportResponse = transactionService.getMonthlyReport(authToken, month, year);
        BaseResponse<GetMonthlyReportResponse> response = BaseResponse.success(HttpStatus.OK, getMonthlyReportResponse, "Success Get Monthly Report");
        return ResponseEntity.ok(response);
    }
}
