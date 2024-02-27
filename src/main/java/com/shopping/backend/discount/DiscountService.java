package com.shopping.backend.discount;

import com.shopping.backend.pricing.FinalPrice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


@Service
public class DiscountService {

    @Autowired
    private DiscountPolicyConfiguration discountPolicyConfiguration;


    public FinalPrice getFinalPrice(BigDecimal basePrice, Integer amount, DiscountType discountType) {
        BigDecimal discount;
        BigDecimal totalPriceAfterDiscount;
        BigDecimal totalPrice = basePrice.multiply(BigDecimal.valueOf(amount));

        switch (discountType) {
            case PERCENTAGE:
                discount = discountPolicyConfiguration.findPercentage(amount);
                totalPriceAfterDiscount = totalPrice.subtract(discount.multiply(totalPrice.divide(BigDecimal.valueOf(100))));
                break;
            case ABSOLUTE:
                discount = discountPolicyConfiguration.findAbsolute(amount);
                totalPriceAfterDiscount = totalPrice.subtract(discount);
                break;
            default:
                discount = BigDecimal.ZERO;
                totalPriceAfterDiscount = basePrice.multiply(BigDecimal.valueOf(amount));
        }

        return FinalPrice.builder()
                .totalPrice(totalPrice)
                .discountApplied(discount)
                .totalPriceAfterDiscount(totalPriceAfterDiscount)
                .discountType(discountType)
                .build();
    }
}
