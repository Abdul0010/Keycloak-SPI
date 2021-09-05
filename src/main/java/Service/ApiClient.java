package Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import dto.ApiRequest;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import util.CustomConstant;

public class ApiClient {
  public static HttpResponse<String> userApiAuth(String username, String password) throws IOException, InterruptedException {
    ApiRequest requestBody= new ApiRequest(username, password);
    ObjectMapper objectMapper= new ObjectMapper();
    String body = objectMapper.writeValueAsString(requestBody);

    HttpClient client = HttpClient.newHttpClient();
    HttpRequest request= HttpRequest.newBuilder().uri(URI.create(CustomConstant.API_URL))
        .header("Accept", "application/json")
        .header("Content-Type", "application/json")
        .POST(HttpRequest.BodyPublishers.ofString(body))
        .build();
    HttpResponse<String> response= client.send(request,HttpResponse.BodyHandlers.ofString());
    return response;
  }}
