package liveproject;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.lessThan;

public class GitHubProjectTesting {

    RequestSpecification requestSpec;
    ResponseSpecification responseSpec;
    int idSSH;
    String KeySSH = "ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABAQCTGXI5wbJfHXZsnhT0iJLARXH0dlrPFaHFbP4K/hhmnoNtwYf5ZdRZTlpMmzm8s4/pMBlZsO12332XtlfNXlpyLwjUtZuTHybQTYXAklkno74caNTuk8rAha8BjTGzG45giVg/1j66PrOCd4aeaqLZH8va3oD3i2C8VqOcZ2NQ2M2hGbfwuuYBHTuqGiFj5nXPQygDbsMKuAyar8CNpaLM5tGiqPS80JWvxPQxTFwXezd6EqKzygYzz9lC3wRKmI2uyuqu1sOfrw8jhWEZiMvKk7vj0BrJqLltnNILHasiWJfC/8pkd00uKgDKK629aGaCssGMzKemPL7YkJJqwqT7";

    @BeforeClass
    public void setUp() {
        requestSpec = new RequestSpecBuilder().
                setBaseUri("https://api.github.com").
                setContentType(ContentType.JSON).addHeader("Authorization", "token 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").
                build();

        responseSpec = new ResponseSpecBuilder().
                build();
    }


    @Test(priority=1)
    public void addSshKey()
    {
        //Request Body
        Map<String,Object> reqBody = new HashMap<>();
        reqBody.put("title", "TestAPIKey");
        reqBody.put("key", KeySSH);

        //Generate Response
        Response response = given().log().all().spec(requestSpec).body(reqBody).when().post("/user/keys");
        System.out.println(response.getBody().asPrettyString());

        //Extract the id
        idSSH = response.then().extract().path("id");
        System.out.println(idSSH);

        //Assertions
        response.then().log().all().spec(responseSpec).statusCode(201);
    }

    @Test(priority=2)
    public void getRequestSSH() {
        //Generate Response and assert
        given().log().all().spec(requestSpec).pathParam("id", idSSH).
                when().get("/user/keys/{id}").
                then().log().all().spec(responseSpec).statusCode(200);

    }

    @Test(priority=3)
    public void deleteRequestSSH() {
        given().log().all().spec(requestSpec).pathParam("id", idSSH).
                when().delete("/user/keys/{id}").
                then().log().all().spec(responseSpec).statusCode(204);
    }
}
