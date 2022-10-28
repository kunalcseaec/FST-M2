package examples;

import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FirstTest {

    // Set the BaseUURI
    String baseURI = "https://petstore.swagger.io/v2/pet";

    @Test

    public void getRequestWithQueryParam() {
        //Generate response and save it
        //https://petstore.swagger.io/v2/pet/findByStatus?Status=sold
        Response response =
                given().header("Content-Type", "application/json").queryParam("status", "sold").
                        when().get(baseURI + "/findByStatus");

        //Print the response body
        System.out.println(response.getBody().asString());
        System.out.println(response.getBody().asPrettyString());

        //Print the response headers
        System.out.println(response.getHeaders().asList());
        //Extract properties  from the response
        String petStatus = response.then().extract().path("[0].status");
        System.out.println(petStatus);
        //Assertions
        assertEquals(petStatus, "sold");
        response.then().statusCode(200).body("[0].status", equalTo("sold"));
        response.then().time(lessThan(10000L));
    }

        @Test
        public void getRequestWithPathParam() {
            //Generate response and write assertions
            given().header("Content-Type","application/json").pathParam("petId",9).
            when().get(baseURI +"/{petId}").
            then().statusCode(200).body("status",equalTo("sold")).time(lessThan(10000L));
        }
    }



