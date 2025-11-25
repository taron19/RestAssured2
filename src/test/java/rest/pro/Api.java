package rest.pro;

import models.*;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

public class Api {
    private static final UserJson USER = new UserJson("morpheus", "leader");
    private static final UserJson USER_PUT = new UserJson("Samson", "lead");
    private static final String UNKNOWN = "/api/unknown/23";
    private static final String URL = "/api/users";

    @Test
    void shouldReturnCorrectEmailOfFirstUser() {
        UserResponseData response = step("shouldReturnCorrectEmailOfFirstUser", () -> given()
                .spec(SpecCustoms.requestSpecification)
                .when()
                .get(URL)
                .then()
                .spec(SpecCustoms.responseSpecification)
                .extract().as(UserResponseData.class));

        step("get correctEmail check", () -> assertEquals("janet.weaver@reqres.in", response.getData().get(1).getEmail()));


    }

    @Test
    void sizeOfArrayShouldBeCorrect() {
        UserResponseData response = step("sizeOfArrayShouldBeCorrect", () -> given()
                .spec(SpecCustoms.requestSpecification)
                .when()
                .get(URL)
                .then()
                .spec(SpecCustoms.responseSpecification)
                .extract().as(UserResponseData.class));

        step("get correctSize check", () ->assertEquals(6, response.getData().size()));


    }

    @Test
    void shouldAddUser() {
        UserJsonPostResponse user = step("shouldAddUser", () -> given()
                .spec(SpecCustoms.requestSpecification)
                .body(USER)
                .when()
                .post(URL)
                .then()
                .spec(SpecCustoms.responseSpecificationPost)
                .extract().as(UserJsonPostResponse.class));

        step("add check", () -> assertAll(() -> assertEquals("morpheus", user.getName()),
                () -> assertEquals("leader", user.getJob()),
                () -> assertNotNull(user.getId()),
                () -> assertNotNull(user.getCreatedAt())));

    }

    @Test
    void shouldDeleteUser() {

        step("shouldDeleteUser", () -> given()
                .spec(SpecCustoms.requestSpecification)
                .when()
                .delete(URL + "/2")
                .then()
                .spec(SpecCustoms.responseSpecificationDelete));

        step("Verification successful");

    }

    @Test
    void shouldUpdateUser() {

        UserJsonPutResponse putResponse = step("shouldUpdateUser", () -> given()
                .spec(SpecCustoms.requestSpecification)
                .body(USER_PUT)
                .when()
                .put(URL + "/2")
                .then()
                .spec(SpecCustoms.responseSpecification)
                .extract().as(UserJsonPutResponse.class));


        step("check update", () -> assertAll(() -> assertEquals("Samson", putResponse.getName()),
                () -> assertEquals("lead", putResponse.getJob()),
                () -> assertNotNull(putResponse.getUpdatedAt())));


    }


    @Test
    void shoudReturnEmpty404Status() {

        String response = step("shoudReturnEmpty404Status", () -> given()
                .spec(SpecCustoms.requestSpecification)
                .when()
                .get(UNKNOWN)
                .then()
                .spec(SpecCustoms.responseSpecification404Empty)
                .extract().asString());

        step("delete check", () -> assertEquals("{}", response));
    }


}
