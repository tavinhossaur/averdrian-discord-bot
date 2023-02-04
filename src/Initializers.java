import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.dv8tion.jda.api.EmbedBuilder;

public interface Initializers {

    List<String> lista = new ArrayList<String>();

    // Método inicializador para encontrar as falas
    public static String[] init(String caminho) {
        try (BufferedReader buffer = new BufferedReader(new FileReader(caminho))) {

            String linha;
            while ((linha = buffer.readLine()) != null) { lista.add(linha); }

        // Exceptions
        } catch (FileNotFoundException e) {
            System.out.println("Não foi possível encontrar o arquivo");
        } catch (IOException e) {
            System.out.println("Não foi possível ler uma linha");
            e.printStackTrace();
        }

        // Instanciando o array com a conversão da lista de falas para um array de strings
        String[] array = (String[]) lista.toArray(new String[lista.size()]);
        return array;
    }

    // Método para gerar embeds
    public static EmbedBuilder novoEmbedBuilder(String titulo, String descricao, boolean addCampo, String campoTit, String campoDesc, boolean defaultFooter) {

        EmbedBuilder embed = new EmbedBuilder();

        // Definindo atributos do embed
        embed.setTitle(titulo);
        embed.setDescription(descricao);
        embed.setColor(0x8963fe);

        if (addCampo) embed.addField(campoTit, campoDesc, false);
    
        if (defaultFooter) embed.setFooter("Developed by Tavinho in Java ☕");

        return embed;
    }
}
