import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

// Classe comandos extende a classe da API JDA "ListenerAdapter"
public class Commands extends ListenerAdapter {

    // Strings
    String linha; // Linha do arquivo campeoes.txt
    String[] values; // Dados do campeão e a frase

    // Booleans
    boolean isBotActive = false; // Define se uma operação de falas já está sendo realizada pelo bot

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
                ":robot: Averdrian BOT", 
                "Sou um bot que retorna uma fala de um campeão de League of Legends ou emotes que definem ele para você tentar descobrir. Digite **!cmds** para ver todos os comandos.",
                true,
                ":joystick: Como jogar", 
                "Execute o comando **!falas** ou **!emotes** para iniciar uma rodada.\n\n" +
                "Uma frase ou emotes aparecerão e você deverá descobrir o campeão misterioso digitando o nome dele no chat (Não precisa de letras maiúsculas).\n\n" +
                "**OBS**: Campeões de nome composto como \"Lee Sin\" devem ser digitados com um hífen nos espaços. **Ex**: _Lee-Sin_.", 
                true)
                .build()).queue();
        }

        // COMANDO PARA VER COMANDOS DO BOT
        if (msg[0].equalsIgnoreCase(Main.prefix + "cmds")) {
            // Exibe o embed
            event.getChannel().sendMessageEmbeds(
                Instantiators.setEmbed(
                ":robot: Averdrian - Comandos", 
                "Prefixo -> **!**\n\n"
                + "**Comandos**\n"
                + "    ● info - Informações gerais.\n"
                + "    ● cmds - Exibe todos os comandos.\n"
                + "    ● rep - Exibe o link do repositório do bot no GitHub.\n"
                + "    ● falas - Retorna uma fala para você descobrir quem a disse.\n"
                + "    ● emotes - Retorna emotes para você descobrir a qual camepeão eles se referem.\n"
                + "    ● emotes.info - Exibe informações e significados gerais sobre os emotes.\n"
                + "    ● bot.off - Desativa as atividades ativas do bot.\n",
                false,
                "",
                "",
                false)
                .build()).queue();
        }

        // COMANDO PARA VER DETALHES DO BOT
        if (msg[0].equalsIgnoreCase(Main.prefix + "emotes.info")) {
            // Exibe o embed
            event.getChannel().sendMessageEmbeds(
                Instantiators.setEmbed(
                ":robot: Averdrian BOT", 
                "",
                true,
                "Emotes - Informações Gerais",
                ":space_invader: -> O campeão é do Vazio\n"
                + ":desert: -> O campeão é de Shurima\n" 
                + ":feather: -> O campeão é um Vastaya\n" 
                + ":military_helmet: -> É um soldado ou guerreiro\n"
                + ":levitate: -> O campeão flutua ou voa\n"
                + ":moyai: -> Ele é chad ou é uma estátua\n"
                + ":dagger: -> Adaga, espada ou arma afiada de um campeão\n"
                + ":pinching_hand: -> O campeão é pequeno\n"
                + ":handshake: -> O campeão possui uma parceria\n"
                + ":arrows_counterclockwise: -> O campeão se transforma ou algo gira\n"
                + ":arrow_left: -> Indicação ou continuidade de um processo\n"
                + ":fast_forward: -> O campeão possui um dash\n"
                + ":regional_indicator_r: -> Indica a skill do personagem",
                false)
                .build()).queue();
        }

        // COMANDO PARA EXIBIR REPOSITÓRIO GITHUB
        if (msg[0].equalsIgnoreCase(Main.prefix + "rep")) {
            event.getChannel().sendMessage("https://github.com/tavinhossaur/averdrian-discord-bot").queue();
        }

        // COMANDO PARA DESATIVAR AS ATIVIDADES
        if (msg[0].equalsIgnoreCase(Main.prefix + "bot.off") && isBotActive) {
            // Define como desativado o comando
            isBotActive = false;
            // E mostra um retorno no chat avisando a alteração
            event.getChannel().sendMessage("**Atividades do bot desativadas**").queue();
        }

        // Caso o usuário digite novamente o comando para criar uma nova fala com o bot já ativo
        if (msg[0].equalsIgnoreCase(Main.prefix + "falas") && isBotActive || msg[0].equalsIgnoreCase(Main.prefix + "emotes") && isBotActive) {
            // Exibe uma mensagem para guiar o usuário na desativação da fala anterior.
            event.getChannel().sendMessage("**Já há uma atividade para você jogar " + 
            event.getAuthor().getAsMention() + "!**\n\n" + "Se deseja trocar a atividade, ou reiniciá-la, utilize o comando: **!bot.off**").queue();
        }
        // COMANDO PARA ATIVAR A ATIVIDADE FALAS
        else if (msg[0].equalsIgnoreCase(Main.prefix + "falas")) {
            // Se já não tiver uma atividade ativa, instancia novos valores para o campeão e a fala
            if (!isBotActive) values = Instantiators.getRandomChamp();

            // Exibe o embed
            event.getChannel().sendMessageEmbeds(
                Instantiators.setEmbed(
                "Que campeão fala isso? :thinking:", 
                "_\"" + values[1] + "\"_",
                false,
                "",
                "",
                false)
                .build()).queue();

            // Define como ativo o comando
            isBotActive = true;
        }
        // COMANDO PARA ATIVAR A ATIVIDADE EMOTES
        else if (msg[0].equalsIgnoreCase(Main.prefix + "emotes")) {
            // Se já não tiver uma fala ativa, instancia novos valores para o campeão e os emotes
            if (!isBotActive) values = Instantiators.getRandomChamp();
        
            //  Exibe o embed
            event.getChannel().sendMessageEmbeds(
                Instantiators.setEmbed(
                "Que campeão é esse? :thinking:", 
                "",
                false,
                "",
                "",
                false)
                .build()).queue();

            event.getChannel().sendMessage(values[2]).queue();
        
            // Define como ativo o comando
            isBotActive = true;    
        }

        // Se o usuário digitar corretamente o campeão da fala, esse comando estiver
        // ativo e não seja um bot que enviou a ultima mensagem
        if (msg[0].equalsIgnoreCase(values[0]) && isBotActive && !event.getMessage().getAuthor().isBot()) {

            // Confirmação de acerto
            event.getChannel().sendMessage("Parabéns " + event.getAuthor().getAsMention() + 
            "! Você acertou!\nCampeão: " + "**" + values[0] + "**").queue();

            // Define como desativado o comando
            isBotActive = false;
        }
    }
}
