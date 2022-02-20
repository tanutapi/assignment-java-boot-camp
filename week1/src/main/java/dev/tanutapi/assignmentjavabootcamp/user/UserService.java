package dev.tanutapi.assignmentjavabootcamp.user;

import dev.tanutapi.assignmentjavabootcamp.userShippingAddress.UserShippingAddressResponse;
import dev.tanutapi.assignmentjavabootcamp.userShippingAddress.UserShippingAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.spec.KeySpec;
import java.util.*;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserShippingAddressService userShippingAddressService;

    @Value("${jwtTokenValidity}")
    private long jwtTokenValidity;

    @Value("${jwtSecret}")
    private String jwtSecret;

    public Optional<User> verifyUsernameAndPassword(String username, String password) {
        try {
            Optional<User> optionalUser = userRepository.findByUsername(username);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                Base64.Decoder dec = Base64.getDecoder();
                byte[] salt = dec.decode(user.getSalt());
                KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
                SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
                byte[] hash = f.generateSecret(spec).getEncoded();
                Base64.Encoder enc = Base64.getEncoder();
                if (enc.encodeToString(hash).equals(user.getHashedPassword())) {
                    return Optional.of(user);
                }
            }
        } catch (Exception ignored) {
        }
        return Optional.empty();
    }

    public User createUser(String username, String password, String firstName, String lastName) throws Exception {
        User user = new User();
        user.setUsername(username);
        user.setFirstName(firstName);
        user.setLastName(lastName);

        byte[] salt = new byte[16];
        Random random = new Random();
        random.nextBytes(salt);
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
        SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = f.generateSecret(spec).getEncoded();
        Base64.Encoder enc = Base64.getEncoder();
        user.setSalt(enc.encodeToString(salt));
        user.setHashedPassword(enc.encodeToString(hash));

        user = userRepository.save(user);

        return user;
    }

    public String generateJsonWebToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", user.getUsername());
        claims.put("firstName", user.getFirstName());
        claims.put("lastName", user.getLastName());
        return Jwts.builder().setClaims(claims)
                .setSubject("" + user.getId())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtTokenValidity * 1000))
                .signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
    }

    public UserResponse getUser(Integer userId) {
        Optional<User> optUser = userRepository.findById(userId);
        if (optUser.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No user found for userId");
        }
        User user = optUser.get();
        UserResponse response = new UserResponse();
        response.setUserId(user.getId());
        response.setUsername(user.getUsername());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        try {
            UserShippingAddressResponse shippingAddress = userShippingAddressService.getUserShippingAddress(userId);
            response.setShippingAddress(shippingAddress);
        } catch (Exception ignore) {
        }
        return response;
    }
}
