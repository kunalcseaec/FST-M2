package examples;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static io.restassured.RestAssured.given;

public class WriteFilesResponseRestAssured {
    // Set base URL
    String ROOT_URI = "https://petstore.swagger.io/v2/pet";

    @Test
    public void getPetInfo() {
        // Set file path
        File resJSONFile = new File("src/main/resources/resLog.json");
        Response response =
                given().contentType(ContentType.JSON) // Set headers
                        .when().get(ROOT_URI + "/77232"); // Send GET request

        // Get response body
        String resBody = response.asPrettyString();

        try {
            // Create log file
            resJSONFile.createNewFile();
            // Write response body to external file
            FileWriter writer = new FileWriter(resJSONFile.getPath());
            writer.write(resBody);
            writer.close();
        } catch (IOException excp) {
            excp.printStackTrace();
        }
    }
}
