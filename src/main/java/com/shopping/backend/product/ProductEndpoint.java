package com.shopping.backend.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ProductEndpoint {

  @Autowired
  ProductRepository productRepository;

  @PostMapping(value = "/v1/product/add")
  public ResponseEntity<String> addProduct(@RequestBody ProductDto productDto) {
      return ResponseEntity.ok().body(productRepository.add(productDto).getId().toString());
  }

}
