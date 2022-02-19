package dev.tanutapi.assignmentjavabootcamp.user;

import io.jsonwebtoken.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Value("${jwtSecret}")
    private String jwtSecret;

    @Test
    @DisplayName("No username or password were sent to login, it should return BAD REQUEST")
    void failCaseLoginNoUsernameAndPassword() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<LoginRequest> httpEntity = new HttpEntity<>(new LoginRequest(), headers);

        ResponseEntity<LoginResponse> result = testRestTemplate.postForEntity("/login", httpEntity, LoginResponse.class);
        assertEquals(result.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    @DisplayName("Incorrect username or password were sent to login, it should return NOT FOUND")
    void failCaseLoginIncorrectUsernameOrPassword() {
        String username = "xxxx";
        String password = "yyyy";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<LoginRequest> httpEntity = new HttpEntity<>(new LoginRequest(username, password), headers);

        ResponseEntity<LoginResponse> result = testRestTemplate.postForEntity("/login", httpEntity, LoginResponse.class);
        assertEquals(result.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    @DisplayName("Correct username or password were sent to login, it should return JWT")
    void successCaseLogin() {
        String username = "tanutapi";
        String password = "qwerty";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<LoginRequest> httpEntity = new HttpEntity<>(new LoginRequest(username, password), headers);

        ResponseEntity<LoginResponse> result = testRestTemplate.postForEntity("/login", httpEntity, LoginResponse.class);
        assertEquals(result.getStatusCode(), HttpStatus.OK);
        String token = Objects.requireNonNull(result.getBody()).getJwtToken();
        assert(token.length() > 0);
        assertDoesNotThrow(new Executable() {
            @Override
            public void execute() throws Throwable {
                Jwts.parser().setSigningKey(jwtSecret).parse(token);
            }
        });
        Jws<Claims> jwt = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
        assertEquals(jwt.getBody().get("username"), username);
    }
}