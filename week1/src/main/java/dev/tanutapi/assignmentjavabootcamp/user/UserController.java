package dev.tanutapi.assignmentjavabootcamp.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.resource.HttpResource;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.Optional;
import java.util.Random;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest) throws Exception {
        if (loginRequest.getUsername() != null && loginRequest.getPassword() != null) {
            Optional<User> user = userService.verifyUsernameAndPassword(loginRequest.getUsername(), loginRequest.getPassword());
            if (user.isPresent()) {
                String jwt = userService.generateJsonWebToken(user.get());
                return new LoginResponse(jwt);
            }
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/users/{userId}")
    public UserResponse getUser(@PathVariable Integer userId) {
        return userService.getUser(userId);
    }
}
