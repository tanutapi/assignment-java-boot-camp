package dev.tanutapi.assignmentjavabootcamp.product;

import dev.tanutapi.assignmentjavabootcamp.user.LoginRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductControllerTest {

    @Autowired
    TestRestTemplate testRestTemplate;

    @Test
    @DisplayName("It should return all product in the database if no keyword set")
    void findAllProducts() {
        ResponseEntity<ProductShortResponse[]> response = testRestTemplate.getForEntity("/products", ProductShortResponse[].class);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assert(Objects.requireNonNull(response.getBody()).length > 0);
    }

    @Test
    @DisplayName("It should return match product in the database if keyword set")
    void searchProducts() {
        Map<String, String> params = new HashMap<>();
        params.put("q", "PK");
        ResponseEntity<ProductShortResponse[]> response = testRestTemplate.getForEntity("/products?q={q}", ProductShortResponse[].class, params);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assert(Objects.requireNonNull(response.getBody()).length > 0);
        List<ProductShortResponse> productShortResponses = List.of(Objects.requireNonNull(response.getBody()));
        productShortResponses.forEach(productShortResponse -> {
            System.out.println(productShortResponse.getTitle());
            assert(productShortResponse.getTitle().contains("PK"));
        });
    }

    @Test
    @DisplayName("It should return empty list if keyword set and not match")
    void searchProductsNoResult() {
        Map<String, String> params = new HashMap<>();
        params.put("q", "PKXXXXXXXX");
        ResponseEntity<ProductShortResponse[]> response = testRestTemplate.getForEntity("/products?q={q}", ProductShortResponse[].class, params);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assert(Objects.requireNonNull(response.getBody()).length == 0);
    }

    @Test
    @DisplayName("It should return product specific detail of given id")
    void getProduct() {
        ResponseEntity<ProductShortResponse[]> response = testRestTemplate.getForEntity("/products", ProductShortResponse[].class);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assert(Objects.requireNonNull(response.getBody()).length > 0);
        List<ProductShortResponse> productShortResponses = List.of(Objects.requireNonNull(response.getBody()));
        ProductShortResponse productShortResponse = productShortResponses.get(0);

        Map<String, String> params = new HashMap<>();
        params.put("id", "" + productShortResponse.getProductId());
        ResponseEntity<ProductFullResponse> fullResponse = testRestTemplate.getForEntity("/products/{id}", ProductFullResponse.class, params);
        assertEquals(fullResponse.getStatusCode(), HttpStatus.OK);
        assertNotNull(fullResponse.getBody());
        if (fullResponse.getBody() != null) {
            ProductFullResponse productFullResponse = fullResponse.getBody();
            assertEquals(productFullResponse.getTitle(), productShortResponse.getTitle());
            assertNotNull(productFullResponse.getDesc());
        }
    }
}