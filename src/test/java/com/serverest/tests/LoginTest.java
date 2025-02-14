package com.serverest.tests;

import com.serverest.model.LoginModel;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class LoginTest {

    @BeforeEach
    public void setup() {
        baseURI = "https://serverest.dev";
    }

    @Test
    @Tag("Health-Check")
    public void testHealthCheck() {

        given()
                .contentType(ContentType.JSON)
                .body(retornaLoginValido())
        .when()
                .post("/login")
        .then()
                .statusCode(200)
        ;
    }

    @Test
    @Tag("Contract")
    public void testLoginValidoJSONSchema() {

        given()
                .contentType(ContentType.JSON)
                .body(retornaLoginValido())
        .when()
                .post("/login")
        .then()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("valid_login_schema.json"))
        ;
    }

    @Test
    @Tag("Functional")
    public void testLoginValido() {

        given()
                .contentType(ContentType.JSON)
                .body(retornaLoginValido())
        .when()
                .post("/login")
        .then()
                .statusCode(200)
                .body("message", equalTo("Login realizado com sucesso"))
                .body("authorization", notNullValue())
        ;
    }

    public LoginModel retornaLoginValido() {
        LoginModel login = new LoginModel();
        login.setEmail("beltrano@qa.com.br");
        login.setPassword("teste");

        return login;
    }
}
