package com.example.demo.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class OrderItemDTO {
    private Long id;
    private ProductDTO product;
    private Integer quantity;
    private BigDecimal unitPrice;
}