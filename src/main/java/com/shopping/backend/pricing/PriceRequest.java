package com.shopping.backend.pricing;

import com.shopping.backend.discount.DiscountType;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class PriceRequest {
    private UUID productId;
    private Integer itemsCount;
    private DiscountType discountType;
}
