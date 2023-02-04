import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

// Classe comandos extende a classe da API JDA "ListenerAdapter"
public class Commands extends ListenerAdapter {

    // Declaração de variáveis
    // String arrays
    String[] champArray = null;

    // Strings
    String linha; // Armazena uma linha do arquivo campeoes.txt
    String fala; // Armazena apenas a fala da linha do arquivo
    String champ; // Armazena apenas o campeão da linha do arquivo

    // Int
    int randomChamp; // Armazena um valor inteiro aleatório que será utilizado como um index
    int randomFala; // Armazena um valor inteiro aleatório que será utilizado como um index
    int corPadrao = 0x8963fe;

    // Booleans
    boolean falaAtiva = false; // Define se uma operação já está sendo realizada pelo bot

    // List<String>
    List<String> lista = new ArrayList<String>(); // Armazena uma lista de string que contém todas as linhas do arquivo campeoes.txt

    // Método inicializador
    public void init(){
        // Criando e instanciando um buffer que fará uma leitura no arquivo txt
        try (BufferedReader buffer = new BufferedReader(new FileReader("src/res/campeoes.txt"))) {
            // Enquanto a linha que está sendo lida não for nula
            while ((linha = buffer.readLine()) != null) { 
                // Adicione a linha a lista de string
                lista.add(linha);
            }
        // Exceptions
        // Arquivo não encontrado
        } catch (FileNotFoundException e) {
            System.out.println("Não foi possível encontrar o arquivo");
        // Erro de input/output    
        } catch (IOException e) {
            System.out.println("Não foi possível ler uma linha");
            e.printStackTrace();
        }

        // Instanciando o array com a conversão da lista para um array de strings
        champArray = (String[])lista.toArray(new String[lista.size()]);
    }

