package rest.pro;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.http.ContentType.JSON;

public class SpecCustoms {

   public static RequestSpecification requestSpecification = new RequestSpecBuilder().log(LogDetail.ALL)
                .addHeader("x-api-key", "reqres-free-v1")
                .setContentType(JSON)
                .setBaseUri("https://reqres.in")
                .build();

   public static ResponseSpecification responseSpecification = new ResponseSpecBuilder()
           .log(LogDetail.ALL)
           .expectStatusCode(200)
           .build();


   public static ResponseSpecification responseSpecificationPost = new ResponseSpecBuilder()
           .log(LogDetail.ALL)
           .expectStatusCode(201)
           .build();


   public static ResponseSpecification responseSpecificationDelete = new ResponseSpecBuilder()
           .log(LogDetail.ALL)
           .expectStatusCode(204)
           .build();


   public static ResponseSpecification responseSpecification404Empty = new ResponseSpecBuilder()
           .expectStatusCode(404)
           .log(LogDetail.ALL)
           .build();

   public static ResponseSpecification responseSpecification400Missing = new ResponseSpecBuilder()
           .expectStatusCode(400)
           .log(LogDetail.ALL)
           .build();



}
