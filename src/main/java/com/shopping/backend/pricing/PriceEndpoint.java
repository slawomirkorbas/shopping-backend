package com.shopping.backend.pricing;

import com.shopping.backend.discount.DiscountService;
import com.shopping.backend.product.Product;
import com.shopping.backend.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.annotation.*;


@RestController
public class PriceEndpoint {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    DiscountService discountService;

    @PostMapping(value = "/v1/price/calculate")
    public ResponseEntity<FinalPrice> getPrice(@RequestBody PriceRequest priceRequest) {

        Product product = productRepository.findById(priceRequest.getProductId());
        if (product == null) {
            throw new ResourceNotFoundException("Product not found with id: " + priceRequest.getProductId());
        }

    return ResponseEntity.ok().body(
            discountService.getFinalPrice(
                    product.getPrice(), priceRequest.getItemsCount(), priceRequest.getDiscountType()));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleResourceNotFoundException(ResourceNotFoundException ex) {
        return new ErrorResponseException(HttpStatus.NOT_FOUND, ex.getCause());
    }

}
