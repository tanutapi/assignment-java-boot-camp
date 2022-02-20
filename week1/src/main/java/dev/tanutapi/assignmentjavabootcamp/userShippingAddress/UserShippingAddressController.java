package dev.tanutapi.assignmentjavabootcamp.userShippingAddress;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserShippingAddressController {

    @Autowired
    UserShippingAddressService userShippingAddressService;

    @GetMapping("/users/{userId}/shippingaddress")
    UserShippingAddressResponse getUserShippingAddress(@PathVariable Integer userId) {
        // TODO verify JWT. Only return the shipping address of the JWT's subject!
        return userShippingAddressService.getUserShippingAddress(userId);
    }

    @PutMapping("/users/{userId}/shippingaddress")
    UserShippingAddressResponse setUserShippingAddress(@PathVariable Integer userId, @RequestBody UserShippingAddressRequest userShippingAddressRequest) {
        // TODO verify JWT. Only modify the shipping address of the JWT's subject!
        return userShippingAddressService.setUserShippingAddress(userId, userShippingAddressRequest);
    }
}
