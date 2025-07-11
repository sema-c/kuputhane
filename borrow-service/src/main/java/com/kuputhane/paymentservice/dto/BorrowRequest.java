package com.kuputhane.paymentservice.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BorrowRequest {
    private Long userId;
    private Long bookId;
}
