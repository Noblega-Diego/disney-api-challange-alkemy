package com.history.api.disney.services;

import com.history.api.disney.models.User;
import org.junit.jupiter.api.*;

import java.awt.*;
import java.io.Console;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class JwtServiceTest extends JwtService{

    private User user;
    private Map<String, Object> valuesForToken;

    @BeforeEach
    void tearUp(){
        System.out.println("tearUp");
        user = new User();
        user.setName("diego");
        user.setLastname("noblega");
        user.setEmail("diegomendoza2015@gmail.com");
        user.setId(12L);
        valuesForToken = new HashMap<>();
        valuesForToken.put("test", "test");
    }

    @Test
    @Order(14)
    @DisplayName("createToken(user, values): token not null")
    void createToken1() {
        String token =this.createToken(user, valuesForToken);
        assertNotNull(token);
    }

    @Test
    @Order(15)
    @DisplayName("createToken(user): token not null")
    void createToken0() {
        String token =this.createToken(user);
        assertNotNull(token);
    }


    @Test
    @Order(13)
    void testCreateToken() {
    }

    @Test
    @Order(13)
    void getValue() {
    }

    @Test
    @Order(12)
    void hasTokenExpired() {
    }

    @Test
    @Order(11)
    void extractUserName() {
    }

    @Test
    @Order(1)
    @DisplayName("extract id for token, not null")
    void extractIdNotNullId() {
        Long expected = 12L;
        Long actual;
        //---- set Id in User
        user.setId(expected);
        //---- generate Token
        String token = this.createToken(user, valuesForToken);
        //------------------
        actual = this.extractId(token);
        assertNotNull(actual);
        this.extractId(token);
    }

    @Test
    @Order(2)
    @DisplayName("extract correct id for token")
    void extractId() {
        Long expected = 12L;
        Long actual;
        //---- set Id in User
        user.setId(expected);
        //---- generate Token
        String token = this.createToken(user, valuesForToken);
        //------------------
        actual = this.extractId(token);
        assertEquals(expected, actual);
        this.extractId(token);
    }

    @Test
    @Order(10)
    void validateToken() {
        String token = this.createToken(user, valuesForToken);
        assertTrue(this.validateToken(token, user));
    }
}