package org.synrgy.setara.vendor.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EwalletResponse {
    private String id;
    private String name;
    private String imagePath;
}