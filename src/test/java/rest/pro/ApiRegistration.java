package rest.pro;


import io.qameta.allure.restassured.AllureRestAssured;
import models.EmailResponseError;
import models.RegisterResponse;
import models.ResponseEmail;
import models.UserRegistration;

import static io.qameta.allure.Allure.step;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;


@Tag("ApiRegistration")
public class ApiRegistration {

    private static final String URL_LOGIN = "/api/login";
    private static final UserRegistration USER_PUT2 = new UserRegistration("eve.holt@reqres.in", "cityslicka");
    private static final ResponseEmail RESPONSE_EMAIL = new ResponseEmail("jsdgh@mail.ru");


    @Test
    void shoudSuccessfullyLoginWith200Status() {

        RegisterResponse response = step("shoudSuccessfullyLoginWith200Status", () -> given()
                .filter(new AllureRestAssured())
                .spec(SpecCustoms.requestSpecification)
                .body(USER_PUT2)
                .when()
                .post(URL_LOGIN)
                .then()
                .spec(SpecCustoms.responseSpecification)
                .extract().as(RegisterResponse.class));

       step("check SuccessfullyLoginWith200Status",()->  assertEquals("QpwL5tke4Pnpja7X4", response.getToken()));
    }

    @Test
    void shoudReturn400Status() {

        EmailResponseError error = step("shoudReturn400Status", () -> given()
                .filter(new AllureRestAssured())
                .spec(SpecCustoms.requestSpecification)
                .body(RESPONSE_EMAIL)
                .when()
                .post(URL_LOGIN)
                .then()
                .spec(SpecCustoms.responseSpecification400Missing)
                .extract().as(EmailResponseError.class));

        step("400Status check",()->assertEquals("Missing password",error.getError()));
    }
}