    // Método para o evento de mensagem recebida no Discord
    public void onMessageReceived(MessageReceivedEvent event){
        init(); // Executa o método para inicialização da leitura do txt
    
        // Criando e instanciando a variável que armazenará a mensagem do usuário, 
        // formatando e removendo espaços para garantir a leitura correta da mensagem
        String[] msg = event.getMessage().getContentRaw().split("\\s+", 0);

        // Independente da texto ser escrito em caixa alta ou não, 
        // verifica se o que foi digitado é igual ao prefixo do bot + o nome do comando específico do comando
        // COMANDO PARA VER INFORMAÇÕES SOBRE O BOT
        if (msg[0].equalsIgnoreCase(Main.prefixo + "info")) {

            // Criando e instanciando novo EmbedBuilder
            EmbedBuilder embedInfo = new EmbedBuilder();
            
            // Definindo atributos do embed
            embedInfo.setTitle("💜 League of Falas");
            
            // Conteúdo do embed
            embedInfo.setDescription("Sou um bot que retorna uma fala de um campeão de League of Legends para você tentar descobrir de quem ela é. Digite **!cmds** para ver todos os comandos.");
            embedInfo.addField("🕹 Como jogar", "Execute o comando **!falas** para iniciar uma rodada.\n\n" + 
            "Uma frase aparecerá e você deverá descobrir o campeão que a disse digitando o nome dele no chat (Não precisa de letras maiúsculas).\n\n" +
            "**OBS**: Campeões de nome composto como \"Lee Sin\" devem ser digitados com um hífen nos espaços. **Ex**: _Lee-Sin_", false);

            // Cor do embed
            embedInfo.setColor(corPadrao);
            embedInfo.setFooter("Developed by Tavinho in Java ☕");

            // Exibindo resposta do bot no canal de texto do Discord onde ele foi instanciado inicialmente pelo comando
            event.getChannel().sendMessageEmbeds(embedInfo.build()).queue();

            // Limpando o embed
            embedInfo.clear();
        }

        // COMANDO PARA VER COMANDOS DO BOT
        if (msg[0].equalsIgnoreCase(Main.prefixo + "cmds")) {

            // Criando e instanciando novo EmbedBuilder
            EmbedBuilder embedInfo = new EmbedBuilder();
            
            // Definindo atributos do embed
            embedInfo.setTitle("💜 League of Falas - Comandos");
            
            // Conteúdo do embed
            embedInfo.setDescription("Prefixo utlizado -> **!**\n\n"
            + "**Comandos**\n" 
            + "    ● info - Informações gerais.\n"
            + "    ● cmds - Exibe todos os comandos.\n"
            + "    ● rep - Exibe o link do repositório do bot no GitHub.\n"
            + "    ● falas - Retorna uma fala para você descobrir quem a disse.\n" 
            + "    ● falas.off - Desativa a atividade de falas.\n");

            // Cor do embed
            embedInfo.setColor(corPadrao);

            // Exibindo resposta do bot no canal de texto do Discord onde ele foi instanciado inicialmente pelo comando
            event.getChannel().sendMessageEmbeds(embedInfo.build()).queue();

            // Limpando o embed
            embedInfo.clear();
        }

        // COMANDO PARA VER COMANDOS DO BOT
        if (msg[0].equalsIgnoreCase(Main.prefixo + "rep")){
            event.getChannel().sendMessage("https://github.com/tavinhossaur/league_of_falas_discord_bot").queue();
        }

        // COMANDO PARA DESATIVAR A ATIVIDADE FALAS
        if (msg[0].equalsIgnoreCase(Main.prefixo + "falas.off") && falaAtiva) {
            // Define como desativado o comando
            falaAtiva = false;
            // E mostra um retorno no chat avisando a alteração
            event.getChannel().sendMessage("**Atividade de falas dos campeões desativada**").queue();
        }
        
        // Caso o usuário digite novamente o comando para criar uma nova fala com uma já ativa
        if (msg[0].equalsIgnoreCase(Main.prefixo + "falas") && falaAtiva) {
            // Exibe uma mensagem para guiar o usuário na desativação da fala anterior. 
            event.getChannel().sendMessage("**Já há uma fala para você descobrir " + event.getAuthor().getAsMention() + "!**\n\n" +
            "Se deseja trocar a fala para tentar outra, utilize o comando: **!falas.off**").queue();
        }
        // Independente da texto ser escrito em caixa alta ou não, 
        // verifica se o que foi digitado é igual ao prefixo do bot + o nome do comando específico do comando
        // COMANDO PARA ATIVAR A ATIVIDADE FALAS
        else if(msg[0].equalsIgnoreCase(Main.prefixo + "falas")) {
            // Se o comando não está ativo
            if (!falaAtiva){
                // Instancia um novo número inteiro aleatório para o index
                randomChamp = new Random().nextInt(champArray.length);
                // Instancia um novo número inteiro aleatório para definir qual das duas frases será selecionada (cara ou coroa)
                randomFala = new Random().nextInt(99);
                
                // Instanciando a fala
                // Se o valor gerado for menor que 50, então escolhe a primeira frase 
                if (randomFala < 50) {
                    fala = champArray[randomChamp].split("_")[1];
                // Caso contrário, escolhe a segunda    
                }else{
                    fala = champArray[randomChamp].split("_")[2];
                }
                
                // Instanciando o campeão
                champ = champArray[randomChamp].split("_")[0];

                // Printa no console o campeão e a fala que foram retornados pelo index aleatório
                System.out.println("Campeão: " + champ);
                System.out.println("Fala: " + fala);
                System.out.println();
            }     

            // Criando e instanciando novo EmbedBuilder
            EmbedBuilder embedfala = new EmbedBuilder();
            
            // Definindo atributos do embed
            embedfala.setTitle("Que campeão fala isso? 🤔");
            embedfala.setDescription("_\"" + fala + "\"_");
            embedfala.setColor(corPadrao);

            // Caso o usuário possua um apelido no servidor, o bot se referirá ao usuário por ele
            if (event.getMember().getNickname() != null) {
                embedfala.setFooter("Instanciado por " + event.getMember().getNickname(), event.getMember().getUser().getAvatarUrl());
            // Caso contrário, é utilizado o nome de usuário do Discord.    
            }else{
                embedfala.setFooter("Instanciado por " + event.getMember().getUser().getName(), event.getMember().getUser().getAvatarUrl());
            }    

            // Exibindo resposta do bot no canal de texto do Discord onde ele foi instanciado inicialmente pelo comando
            event.getChannel().sendMessageEmbeds(embedfala.build()).queue();

            // Limpando o embed
            embedfala.clear();

            // Define como ativo o comando
            falaAtiva = true;
        }
        
        //  Se o usuário digitar corretamente o campeão da fala, esse comando estiver ativo e não seja um bot que enviou a ultima mensagem
        if (msg[0].equalsIgnoreCase(champ) && falaAtiva && !event.getMessage().getAuthor().isBot()) {
            // Exibe a confirmação de resposta correta no canal de texto do Discord onde ele foi instanciado inicialmente pelo comando
            event.getChannel().sendMessage("Parabéns " + event.getAuthor().getAsMention() + "! Você acertou!\nCampeão: " + "**" + champ + "**").queue();
            
            // Define como desativado o comando
            falaAtiva = false;
        }
    }
}
