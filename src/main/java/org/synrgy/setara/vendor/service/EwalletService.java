package org.synrgy.setara.vendor.service;

import org.synrgy.setara.vendor.dto.EwalletResponse;

import java.util.List;

public interface EwalletService {
    void seedEwallet();

    List<EwalletResponse> getAllEwallets();
}
