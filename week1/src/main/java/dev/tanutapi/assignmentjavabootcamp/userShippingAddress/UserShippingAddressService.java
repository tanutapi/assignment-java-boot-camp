package dev.tanutapi.assignmentjavabootcamp.userShippingAddress;

import dev.tanutapi.assignmentjavabootcamp.user.User;
import dev.tanutapi.assignmentjavabootcamp.user.UserNotFoundException;
import dev.tanutapi.assignmentjavabootcamp.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserShippingAddressService {

    @Autowired
    UserShippingAddressRepository userShippingAddressRepository;

    @Autowired
    UserRepository userRepository;

    public UserShippingAddressResponse getUserShippingAddress(Integer userId) {
        Optional<UserShippingAddress> optUserShippingAddress = userShippingAddressRepository.findByUserId(userId);
        if (optUserShippingAddress.isEmpty()) {
            throw new ShippingAddressNotFoundException("Shipping address was not found for specified userId");
        }
        UserShippingAddress userShippingAddress = optUserShippingAddress.get();
        UserShippingAddressResponse response = new UserShippingAddressResponse();
        response.setUserId(userId);
        response.setName(userShippingAddress.getName());
        response.setAddress(userShippingAddress.getAddress());
        response.setDistrict(userShippingAddress.getDistrict());
        response.setProvince(userShippingAddress.getProvince());
        response.setPostcode(userShippingAddress.getPostCode());
        response.setTelephone(userShippingAddress.getTelephone());
        return response;
    }

    public UserShippingAddressResponse setUserShippingAddress(Integer userId, UserShippingAddressRequest userShippingAddressRequest) {
        Optional<User> optUser = userRepository.findById(userId);
        if (optUser.isEmpty()) {
            throw new UserNotFoundException("User was not found for specified userId");
        }
        User user = optUser.get();

        Optional<UserShippingAddress> optUserShippingAddress = userShippingAddressRepository.findByUserId(userId);
        UserShippingAddress userShippingAddress;
        if (optUserShippingAddress.isEmpty()) {
            userShippingAddress = new UserShippingAddress();
        } else {
            userShippingAddress = optUserShippingAddress.get();
        }
        userShippingAddress.setUser(user);
        userShippingAddress.setName(userShippingAddressRequest.getName());
        userShippingAddress.setAddress(userShippingAddressRequest.getAddress());
        userShippingAddress.setDistrict(userShippingAddressRequest.getDistrict());
        userShippingAddress.setProvince(userShippingAddressRequest.getProvince());
        userShippingAddress.setPostCode(userShippingAddressRequest.getPostcode());
        userShippingAddress.setTelephone(userShippingAddressRequest.getTelephone());
        userShippingAddressRepository.save(userShippingAddress);

        return getUserShippingAddress(userId);
    }
}
