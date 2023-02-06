import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.dv8tion.jda.api.EmbedBuilder;

public interface Instantiators {

    List<String> list = new ArrayList<String>();

    // Método inicializador para encontrar as falas
    public static String[] getQuotesArray(String path) {
        try (BufferedReader buffer = new BufferedReader(new FileReader(path))) {

            String line;
            while ((line = buffer.readLine()) != null) { list.add(line); }

        // Exceptions
        } catch (FileNotFoundException e) {
            System.out.println("Não foi possível encontrar o arquivo");
        } catch (IOException e) {
            System.out.println("Não foi possível ler uma linha");
            e.printStackTrace();
        }

        // Instanciando o array com a conversão da lista de falas para um array de strings
        String[] array = (String[]) list.toArray(new String[list.size()]);
        return array;
    }

    // Método para gerar embeds
    public static EmbedBuilder setEmbed(String title, String desc, boolean addField, String fieldTitle, String fieldDesc, boolean defaultFooter) {

        EmbedBuilder embed = new EmbedBuilder();

        // Definindo atributos do embed
        embed.setTitle(title);
        embed.setDescription(desc);
        embed.setColor(0x8963fe);

        if (addField) embed.addField(fieldTitle, fieldDesc, false);
    
        if (defaultFooter) embed.setFooter("Developed by Tavinho in Java ☕");

        return embed;
    }

    // Retorna um campeão e uma frase aleatória
    public static String[] getRandomChamp() {
        // Int
        int randomChamp; // index campeao aleatório
        int randomQuote; // index fala aleatoria

        // String
        String quote; // Fala da linha do arquivo
        String champ; // Campeão da linha do arquivo

        // String array
        String[] champArray = Instantiators.getQuotesArray("src/res/data.txt");

        randomChamp = new Random().nextInt(champArray.length);
        // Instancia um novo número inteiro aleatório para definir qual das duas frases será selecionada (cara ou coroa)
        randomQuote = new Random().nextInt(99);

        // Instanciando a fala
        // Se o valor gerado for menor que 50, então escolhe a primeira frase, caso contrário, a segunda
        if (randomQuote < 50) quote = champArray[randomChamp].split("_")[1];
        else quote = champArray[randomChamp].split("_")[2];

        // Instanciando o campeão
        champ = champArray[randomChamp].split("_")[0];

        // Printa no console o resultado do cara ou coroa, o campeão e a fala que foram retornados pelo index aleatório
        System.out.println("Cara ou coroa: " + randomQuote);
        System.out.println("Campeão: " + champ);
        System.out.println("Fala: " + quote);
        System.out.println();
       
        // Retorna os dois valores
        return new String[] { champ, quote };
    }
}