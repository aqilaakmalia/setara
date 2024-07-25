package org.synrgy.setara.transaction.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.synrgy.setara.common.dto.BaseResponse;
import org.synrgy.setara.transaction.dto.TransferRequestDTO;
import org.synrgy.setara.transaction.dto.TransferResponseDTO;
import org.synrgy.setara.transaction.service.BankTransferService;

@RestController
@RequestMapping("/api/bank")
@RequiredArgsConstructor
@Slf4j
public class BankTransferController {
    private final BankTransferService bankService;

    @PostMapping("/bca-transfer")
    public ResponseEntity<BaseResponse<TransferResponseDTO>> bcaTransfer(@RequestBody TransferRequestDTO request, @RequestHeader("Authorization") String token) {
        String authToken = token.substring(7);
        TransferResponseDTO response = bankService.transferWithinBCA(request, authToken);
        return ResponseEntity.ok(BaseResponse.success(HttpStatus.OK, response,"Transfer successful"));
    }
}
