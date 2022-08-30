package requirements;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;
import org.testng.annotations.Test;
import static io.restassured.path.json.JsonPath.from;
import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.get;
import static org.hamcrest.MatcherAssert.assertThat;


public class RestTests {


    @Test
    public void firstAndSecondRequirements(){

        String response1 = get("https://jsonplaceholder.typicode.com/posts/1/comments").asString();
        System.out.println(response1);
        String response2 = get("https://jsonplaceholder.typicode.com/users/2/albums").asString();
        for(int i = 0 ; i < 10 ; i++ ){
            String path = "[" +  i + "].title.length()";
            int titleLength = from(response2).getInt(path);
            assertThat(titleLength, lessThan(300));
        }
    }
    @Test
    public void thirdRequirement(){
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com/posts";
        RequestSpecification request = RestAssured.given();
        JSONObject requestParams = new JSONObject();
        requestParams.put("userId", 2);
        requestParams.put("id", 101);
        requestParams.put("title","Hello");
        requestParams.put("body","Hello from the other side");
        request.header("Content-Type", "application/json");
        request.body(requestParams.toJSONString());
        Response response = request.post("https://jsonplaceholder.typicode.com/posts");
        System.out.println("The status received: " + response.statusLine());

    }

    @Test
    public void fourthRequirement(){

        String response1 = get("https://jsonplaceholder.typicode.com/users/2/todos?complete=%27false%27").asString();
        for(int i = 0 ; i < 20 ; i++ ){
            String path = "[" +  i + "].title";
            String title = from(response1).getString(path);
            System.out.println("title " + i + " : " +title);
        }
    }
}
