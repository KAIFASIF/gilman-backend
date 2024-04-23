package com.kaif.gilmanbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {
    private Long amount;
    private Long amountPaid;
    private Long createdAt;
    private Long amountDue;
    private String currency;
    private String id;
    private String status;
}
