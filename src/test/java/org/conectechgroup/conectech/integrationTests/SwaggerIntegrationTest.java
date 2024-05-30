package org.conectechgroup.conectech.integrationTests;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.containers.GenericContainer;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.containsStringIgnoringCase;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SwaggerIntegrationTest {

    private static final String BASE_URI = "http://localhost:8080";
    private static final String SWAGGER_UI_ENDPOINT = "/swagger-ui/index.html";

    @Container
    public static GenericContainer<?> mongoContainer = new GenericContainer<>("mongo:4.4.3")
            .withExposedPorts(27017);

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = BASE_URI;
    }

    @Test
    public void testSwaggerUIIsAccessible() {
        given()
                .when()
                .get(SWAGGER_UI_ENDPOINT)
                .then()
                .log().ifError() // Log response if the test fails
                .statusCode(200)
                .body(containsStringIgnoringCase("Swagger UI"))
                .body(containsString("swagger-ui.css"))
                .body(containsString("swagger-ui-bundle.js"));
    }
}