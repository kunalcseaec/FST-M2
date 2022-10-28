package liveproject;

import au.com.dius.pact.consumer.dsl.*;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;


import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

@ExtendWith(PactConsumerTestExt.class)
public class ConsumerTest {
    //Create the Headers
    Map<String, String> headers = new HashMap<>();
    // Set the Resource path
    String resourcePath = "/api/users";

    //Create the contract
    @Pact(consumer = "UserConsumer",provider = "UserProvider" )
    public RequestResponsePact createPact(PactDslWithProvider builder) {
        //Set the Headers
        headers.put("Content-Type", "application/json");

        //Create the body
        DslPart requestResponseBody = new PactDslJsonBody()
                .numberType("id" , 123)
                .stringType("firstName", "Kunal")
                .stringType("lastName", "Dasgupta")
                .stringType("email", "dasgupta@example.com");
        //Record interaction to pact
        return builder.given("A request to create a user")
                .uponReceiving("A request to create a user")
                .method("Post")
                .path(resourcePath)
                .headers(headers)
                .body(requestResponseBody)
            .willRespondWith()
                .status(201)
                .body(requestResponseBody)
                .toPact();
    }
    @Test
    @PactTestFor(providerName = "UserProvider" , port ="8282")
    public void consumerTest() {
        //baseURI
        String baseURI = "http://localhost:8282"+resourcePath;

        //Request Body
        Map<String , Object> reqbody = new HashMap<>();
        reqbody.put("id", 123);
        reqbody.put("firstName", "Kunal");
        reqbody.put("lastName", "dasgupta");
        reqbody.put("email", "dasgupta@example.com");

        //generate Response
        given().headers(headers).body(reqbody).
        when().post(baseURI).
        then().statusCode(201).log().all();

    }
}
