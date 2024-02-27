package com.shopping.backend.pricing;


import com.shopping.backend.discount.DiscountType;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class FinalPrice {
    BigDecimal totalPrice;
    BigDecimal totalPriceAfterDiscount;
    BigDecimal discountApplied;
    DiscountType discountType;
}
