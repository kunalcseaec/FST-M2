package examples;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class SendingPutRequest {
    // Set Base URL
    final static String ROOT_URI = "https://petstore.swagger.io/v2/pet";

    @Test
    public void AddNewPet() {
        // Write the request body
        String reqBody = "{\"id\": 77232,\"name\": \"Hansel\",\"status\": \"alive\"}";

    Response response =
            given().contentType(ContentType.JSON) // Set headers
                    .body(reqBody).when().put(ROOT_URI); // Send PUT request

    // Print the response
    String resBody = response.getBody().asPrettyString();
    System.out.println(resBody);

    // Assert the updates
    response.then().body("name", equalTo("Hansel"));
}
}
