package com.shopping.backend.discount;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
class DiscountThreshold {
    private Integer itemsCount;
    private BigDecimal discount;
}