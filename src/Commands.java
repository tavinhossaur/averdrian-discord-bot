import java.util.Random;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

// Classe comandos extende a classe da API JDA "ListenerAdapter"
public class Commands extends ListenerAdapter {

    // String array
    String[] champArray = null;

    // Strings
    String linha; // Linha do arquivo campeoes.txt
    String fala; // Fala da linha do arquivo
    String champ; // Campeão da linha do arquivo

    // Int
    int randomChamp; // index campeao aleatório
    int randomFala; // index fala aleatoria

    // Booleans
    boolean falaAtiva = false; // Define se uma operação já está sendo realizada pelo bot

    // Método para o evento de mensagem recebida no Discord
    public void onMessageReceived(MessageReceivedEvent event) {
        // Criando e instanciando a variável que armazenará a mensagem do usuário,
        // formatando e removendo espaços para garantir a leitura correta da mensagem
        String[] msg = event.getMessage().getContentRaw().split("\\s+", 0);

        // COMANDO PARA VER INFORMAÇÕES SOBRE O BOT
        if (msg[0].equalsIgnoreCase(Main.prefixo + "info")) {
            // Exibe o embed
            event.getChannel().sendMessageEmbeds(
                Initializers.novoEmbedBuilder(
                "💜 League of Falas", 
                "Sou um bot que retorna uma fala de um campeão de League of Legends para você tentar descobrir de quem ela é. Digite **!cmds** para ver todos os comandos.",
                true,
                "🕹 Como jogar", 
                "Execute o comando **!falas** para iniciar uma rodada.\n\n" +
                "Uma frase aparecerá e você deverá descobrir o campeão que a disse digitando o nome dele no chat (Não precisa de letras maiúsculas).\n\n" +
                "**OBS**: Campeões de nome composto como \"Lee Sin\" devem ser digitados com um hífen nos espaços. **Ex**: _Lee-Sin_",
                true)
                .build()).queue();
        }

        // COMANDO PARA VER COMANDOS DO BOT
        if (msg[0].equalsIgnoreCase(Main.prefixo + "cmds")) {
            // Exibe o embed
            event.getChannel().sendMessageEmbeds(
                Initializers.novoEmbedBuilder(
                "💜 League of Falas - Comandos", 
                "Prefixo utlizado -> **!**\n\n"
                + "**Comandos**\n"
                + "    ● info - Informações gerais.\n"
                + "    ● cmds - Exibe todos os comandos.\n"
                + "    ● rep - Exibe o link do repositório do bot no GitHub.\n"
                + "    ● falas - Retorna uma fala para você descobrir quem a disse.\n"
                + "    ● falas.off - Desativa a atividade de falas.\n",
                false,
                "",
                "",
                false)
                .build()).queue();
        }

        // COMANDO PARA EXIBIR REPOSITÓRIO GITHUB
        if (msg[0].equalsIgnoreCase(Main.prefixo + "rep")) {
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
            event.getChannel().sendMessage("**Já há uma fala para você descobrir " + 
            event.getAuthor().getAsMention() + "!**\n\n" + "Se deseja trocar a fala para tentar outra, utilize o comando: **!falas.off**").queue();
        }

        // COMANDO PARA ATIVAR A ATIVIDADE FALAS
        else if (msg[0].equalsIgnoreCase(Main.prefixo + "falas")) {
            champArray = Initializers.init("src/res/falas.txt"); 

            if (!falaAtiva) {
                randomChamp = new Random().nextInt(champArray.length);
                // Instancia um novo número inteiro aleatório para definir qual das duas frases será selecionada (cara ou coroa)
                randomFala = new Random().nextInt(99);

                // Instanciando a fala
                // Se o valor gerado for menor que 50, então escolhe a primeira frase, caso contrário, a segunda
                if (randomFala < 50) {
                    fala = champArray[randomChamp].split("_")[1];
                } else {
                    fala = champArray[randomChamp].split("_")[2];
                }

                // Instanciando o campeão
                champ = champArray[randomChamp].split("_")[0];

                // Printa no console o resultado do cara ou coroa, o campeão e a fala que foram retornados pelo index aleatório
                System.out.println("Cara ou coroa: " + randomFala);
                System.out.println("Campeão: " + champ);
                System.out.println("Fala: " + fala);
                System.out.println();
            }

            // Exibe o embed
            event.getChannel().sendMessageEmbeds(
                Initializers.novoEmbedBuilder(
                "Que campeão fala isso? 🤔", 
                "_\"" + fala + "\"_",
                false,
                "",
                "",
                false)
                .build()).queue();

            // Define como ativo o comando
            falaAtiva = true;
        }

        // Se o usuário digitar corretamente o campeão da fala, esse comando estiver
        // ativo e não seja um bot que enviou a ultima mensagem
        if (msg[0].equalsIgnoreCase(champ) && falaAtiva && !event.getMessage().getAuthor().isBot()) {

            // Confirmação de acerto
            event.getChannel().sendMessage("Parabéns " + event.getAuthor().getAsMention() + 
            "! Você acertou!\nCampeão: " + "**" + champ + "**").queue();

            // Define como desativado o comando
            falaAtiva = false;
        }
    }
}
