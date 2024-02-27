package com.shopping.backend;

import com.shopping.backend.discount.DiscountPolicyConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({DiscountPolicyConfiguration.class})
public class ShoppingBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShoppingBackendApplication.class, args);
    }

}
