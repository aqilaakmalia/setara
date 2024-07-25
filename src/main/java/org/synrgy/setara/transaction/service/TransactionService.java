package org.synrgy.setara.transaction.service;

import org.synrgy.setara.transaction.dto.TopUpRequest;
import org.synrgy.setara.transaction.dto.TopUpResponse;

public interface TransactionService {
    TopUpResponse topUp(TopUpRequest request, String token);
}
