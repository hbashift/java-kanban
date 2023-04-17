package KV.client;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class KVTaskClient {
    private final URI url;
    private final HttpClient client;
    private String API_TOKEN;

    public KVTaskClient(URI url) {
        client = HttpClient.newHttpClient();
        this.url = url;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url + "register/"))
                .GET()
                .build();

        System.out.println(url);

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                API_TOKEN = response.body();
                System.out.println(API_TOKEN);
            } else {
                System.out.println("Something's wrong. Response status code is " + response.statusCode());
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("Во время выполнения запроса возникла ошибка.\n" +
                    "Проверьте, пожалуйста, адрес и повторите попытку.");
        }
    }

    public void put(String key, String json) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url + "save/" + key + "?API_TOKEN=" + API_TOKEN))
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            int statusCode = response.statusCode();

            switch (statusCode) {
                case 400:
                    System.out.println("В запросе содержится ошибка. Проверьте параметры и повторите запрос.");
                    break;
                case 403:
                    System.out.println("Неверный API_TOKEN. Пожалуйста, проверьте API_TOKEN "
                            + "на корректность и повторите запрос");
                    break;
                case 404:
                    System.out.println("По указанному адресу нет ресурса. "
                            + "Проверьте URL-адрес ресурса и повторите запрос.");
                    break;
                case 500:
                    System.out.println("На стороне сервера произошла непредвиденная ошибка.");
                    break;
                case 503:
                    System.out.println("Cервер временно недоступен. Попробуйте повторить запрос позже.");
                    break;
                default:
                    System.out.println(statusCode);
                    System.out.println(response.body());
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("Во время выполнения запроса ресурса по url-адресу: '" + url + "' возникла ошибка.\n" +
                    "Проверьте, пожалуйста, адрес и повторите попытку.");
            e.printStackTrace();
        }
    }

    public String load(String key) {
        String result = null;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url + "load/" + key + "?API_TOKEN=" + API_TOKEN))
                .GET()
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            int statusCode = response.statusCode();

            switch (statusCode) {
                case 400:
                    System.out.println("В запросе содержится ошибка. Проверьте параметры и повторите запрос.");
                    break;
                case 403:
                    System.out.println(
                            "Неверный API_TOKEN. Пожалуйста, проверьте API_TOKEN на корректность и повторите "
                                    + "запрос");
                    break;
                case 404:
                    System.out.println("По указанному адресу нет ресурса. "
                            + "Проверьте URL-адрес ресурса и повторите запрос.");
                    break;
                case 500:
                    System.out.println("На стороне сервера произошла непредвиденная ошибка.");
                    break;
                case 503:
                    System.out.println("Cервер временно недоступен. Попробуйте повторить запрос позже.");
                    break;
                default:
                    JsonElement element = JsonParser.parseString(response.body());

                    if (!element.isJsonObject()) {
                        System.out.println("Ответ от сервера не соответствует ожидаемому.");
                        return null;
                    }

                    result = element.getAsJsonObject().toString();
            }

        } catch (IOException | InterruptedException e) {
            System.out.println("Во время выполнения запроса ресурса по url-адресу: '" + url + "' возникла ошибка.\n" +
                    "Проверьте, пожалуйста, адрес и повторите попытку.");
            e.printStackTrace();
        }

        return result;
    }

    public static void main(String[] args) {
        KVTaskClient client1 = new KVTaskClient(URI.create("http://localhost:8078/"));
        client1.put("Chen", "{\"name\": 123}");
        System.out.println(client1.load("Chen"));
    }
}
