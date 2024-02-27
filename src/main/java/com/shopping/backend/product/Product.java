package com.shopping.backend.product;


import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Builder
public class Product {
    UUID id;
    String name;
    BigDecimal price;
}
