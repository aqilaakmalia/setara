package org.synrgy.setara.transaction.service;

import org.synrgy.setara.transaction.dto.TransferRequestDTO;
import org.synrgy.setara.transaction.dto.TransferResponseDTO;

public interface BankTransferService {
    TransferResponseDTO transferWithinBCA(TransferRequestDTO request, String authToken);
}
