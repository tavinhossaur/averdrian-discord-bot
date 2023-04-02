import java.util.List;
import java.util.Random;

import model.Champion;
import net.dv8tion.jda.api.EmbedBuilder;
import services.BotHttpClient;
import utils.JsonExtractor;

public interface BotInstantiators {

    // Método para gerar embeds
    public static EmbedBuilder setEmbed(boolean addThumb, String thumbUrl, String title, String desc, boolean addField, String fieldTitle, String fieldDesc, boolean defaultFooter, String userName, String userIcon) {

        EmbedBuilder embed = new EmbedBuilder();

        // Definindo atributos do embed
        embed.setTitle(title);
        embed.setDescription(desc);
        embed.setColor(0x18db1f);

        if (addThumb) embed.setThumbnail(thumbUrl);

        if (addField) embed.addField(fieldTitle, fieldDesc, false);
    
        if (defaultFooter) embed.setFooter("Developed by Tavinho in Java ☕");
        else embed.setFooter("Instanciado por " + userName, userIcon);

        // Retorna o embed
        return embed;
    }

    // Retorna um campeão e uma frase ou emote aleatório
    public static String[] getRandomChamp(String userName) {

        // Retornando valor do API_PATH no .env
        String API_PATH = Main.config.get("API_PATH");

        int randomQuote; // armazena resultado cara ou coroa

        // Atributos do campeão
        String name; 
        String quote; 
        String emotes; 

        // Instanciando um http client
        var http = new BotHttpClient();

        // Retornando a string do json dataset dos campeões
        String json = http.returnData(API_PATH);

        // Instanciando um json extractor
        JsonExtractor extractor = new JsonExtractor();

        // Extraindo o conteúdo do json para uma lista de campeões
        List<Champion> champList = extractor.extractContent(json);
        // Selecionando um campeão aleatório da lista
        Champion champion = champList.get(new Random().nextInt(champList.size()));

        // Retornando o nome e os emotes do campeão
        name = champion.getName();
        emotes = champion.getEmotes();
            
        // Selecionando uma das frases
        randomQuote = new Random().nextInt(99);
        if (randomQuote < 50) quote = champion.getQuote1();
        else quote = champion.getQuote2();

        // Printa no console o resultado do cara ou coroa, o campeão, a fala e o emote que foram retornados pelo index aleatório
        System.out.println("NOVA INSTÂNCIA");
        System.out.println("Usuário: " + userName);
        System.out.println("Cara ou coroa: " + randomQuote);
        System.out.println("Campeão: " + name);
        System.out.println("Fala: " + quote);
        System.out.println("Emote: " + emotes);
        System.out.println();
       
        // Retorna os valores
        return new String[] { name, quote, emotes };
    }
}