package com.example.demo.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderDTO {
    private Long id;
    private CustomerDTO customer;
    private LocalDateTime orderDate;
    private BigDecimal totalAmount;
    private List<OrderItemDTO> orderItems;
}