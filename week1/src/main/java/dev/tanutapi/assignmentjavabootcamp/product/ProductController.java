package dev.tanutapi.assignmentjavabootcamp.product;

import dev.tanutapi.assignmentjavabootcamp.productVariant.ProductVariantResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class ProductController {

    @Autowired ProductService productService;

    @GetMapping("/products")
    List<ProductShortResponse> findProduct(@RequestParam(required = false) String q) {
        try {
            return productService.getProductShortResponseList(q);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/products/{id}")
    ProductFullResponse getProduct(@PathVariable Integer id) {
        try {
            return productService.getProductFullResponse(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
