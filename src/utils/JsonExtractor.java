package utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import model.Champion;

public class JsonExtractor {

    // Método de extração de dados do json retornado pelo http request
    public List<Champion> extractContent(String json){
        
        // Instanciando um JsonParser e parseando para uma list<map> de atributos
        JsonParser parser = new JsonParser();
        List<Map<String, String>> attributesList = parser.parse(json);

        // Instanciando uma nova lista (array list) que receberá os campeões organizados
        List<Champion> champArray = new ArrayList<>();
        
        // Populando o array list com foreach
        for (Map<String, String> attributes : attributesList) {
            // Para cada item, divida seus atributos
            String name = attributes.get("name");
            String quote1 = attributes.get("quote1");
            String quote2 = attributes.get("quote2");
            String emotes = attributes.get("emotes");

            // Instancie um novo campeão com esses atributos
            var champ = new Champion(name, quote1, quote2, emotes);
            
            // E o adicione ao array list
            champArray.add(champ);
        }
        return champArray;
    }
}
