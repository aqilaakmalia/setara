package org.synrgy.setara.transaction.service;

import org.synrgy.setara.transaction.dto.*;

public interface TransactionService {
    TransactionResponse topUp(TransactionRequest request, String token);

    TransferResponseDTO transferWithinBCA(TransferRequestDTO request, String authToken);

    GetMonthlyReportResponse getMonthlyReport(String token, int month, int year);
}
