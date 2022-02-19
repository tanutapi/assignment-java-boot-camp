package dev.tanutapi.assignmentjavabootcamp.shoppingCart;

import dev.tanutapi.assignmentjavabootcamp.product.ProductFullResponse;
import dev.tanutapi.assignmentjavabootcamp.product.ProductShortResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ShoppingCartControllerTest {

    @Autowired
    TestRestTemplate testRestTemplate;

    @Autowired
    ShoppingCartService shoppingCartService;

    @Test
    @DisplayName("It should return an empty shopping cart")
    void getShoppingCart() {
        Integer userId = 1;

        // Clear the shopping cart of userId
        shoppingCartService.clearShoppingCart(userId);

        Map<String, String> params = new HashMap<>();
        params.put("userId", "" + userId);
        ResponseEntity<ShoppingCartResponse> response = testRestTemplate.getForEntity("/shoppingcarts/{userId}", ShoppingCartResponse.class, params);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        ShoppingCartResponse shoppingCartResponse = response.getBody();
        assertNotNull(shoppingCartResponse);
        assertEquals(shoppingCartResponse.getUserId(), userId);
        assert(shoppingCartResponse.getProducts().isEmpty());
    }

    @Test
    @DisplayName("It should return 404 (NOT FOUND) if unknown user provided")
    void getShoppingCartOfUnknownUserShouldReturnNotFound() {
        Map<String, String> params = new HashMap<>();
        params.put("userId", "999");
        ResponseEntity<ShoppingCartResponse> response = testRestTemplate.getForEntity("/shoppingcarts/{userId}", ShoppingCartResponse.class, params);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @DisplayName("It should return BAD REQUEST if not specified product")
    void addProductIntoShoppingCartWithNoProduct() {
        int userId = 1;
        Integer productId = 3;
        Map<String, String> params = new HashMap<>();
        params.put("userId", "" + userId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        AddToCartRequest addToCartRequest = new AddToCartRequest();
        HttpEntity<AddToCartRequest> httpEntity = new HttpEntity<>(addToCartRequest, headers);

        ResponseEntity<ShoppingCartResponse> response = testRestTemplate.postForEntity("/shoppingcarts/{userId}", httpEntity, ShoppingCartResponse.class, params);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    @DisplayName("The shopping cart should contain the added product")
    void addProductIntoShoppingCart() {
        Integer userId = 1;

        // Clear the shopping cart of userId
        shoppingCartService.clearShoppingCart(userId);

        Map<String, String> params = new HashMap<>();
        params.put("userId", "" + userId);

        ResponseEntity<ProductShortResponse[]> shortProductResponse = testRestTemplate.getForEntity("/products", ProductShortResponse[].class);
        assertEquals(HttpStatus.OK, shortProductResponse.getStatusCode());
        assertNotNull(shortProductResponse.getBody());
        assert(shortProductResponse.getBody().length > 0);
        ProductShortResponse firstProductShortResponse = shortProductResponse.getBody()[0];

        Map<String, String> fullProductParams = new HashMap<>();
        fullProductParams.put("id", "" + firstProductShortResponse.getProductId());
        ResponseEntity<ProductFullResponse> fullProductResponse = testRestTemplate.getForEntity("/products/{id}", ProductFullResponse.class, fullProductParams);
        assertEquals(HttpStatus.OK, fullProductResponse.getStatusCode());
        assertNotNull(fullProductResponse.getBody());
        ProductFullResponse productFullResponse = fullProductResponse.getBody();
        assertNotNull(productFullResponse.getVariants().get(0));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        AddToCartRequest addToCartRequest = new AddToCartRequest(productFullResponse.getProductId(), productFullResponse.getVariants().get(0).getName(), 1);
        HttpEntity<AddToCartRequest> httpEntity = new HttpEntity<>(addToCartRequest, headers);

        ResponseEntity<ShoppingCartResponse> addToCartResponse = testRestTemplate.postForEntity("/shoppingcarts/{userId}", httpEntity, ShoppingCartResponse.class, params);
        assertEquals(HttpStatus.OK, addToCartResponse.getStatusCode());
        ShoppingCartResponse shoppingCartResponse = addToCartResponse.getBody();
        assertNotNull(shoppingCartResponse);
        assertEquals(shoppingCartResponse.getUserId(), userId);
        assert(!shoppingCartResponse.getProducts().isEmpty());
        List<ShoppingCartProductResponse> shoppingCartProductResponseList = shoppingCartResponse.getProducts().stream().filter(shoppingCartProductResponse -> shoppingCartProductResponse.getProductId() == productFullResponse.getProductId()).collect(Collectors.toList());
        assert(!shoppingCartProductResponseList.isEmpty());
    }

    @Test
    @DisplayName("The shopping cart should not contain the removed product")
    void removeProductFromShoppingCart() {
        Integer userId = 1;

        // Clear the shopping cart of userId
        shoppingCartService.clearShoppingCart(userId);

        Map<String, String> params = new HashMap<>();
        params.put("userId", "" + userId);

        ResponseEntity<ProductShortResponse[]> shortProductResponse = testRestTemplate.getForEntity("/products", ProductShortResponse[].class);
        assertEquals(HttpStatus.OK, shortProductResponse.getStatusCode());
        assertNotNull(shortProductResponse.getBody());
        assert(shortProductResponse.getBody().length > 0);
        ProductShortResponse firstProductShortResponse = shortProductResponse.getBody()[0];

        Map<String, String> fullProductParams = new HashMap<>();
        fullProductParams.put("id", "" + firstProductShortResponse.getProductId());
        ResponseEntity<ProductFullResponse> fullProductResponse = testRestTemplate.getForEntity("/products/{id}", ProductFullResponse.class, fullProductParams);
        assertEquals(HttpStatus.OK, fullProductResponse.getStatusCode());
        assertNotNull(fullProductResponse.getBody());
        ProductFullResponse productFullResponse = fullProductResponse.getBody();
        assertNotNull(productFullResponse.getVariants().get(0));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        AddToCartRequest addToCartRequest = new AddToCartRequest(productFullResponse.getProductId(), productFullResponse.getVariants().get(0).getName(), 1);
        HttpEntity<AddToCartRequest> httpEntity = new HttpEntity<>(addToCartRequest, headers);

        // Add product to shopping cart
        ResponseEntity<ShoppingCartResponse> addToCartResponse = testRestTemplate.postForEntity("/shoppingcarts/{userId}", httpEntity, ShoppingCartResponse.class, params);
        assertEquals(HttpStatus.OK, addToCartResponse.getStatusCode());
        ShoppingCartResponse shoppingCartResponse = addToCartResponse.getBody();
        assertNotNull(shoppingCartResponse);
        assertEquals(shoppingCartResponse.getUserId(), userId);
        assert(!shoppingCartResponse.getProducts().isEmpty());
        List<ShoppingCartProductResponse> shoppingCartProductResponseList = shoppingCartResponse.getProducts().stream().filter(shoppingCartProductResponse -> shoppingCartProductResponse.getProductId() == productFullResponse.getProductId()).collect(Collectors.toList());
        assert(!shoppingCartProductResponseList.isEmpty());

        // Remove from shopping cart
        Map<String, String> removeParams = new HashMap<>();
        removeParams.put("userId", "" + userId);
        removeParams.put("productId", "" + productFullResponse.getProductId());
        removeParams.put("variantName", "" + productFullResponse.getVariants().get(0).getName());
        testRestTemplate.delete("/shoppingcarts/{userId}/{productId}/{variantName}", removeParams);

        ResponseEntity<ShoppingCartResponse> response = testRestTemplate.getForEntity("/shoppingcarts/{userId}", ShoppingCartResponse.class, params);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        shoppingCartResponse = response.getBody();
        assertNotNull(shoppingCartResponse);
        assertEquals(shoppingCartResponse.getUserId(), userId);
        assert(shoppingCartResponse.getProducts().isEmpty());
    }
}