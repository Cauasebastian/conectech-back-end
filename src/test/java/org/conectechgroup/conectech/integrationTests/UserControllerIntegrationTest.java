package org.conectechgroup.conectech.integrationTests;

import org.conectechgroup.conectech.DTO.UserDTO;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerIntegrationTest {

    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.4.2");

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @DynamicPropertySource
    static void mongoProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @BeforeAll
    static void setUp() {
        mongoDBContainer.start();
    }

    @AfterAll
    static void tearDown() {
        mongoDBContainer.stop();
    }

    @Test
    public void testFindAllUsers() {
        String url = "http://localhost:" + port + "/users";
        UserDTO[] users = restTemplate.getForObject(url, UserDTO[].class);
        assertThat(users).isNotNull();
        assertThat(users).hasSizeGreaterThanOrEqualTo(0);
    }

    @Test
    public void testCreateUser() {
        String url = "http://localhost:" + port + "/users";
        UserDTO userDTO = new UserDTO();
        userDTO.setName("TestUser");
        userDTO.setEmail("testuser@example.com");
        UserDTO createdUser = restTemplate.postForObject(url, userDTO, UserDTO.class);
        assertThat(createdUser).isNotNull();
        assertThat(createdUser.getName()).isEqualTo("TestUser");
        assertThat(createdUser.getEmail()).isEqualTo("testuser@example.com");
    }
}