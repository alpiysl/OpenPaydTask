package com.openpayd.APITest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static org.junit.Assert.*;
import org.junit.Test;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApiTask extends BaseAPI {

    @Test
    public void task1() {
        Response response = given().
                contentType(ContentType.JSON).
                when().
                baseUri(BASE_URI).
                get();
        response.then().log().ifError();

        List<Integer> userIds = lastResponse().jsonPath().getList("userId");
        System.out.println(userIds.size());
        for (int i = 1; i <= userIds.size()/10; i++) {
            Response response1 = given().
                    contentType(ContentType.JSON).
                    when().
                    baseUri(BASE_URI).
                    queryParam("userId",i).
                    get();
            response1.then().log().ifError();
            Map<Integer,Integer> outPut = new HashMap<>();
            outPut.put(i,lastResponse().jsonPath().getList("id").size());
            System.out.println(outPut);
            Map<Integer,Integer> expected = new HashMap<>();
            expected.put(i,10);
            assertEquals(expected, outPut);
        }
    }

    @Test
    public void task2(){
        Response response = given().
                contentType(ContentType.JSON).
                when().
                baseUri(BASE_URI).
                get();
        response.then().log().ifError();
        List<Integer> ids = lastResponse().jsonPath().getList("id");
        for (int i = 0; i < ids.size()-1; i++) {
            for (int j = 1+i; j < ids.size(); j++) {
                System.out.println(ids.get(i)+" "+ids.get(j));
                assertNotEquals(ids.get(i),ids.get(j));
            }
        }
    }
}
