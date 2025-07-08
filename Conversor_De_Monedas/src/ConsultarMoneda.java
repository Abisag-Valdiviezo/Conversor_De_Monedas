import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConsultarMoneda {

    private static final String API_URL = "https://v6.exchangerate-api.com/v6/c75159b23d9496a6cd45321c/latest/";
    private final HttpClient client;
    private final Gson gson;

    public ConsultarMoneda() {
        client = HttpClient.newHttpClient();
        gson = new Gson();
    }
    public Moneda obtenerTasasDeCambio(String codigoBase) throws IOException, InterruptedException {
        String url = API_URL + codigoBase;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            return gson.fromJson(response.body(), Moneda.class);
        } else {
            System.err.println("Error al consultar la API. Código de estado: " + response.statusCode());
            return null;
        }
    }
}