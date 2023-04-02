package utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JsonParser {
    
    // Padrões de expressões regulares para divisão de itens e atributos no json dataset
    private static final Pattern REGEX_ITEMS = Pattern.compile(".*\\[(.+)\\].*");
    private static final Pattern REGEX_JSON_ATTRIBUTES = Pattern.compile("\"(.+?)\":\"(.*?)\"");
    
    // Método para parsear o dataset e transformá-lo num List<Map>
    public List<Map<String, String>> parse(String json){

        // Aplicando o padrão de regex para identificar itens
        Matcher matcher = REGEX_ITEMS.matcher(json);
        if (!matcher.find()) {
            throw new IllegalArgumentException("Nenhum item encontrado");
        }

        // Divindo e agrupando itens
        String[] items = matcher.group(1).split("\\},\\{");

        // ArrayList com todos os dados (atributos e valores de cada item)
        List<Map<String, String>> data = new ArrayList<>();

        // Foreach para dividir atributos e itens uns dos outros
        for (String item : items) {

            // HashMap de id e valor (atributo e valor do atributo)
            Map<String, String> attributesItem = new HashMap<>();

            // Aplicando padrão de regex para identificar atributos
            Matcher matcherAtributosJson = REGEX_JSON_ATTRIBUTES.matcher(item);
            while (matcherAtributosJson.find()) {
                // Atribua os atributos e valores enquanto eles estiverem sendo identificados pelo matcher
                String attribute = matcherAtributosJson.group(1);
                String value = matcherAtributosJson.group(2);

                // Adicionando para o HashMap
                attributesItem.put(attribute, value);
            }
            // Adicionando o item do HashMap para o ArrayList
            data.add(attributesItem);
        }

        return data;
    }
}
