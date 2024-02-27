package com.shopping.backend.discount;


import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;


@Component
@ConfigurationProperties(prefix = "discount.policies")
public class DiscountPolicyConfiguration {

    @Setter
    private List<DiscountThreshold> percentageThresholds;

    @Setter
    private List<DiscountThreshold> absoluteThresholds;

    public BigDecimal findPercentage(final Integer itemsCount) {
        return find(itemsCount, percentageThresholds);
    }

    public BigDecimal findAbsolute(final Integer itemsCount) {
        return find(itemsCount, absoluteThresholds);
    }

    private BigDecimal find(Integer itemsCount, List<DiscountThreshold> discountThresholds) {
        final BigDecimal[] discount = new BigDecimal[1];
        discountThresholds.stream().sorted(Comparator.comparing(DiscountThreshold::getItemsCount))
                .forEachOrdered( t -> discount[0] = itemsCount >= t.getItemsCount() ? t.getDiscount() : discount[0]);
        return discount[0];
    }

}
