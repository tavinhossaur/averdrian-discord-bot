package services;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

public class BotHttpClient {

    // Método para retornar o body do json dataset 
    public String returnData(String url){
        try {
            URI address = URI.create(url);

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder(address).GET().build();
    
            // Utilizando o cliente http, o request é feito, e se espera um retorno do body em string
            HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
    
            return response.body();

        } catch (IOException | InterruptedException ex) {
            System.err.println();
            // O erro mais provável é que a API não está rodando
            System.err.println("ERRO: HttpRequest sem retorno (API possivelmente fora do ar)\n");
            throw new RuntimeException(ex);
        }
    }
}
