package rest.pro;

import io.qameta.allure.restassured.AllureRestAssured;
import models.*;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spec.SpecCustoms;

import static helpers.CustomApiListener.withCustomTemplates;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;



@Tag("Api")
public class Api extends TestBase{
    private static final UserJson USER = new UserJson("morpheus", "leader");
    private static final UserJson USER_PUT = new UserJson("Samson", "lead");
    private static final String UNKNOWN = "/unknown/23";
    private static final String URL = "/users";

    @Test
    void shouldReturnCorrectEmailOfFirstUser() {
        UserResponseData response = step("shouldReturnCorrectEmailOfFirstUser", () -> given()
                .filter(withCustomTemplates())
                .spec(SpecCustoms.requestSpecification)
                .when()
                .get(URL)
                .then()
                .spec(SpecCustoms.responseSpecificationBuilder(200))
                .extract().as(UserResponseData.class));

        step("get correctEmail check", () -> assertEquals("janet.weaver@reqres.in", response.getData().get(1).getEmail()));


    }

    @Test
    void sizeOfArrayShouldBeCorrect() {
        UserResponseData response = step("sizeOfArrayShouldBeCorrect", () -> given()
                .filter(withCustomTemplates())
                .spec(SpecCustoms.requestSpecification)
                .when()
                .get(URL)
                .then()
                .spec(SpecCustoms.responseSpecificationBuilder(200))
                .extract().as(UserResponseData.class));

        step("get correctSize check", () ->assertEquals(6, response.getData().size()));


    }

    @Test
    void shouldAddUser() {
        UserJsonPostResponse user = step("shouldAddUser", () -> given()
                .filter(withCustomTemplates())
                .spec(SpecCustoms.requestSpecification)
                .body(USER)
                .when()
                .post(URL)
                .then()
                .spec(SpecCustoms.responseSpecificationBuilder(201))
                .extract().as(UserJsonPostResponse.class));

        step("add check", () -> assertAll(() -> assertEquals("morpheus", user.getName()),
                () -> assertEquals("leader", user.getJob()),
                () -> assertNotNull(user.getId()),
                () -> assertNotNull(user.getCreatedAt())));

    }

    @Test
    void shouldDeleteUser() {

        step("shouldDeleteUser", () -> given()
                .filter(withCustomTemplates())
                .spec(SpecCustoms.requestSpecification)
                .when()
                .delete(URL + "/2")
                .then()
                .spec(SpecCustoms.responseSpecificationBuilder(204)));

        step("deletion is successful");

    }

    @Test
    void shouldUpdateUser() {

        UserJsonPutResponse putResponse = step("shouldUpdateUser", () -> given()
                .filter(withCustomTemplates())
                .spec(SpecCustoms.requestSpecification)
                .body(USER_PUT)
                .when()
                .put(URL + "/2")
                .then()
                .spec(SpecCustoms.responseSpecificationBuilder(200))
                .extract().as(UserJsonPutResponse.class));


        step("check update", () -> assertAll(() -> assertEquals("Samson", putResponse.getName()),
                () -> assertEquals("lead", putResponse.getJob()),
                () -> assertNotNull(putResponse.getUpdatedAt())));


    }


    @Test
    void shoudReturnEmpty404Status() {

        String response = step("shoudReturnEmpty404Status", () -> given()
                .filter(withCustomTemplates())
                .spec(SpecCustoms.requestSpecification)
                .when()
                .get(UNKNOWN)
                .then()
                .spec(SpecCustoms.responseSpecificationBuilder(404))
                .extract().asString());

        step("delete check", () -> assertEquals("{}", response));
    }


}
