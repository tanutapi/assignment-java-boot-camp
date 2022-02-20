package dev.tanutapi.assignmentjavabootcamp.userShippingAddress;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserShippingAddressControllerTest {

    @Autowired
    TestRestTemplate testRestTemplate;

    @Test
    @DisplayName("Return NOT FOUND if userId is not exist")
    void failCaseGetUserShippingAddressWithNonExistingUserId() {
        int userId = 9999;
        Map<String, String> params = new HashMap<>();
        params.put("userId", "" + userId);
        ResponseEntity<UserShippingAddressResponse> result = testRestTemplate.getForEntity("/users/{userId}/shippingaddress", UserShippingAddressResponse.class, params);
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    @DisplayName("Get user shipping address if userId is exist and user has shipping address")
    void successCaseGetUserShippingAddress() {
        int userId = 1;
        Map<String, String> params = new HashMap<>();
        params.put("userId", "" + userId);
        ResponseEntity<UserShippingAddressResponse> result = testRestTemplate.getForEntity("/users/{userId}/shippingaddress", UserShippingAddressResponse.class, params);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        UserShippingAddressResponse userShippingAddressResponse = result.getBody();
        assertEquals(userId, userShippingAddressResponse.getUserId());
        assertNotNull(userShippingAddressResponse.getName());
        assertNotNull(userShippingAddressResponse.getAddress());
        assertNotNull(userShippingAddressResponse.getDistrict());
        assertNotNull(userShippingAddressResponse.getProvince());
        assertNotNull(userShippingAddressResponse.getPostcode());
        assertNotNull(userShippingAddressResponse.getTelephone());
    }

    @Test
    @DisplayName("Set new shipping address for user")
    void successCaseSetUserShippingAddressNewAddress() {
        int userId = 2;
        // Before put new shipping addreess, it must not exist for userId=2
        Map<String, String> params = new HashMap<>();
        params.put("userId", "" + userId);
        ResponseEntity<UserShippingAddressResponse> result = testRestTemplate.getForEntity("/users/{userId}/shippingaddress", UserShippingAddressResponse.class, params);
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        UserShippingAddressRequest userShippingAddressRequest = new UserShippingAddressRequest();
        userShippingAddressRequest.setName("Hugo Man");
        userShippingAddressRequest.setAddress("Somewhere in TH");
        userShippingAddressRequest.setDistrict("Chatujak");
        userShippingAddressRequest.setProvince("Bangkok");
        userShippingAddressRequest.setTelephone("0895555555");
        HttpEntity<UserShippingAddressRequest> httpEntity = new HttpEntity<>(userShippingAddressRequest, headers);
        testRestTemplate.put("/users/{userId}/shippingaddress", httpEntity, params);

        // After put new shipping address, it must exist!
        result = testRestTemplate.getForEntity("/users/{userId}/shippingaddress", UserShippingAddressResponse.class, params);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        UserShippingAddressResponse userShippingAddressResponse = result.getBody();
        assertEquals(userId, userShippingAddressResponse.getUserId());
        assertEquals(userShippingAddressResponse.getName(), userShippingAddressRequest.getName());
        assertEquals(userShippingAddressResponse.getAddress(), userShippingAddressRequest.getAddress());
        assertEquals(userShippingAddressResponse.getDistrict(), userShippingAddressRequest.getDistrict());
        assertEquals(userShippingAddressResponse.getProvince(), userShippingAddressRequest.getProvince());
        assertEquals(userShippingAddressResponse.getPostcode(), userShippingAddressRequest.getPostcode());
        assertEquals(userShippingAddressResponse.getTelephone(), userShippingAddressRequest.getTelephone());
    }

    @Test
    @DisplayName("Update shipping address for user")
    void successCaseUpdateUserShippingAddress() {
        int userId = 1;
        // Before put new shipping addreess, it must not exist for userId=2
        Map<String, String> params = new HashMap<>();
        params.put("userId", "" + userId);
        ResponseEntity<UserShippingAddressResponse> result = testRestTemplate.getForEntity("/users/{userId}/shippingaddress", UserShippingAddressResponse.class, params);
        assertEquals(HttpStatus.OK, result.getStatusCode());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        UserShippingAddressRequest userShippingAddressRequest = new UserShippingAddressRequest();
        userShippingAddressRequest.setName("Tanut Apiwong");
        userShippingAddressRequest.setAddress("Somewhere in TH");
        userShippingAddressRequest.setDistrict("Chatujak");
        userShippingAddressRequest.setProvince("Bangkok");
        userShippingAddressRequest.setTelephone("0895555555");
        HttpEntity<UserShippingAddressRequest> httpEntity = new HttpEntity<>(userShippingAddressRequest, headers);
        testRestTemplate.put("/users/{userId}/shippingaddress", httpEntity, params);

        // After put new shipping address, it must exist!
        result = testRestTemplate.getForEntity("/users/{userId}/shippingaddress", UserShippingAddressResponse.class, params);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        UserShippingAddressResponse userShippingAddressResponse = result.getBody();
        assertEquals(userId, userShippingAddressResponse.getUserId());
        assertEquals(userShippingAddressResponse.getName(), userShippingAddressRequest.getName());
        assertEquals(userShippingAddressResponse.getAddress(), userShippingAddressRequest.getAddress());
        assertEquals(userShippingAddressResponse.getDistrict(), userShippingAddressRequest.getDistrict());
        assertEquals(userShippingAddressResponse.getProvince(), userShippingAddressRequest.getProvince());
        assertEquals(userShippingAddressResponse.getPostcode(), userShippingAddressRequest.getPostcode());
        assertEquals(userShippingAddressResponse.getTelephone(), userShippingAddressRequest.getTelephone());
    }
}