package com.shopping.backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopping.backend.discount.DiscountType;
import com.shopping.backend.pricing.FinalPrice;
import com.shopping.backend.pricing.PriceRequest;
import com.shopping.backend.product.ProductDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:test-application.yml")
public class PriceCalculationIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCalculatePriceUsingPercentageDiscount() throws Exception {

        //given
        var itemPrice = BigDecimal.valueOf(10);
        UUID productId = addProduct(itemPrice);

        //and
        var priceRequest = PriceRequest.builder()
                .productId(productId)
                .itemsCount(100)
                .discountType(DiscountType.PERCENTAGE).build();
        //and
        BigDecimal expectedTotalPrice = itemPrice.multiply(BigDecimal.valueOf(priceRequest.getItemsCount()));
        BigDecimal expectedDiscount = BigDecimal.valueOf(10);
        BigDecimal expectedPriceAfterDiscount = expectedTotalPrice.subtract(expectedDiscount.multiply(expectedTotalPrice.divide(BigDecimal.valueOf(100))));

        //when
        FinalPrice finalPrice = calculatePrice(priceRequest);

        //then
        assertThat(finalPrice.getTotalPrice()).isEqualTo(expectedTotalPrice);
        assertThat(finalPrice.getDiscountType()).isEqualTo(DiscountType.PERCENTAGE);
        assertThat(finalPrice.getDiscountApplied()).isEqualTo(expectedDiscount); //value from application-test.yml file
        assertThat(finalPrice.getTotalPriceAfterDiscount()).isEqualTo(expectedPriceAfterDiscount);
    }

    @Test
    void shouldCalculatePriceUsingAbsoluteDiscount() throws Exception {

        //given
        var itemPrice = BigDecimal.valueOf(10);
        UUID productId = addProduct(itemPrice);

        //and
        var priceRequest = PriceRequest.builder()
                .productId(productId)
                .itemsCount(100)
                .discountType(DiscountType.ABSOLUTE).build();
        //and
        BigDecimal expectedTotalPrice = itemPrice.multiply(BigDecimal.valueOf(priceRequest.getItemsCount()));
        BigDecimal expectedDiscount = BigDecimal.valueOf(5.00); //value from application-test.yml file
        BigDecimal expectedPriceAfterDiscount = expectedTotalPrice.subtract(expectedDiscount);

        //when
        FinalPrice finalPrice = calculatePrice(priceRequest);

        //then
        assertThat(finalPrice.getTotalPrice()).isEqualTo(expectedTotalPrice);
        assertThat(finalPrice.getDiscountType()).isEqualTo(DiscountType.ABSOLUTE);
        assertThat(finalPrice.getDiscountApplied()).isEqualTo(expectedDiscount);
        assertThat(finalPrice.getTotalPriceAfterDiscount()).isEqualTo(expectedPriceAfterDiscount);
    }

    private UUID addProduct(BigDecimal price) throws Exception {
        MvcResult result = mockMvc.perform(post("/v1/product/add")
                        .content(asJsonString(new ProductDto("prod", price)))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        UUID productId = UUID.fromString(result.getResponse().getContentAsString());
        return productId;
    }


    private FinalPrice calculatePrice(PriceRequest request) throws Exception {
        MvcResult result = mockMvc.perform(post("/v1/price/calculate")
                        .content(asJsonString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        return  objectMapper.readValue(result.getResponse().getContentAsString(), FinalPrice.class);
    }

    private String asJsonString(final Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
