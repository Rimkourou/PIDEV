package controller.sana;

import org.json.JSONObject;

import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author Crunchify.com
 * Overview and Simple Java Asynchronous HttpClient Client Tutorial
 */

public class CrunchifyJavaAsynchronousHTTPClient {

    public static void main(String args) throws Exception {

//        // Async HTTPClient Example
        //downloadImage(args);
    }

    public static final HttpClient crunchifyHttpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .connectTimeout(Duration.ofSeconds(5))
            .build();

    // HttpClient: implementation api  can be used to send requests and retrieve their responses. An HttpClient is created through a builder.

    // Duration: A time-based amount of time, such as '5 seconds'.
//call api

    public static String crunchifyAsyncHTTPClient(String keyword) {

        HttpRequest crunchifyRequest = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("https://google-search3.p.rapidapi.com/api/v1/images/q="+keyword))
                .setHeader("x-rapidapi-key", "0bf50a4a12msh026a7ca947dcfb8p11d6ddjsn0fed481adc94")
                .setHeader("x-rapidapi-host", "google-search3.p.rapidapi.com")
                .build();

        CompletableFuture<HttpResponse<String>> crunchifyAsyncResponse = null;

        // sendAsync(): Sends the given request asynchronously using this client with the given response body handler.
        //Equivalent to: sendAsync(request, responseBodyHandler, null).
        crunchifyAsyncResponse = crunchifyHttpClient.sendAsync(crunchifyRequest, HttpResponse.BodyHandlers.ofString());

        String crunchifyAsyncResultBody = null;
        int crunchifyAsyncResultStatusCode = 0;

        try {
            crunchifyAsyncResultBody = crunchifyAsyncResponse.thenApply(HttpResponse::body).get(60, TimeUnit.SECONDS);
            crunchifyAsyncResultStatusCode = crunchifyAsyncResponse.thenApply(HttpResponse::statusCode).get(60, TimeUnit.SECONDS);

        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
        }

        crunnchifyPrint("=============== AsyncHTTPClient Body:===============  \n" + crunchifyAsyncResultBody);
        crunnchifyPrint("\n=============== AsyncHTTPClient Status Code:===============  \n" + crunchifyAsyncResultStatusCode);
        return crunchifyAsyncResultBody;
    }

    public static void crunnchifyPrint(Object data) {
        System.out.println(data);

    }
//lien de recherche , repjson
    public static void downloadImage(String keyword) throws Exception {
        //Read JSON response and print
        JSONObject myResponse = new JSONObject(crunchifyAsyncHTTPClient(keyword));

        System.out.println("result after Reading JSON Response");
        System.out.printf("image: "+myResponse.getJSONArray("image_results").getJSONObject(1).getJSONObject("image").getString("src"));
        String uri = myResponse.getJSONArray("image_results").getJSONObject(1).getJSONObject("image").getString("src");
        System.out.println("statusCode- " + uri);

//down , stock
        try (InputStream inS = new URL(uri).openStream()) {
            Files.copy(inS, Paths.get("./TniShowDesktop/TniShowDesktop/src/Data/specImage-" +keyword+".png"));
        }
    }
}
