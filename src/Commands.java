import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

// Classe comandos extende a classe da API JDA "ListenerAdapter"
public class Commands extends ListenerAdapter {

    // Strings
    String linha; // Linha do arquivo campeoes.txt
    String[] values; // Dados do campeão e a frase

    // Booleans
    boolean isQuoteActive = false; // Define se uma operação de falas já está sendo realizada pelo bot

    // Método para o evento de mensagem recebida no Discord
    public void onMessageReceived(MessageReceivedEvent event) {
        // Criando e instanciando a variável que armazenará a mensagem do usuário,
        // formatando e removendo espaços para garantir a leitura correta da mensagem
        String[] msg = event.getMessage().getContentRaw().split("\\s+", 0);

        // COMANDO PARA VER INFORMAÇÕES SOBRE O BOT
        if (msg[0].equalsIgnoreCase(Main.prefix + "info")) {
            // Exibe o embed
            event.getChannel().sendMessageEmbeds(
                Instantiators.setEmbed(
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
        if (msg[0].equalsIgnoreCase(Main.prefix + "cmds")) {
            // Exibe o embed
            event.getChannel().sendMessageEmbeds(
                Instantiators.setEmbed(
                "💜 League of Falas - Comandos", 
                "prefixo utlizado -> **!**\n\n"
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
        if (msg[0].equalsIgnoreCase(Main.prefix + "rep")) {
            event.getChannel().sendMessage("https://github.com/tavinhossaur/league_of_falas_discord_bot").queue();
        }

        // COMANDO PARA DESATIVAR A ATIVIDADE FALAS
        if (msg[0].equalsIgnoreCase(Main.prefix + "falas.off") && isQuoteActive) {
            // Define como desativado o comando
            isQuoteActive = false;
            // E mostra um retorno no chat avisando a alteração
            event.getChannel().sendMessage("**Atividade de falas dos campeões desativada**").queue();
        }

        // Caso o usuário digite novamente o comando para criar uma nova fala com uma já ativa
        if (msg[0].equalsIgnoreCase(Main.prefix + "falas") && isQuoteActive) {
            // Exibe uma mensagem para guiar o usuário na desativação da fala anterior.
            event.getChannel().sendMessage("**Já há uma fala para você descobrir " + 
            event.getAuthor().getAsMention() + "!**\n\n" + "Se deseja trocar a fala para tentar outra, utilize o comando: **!falas.off**").queue();
        }
        // COMANDO PARA ATIVAR A ATIVIDADE FALAS
        else if (msg[0].equalsIgnoreCase(Main.prefix + "falas")) {
            // Se já não tiver uma fala ativa, instancia novos valores para o campeão e a fala
            if (!isQuoteActive) values = Instantiators.getRandomChamp();

            // Exibe o embed
            event.getChannel().sendMessageEmbeds(
                Instantiators.setEmbed(
                "Que campeão fala isso? 🤔", 
                "_\"" + values[1] + "\"_",
                false,
                "",
                "",
                false)
                .build()).queue();

            // Define como ativo o comando
            isQuoteActive = true;
        }

        // Se o usuário digitar corretamente o campeão da fala, esse comando estiver
        // ativo e não seja um bot que enviou a ultima mensagem
        if (msg[0].equalsIgnoreCase(values[0]) && isQuoteActive && !event.getMessage().getAuthor().isBot()) {

            // Confirmação de acerto
            event.getChannel().sendMessage("Parabéns " + event.getAuthor().getAsMention() + 
            "! Você acertou!\nCampeão: " + "**" + values[0] + "**").queue();

            // Define como desativado o comando
            isQuoteActive = false;
        }
    }
}
