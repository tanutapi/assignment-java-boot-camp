package dev.tanutapi.assignmentjavabootcamp.invoice;

import dev.tanutapi.assignmentjavabootcamp.product.Product;
import dev.tanutapi.assignmentjavabootcamp.product.ProductService;
import dev.tanutapi.assignmentjavabootcamp.productVariant.ProductVariantRepository;
import dev.tanutapi.assignmentjavabootcamp.shoppingCart.ShoppingCart;
import dev.tanutapi.assignmentjavabootcamp.shoppingCart.ShoppingCartService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class InvoiceControllerTest {

    @Autowired
    TestRestTemplate testRestTemplate;

    @Autowired
    ProductService productService;

    @Autowired
    ShoppingCartService shoppingCartService;

    @Autowired
    InvoiceRepository invoiceRepository;

    @Test
    @DisplayName("User fail to checkout if userId is not exist")
    void failCaseCheckoutUserIdNotExist() {
        // User add two products into their shopping cart
        int userId = 9999;

        // User check out
        Map<String, String> params = new HashMap<>();
        params.put("userId", "" + userId);
        ResponseEntity<InvoiceResponse> invoiceResponseResponseEntity = testRestTemplate.postForEntity("/checkout/{userId}/cs", null, InvoiceResponse.class, params);
        assertEquals(HttpStatus.NOT_FOUND, invoiceResponseResponseEntity.getStatusCode());
    }

    @Test
    @DisplayName("User fail to checkout if user has no item in shopping cart")
    void failCaseCheckoutUserHasNoItem() {
        // User add two products into their shopping cart
        int userId = 1;

        // User check out
        Map<String, String> params = new HashMap<>();
        params.put("userId", "" + userId);
        ResponseEntity<InvoiceResponse> invoiceResponseResponseEntity = testRestTemplate.postForEntity("/checkout/{userId}/cs", null, InvoiceResponse.class, params);
        assertEquals(HttpStatus.NOT_FOUND, invoiceResponseResponseEntity.getStatusCode());
    }

    @Test
    @DisplayName("User success to checkout")
    void successCaseCheckout() {
        // User add two products into their shopping cart
        Integer userId = 1;
        List<Product> products = productService.findProducts("NMD");
        products.forEach(product -> {
            System.out.println();
            System.out.println(product.getId());
            System.out.println(product.getTitle());
            System.out.println(product.getDesc());
            System.out.println(product.getProductVariants());
            shoppingCartService.addProductToShoppingCart(userId, product.getId(), product.getProductVariants().get(0).getName(), 2);
        });
        List<ShoppingCart> shoppingCarts = shoppingCartService.getShoppingCart(userId);
        shoppingCarts.forEach(shoppingCart -> {
            System.out.println();
            System.out.println(shoppingCart.getId());
            System.out.println(shoppingCart.getUser().getId());
            System.out.println(shoppingCart.getProductVariant().getProduct().getTitle());
            System.out.println(shoppingCart.getProductVariant().getPrice());
            System.out.println(shoppingCart.getAmount());
            System.out.println(shoppingCart.getTotalPrice());
        });

        // User check out
        Map<String, String> params = new HashMap<>();
        params.put("userId", "" + userId);
        ResponseEntity<InvoiceResponse> invoiceResponseResponseEntity = testRestTemplate.postForEntity("/checkout/{userId}/cs", null, InvoiceResponse.class, params);
        assertEquals(HttpStatus.OK, invoiceResponseResponseEntity.getStatusCode());
        assertNotNull(invoiceResponseResponseEntity.getBody());
        InvoiceResponse invoiceResponse = invoiceResponseResponseEntity.getBody();
        System.out.println(invoiceResponse.getInvoiceId());
        System.out.println(invoiceResponse.getUserId());
        System.out.println(invoiceResponse.getPaymentMethod());
        System.out.println(invoiceResponse.isPaid());
        System.out.println(invoiceResponse.getTotalPrice());
        invoiceResponse.getItems().forEach(orderItemResponse -> {
            System.out.println(orderItemResponse.getTitle());
            System.out.println(orderItemResponse.getAmount());
            System.out.println(orderItemResponse.getTotalPrice());
        });

        // Confirm with the invoice data in the database
        Optional<Invoice> optInvoice = invoiceRepository.findById(invoiceResponse.getInvoiceId());
        assertFalse(optInvoice.isEmpty());
        Invoice invoice = optInvoice.get();
        assertEquals(invoiceResponse.getInvoiceId(), invoice.getId());
        assertEquals(invoiceResponse.getItems().size(), invoice.getItems().size());
        assertEquals(invoiceResponse.isPaid(), invoice.getPaid());
        assertEquals(invoiceResponse.getTotalPrice(), invoice.getTotalPrice());
    }
}