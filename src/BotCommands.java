import java.util.HashMap;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

// Classe comandos extende a classe da API JDA "ListenerAdapter" e implementa a classe Instantiators
public class BotCommands extends ListenerAdapter implements Instantiators{

    // Strings
    String linha; // Linha do arquivo campeoes.txt
    String[] values; // Dados do campeão e a frase

    // HashMap para controle de instancias do bot
    HashMap<String, String> map = new HashMap<String, String>();

    // Método para o evento de mensagem recebida no Discord
    public void onMessageReceived(MessageReceivedEvent event) {
        // Criando e instanciando a variável que armazenará a mensagem do usuário,
        // formatando e removendo espaços para garantir a leitura correta da mensagem
        String[] msg = event.getMessage().getContentRaw().split("\\s+", 0);

        // CONFIRMAÇÃO DE ACERTO DA RESPOSTA
        // Com base no ID do usuário, verifica se a mensagem contém a resposta correta (Campeão) e verifica se a mensagem não é de um bot
        if (msg[0].equalsIgnoreCase(map.get(event.getAuthor().getId())) && !event.getMessage().getAuthor().isBot()) {
            // Confirmação de acerto
            event.getChannel().sendMessage("Parabéns " + event.getAuthor().getAsMention() + 
            "! Você acertou!\nCampeão: " + "**" + map.get(event.getAuthor().getId()) + "**").queue();

            // Remove o ID do usuário e o campeão do Hash de instâncias
            System.out.println("Encerrado\n> ID: " + event.getAuthor().getId() + "\n> Campeão: " + map.get(event.getAuthor().getId()));
            System.out.println();
            map.remove(event.getAuthor().getId(), map.get(event.getAuthor().getId()));
        }
    }

    @Override
    // SLASH COMMANDS
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        // Armazena o ID do usuário que iniciou um evento
        String userId = event.getUser().getId();

        // COMANDOS DO BOT
        event.getJDA().updateCommands().addCommands(
            Commands.slash("info", "Exibe informações sobre o bot"),
            Commands.slash("cmds", "Exibe todos os comandos"),
            Commands.slash("rep", "Retorna o link do repositório do bot no GitHub"),
            Commands.slash("emoteinfo", "Exibe informações da atividade de emotes"),
            Commands.slash("setoff", "Desliga uma atividade rodando"),
            Commands.slash("ping", "Exibe o tempo de resposta do bot"),
            Commands.slash("falas", "Retorna uma fala de um campeão"),
            Commands.slash("emotes", "Retorna emotes que descrevem um campeão")
        ).queue();

        // SwitchCase para o handle de cada comando
        switch (event.getName()) {
            // COMANDO PARA VER INFORMAÇÕES SOBRE O BOT
            case "info": {
                // Exibe o embed
                event.deferReply().addEmbeds(
                    Instantiators.setEmbed(
                    ":robot: Averdrian BOT", 
                    "Sou um bot que retorna uma fala de um campeão de League of Legends ou emotes que definem ele para você tentar descobrir. Digite **!cmds** para ver todos os comandos.",
                    true,
                    ":joystick: Como jogar", 
                    "Execute o comando **!falas** ou **!emotes** para iniciar uma rodada.\n\n" +
                    "Uma frase ou emotes aparecerão e você deverá descobrir o campeão misterioso digitando o nome dele no chat (Não precisa de letras maiúsculas).\n\n" +
                    "**OBS**: Campeões de nome composto como \"Lee Sin\" devem ser digitados com um hífen nos espaços. **Ex**: _Lee-Sin_.", 
                    true,
                    "",
                    "")
                .build()).queue();
            } break;

            // COMANDO PARA VER TODOS OS COMANDOS DO BOT
            case "cmds": {
                // Exibe o embed
                event.deferReply().addEmbeds(
                    Instantiators.setEmbed(
                    "Averdrian - Interações", 
                    "Prefixo -> **/**\n\n"
                    + "**Comandos**\n"
                    + "> ● info - Informações gerais.\n"
                    + "> ● cmds - Exibe todos os comandos.\n"
                    + "> ● rep - Exibe o link do repositório do bot no GitHub.\n"
                    + "> ● falas - Retorna uma fala para descobrir quem a disse.\n"
                    + "> ● emotes - Retorna emotes para descobrir a que campeão eles se referem.\n"
                    + "> ● emoteinfo - Exibe informações e significados gerais sobre os emotes.\n"
                    + "> ● setoff - Desativa as atividades ativas do bot.\n",
                    false,
                    "",
                    "",
                    true,
                    "",
                    "")
                .build()).queue();
            } break;

            // COMANDO PARA EXIBIR REPOSITÓRIO GITHUB
            case "rep": event.reply("").flatMap(v -> event.getHook().editOriginal("https://github.com/tavinhossaur/averdrian-discord-bot")).queue(); 
            break;
    
            // COMANDO PARA VER DETALHES DA ATIVIDADE DE EMOTES
            case "emoteinfo": {
                // Exibe o embed
                event.deferReply().addEmbeds(
                    Instantiators.setEmbed(
                    "Emotes", 
                    "",
                    true,
                    "Informações Gerais",
                    "> :space_invader: - O campeão é do Vazio\n"
                    + "> :desert: - O campeão é de Shurima\n" 
                    + "> :feather: - O campeão é um Vastaya\n" 
                    + "> :military_helmet: - É um soldado ou guerreiro\n"
                    + "> :levitate: - O campeão flutua ou voa\n"
                    + "> :moyai: - Ele é chad ou é uma estátua\n"
                    + "> :busts_in_silhouete: - O campeão pode se clonar\n"
                    + "> :dagger: - Adaga, espada ou arma afiada de um campeão\n"
                    + "> :pinching_hand: - O campeão é pequeno\n"
                    + "> :handshake: - O campeão possui uma parceria\n"
                    + "> :arrows_counterclockwise: - O campeão se transforma ou algo gira\n"
                    + "> :arrow_left: - Indicação ou continuidade de um processo\n"
                    + "> :fast_forward: - O campeão possui um dash\n"
                    + "> :regional_indicator_r: - Indica a skill do personagem",
                    true,
                    "",
                    "")
                .build()).queue();
            } break;

            // COMANDO PARA DESATIVAR AS ATIVIDADES
            case "setoff": {
                // Se o usuário está no HashMap então remove o registro dele
                if (map.containsKey(userId)) {
                    // Remove o ID do usuário e o campeão do Hash de instâncias
                    System.out.println("Encerrado\nID: " + event.getUser().getId() + "\nCampeão: " + map.get(event.getUser().getId()));
                    System.out.println();
                    map.remove(event.getUser().getId(), map.get(event.getUser().getId()));
        
                    // E mostra um retorno no chat avisando a alteração
                    event.reply("").flatMap(v -> event.getHook().editOriginal("**Atividade do bot desativada para **" + event.getUser().getAsMention())).queue();
                } else event.reply("**Você não está registrado em nenhuma atividade **" + event.getUser().getAsMention()).queue();
            } break;
        
            // COMANDO PARA EXIBIR O TEMPO DE RESPOSTA DO BOT
            case "ping": {
                // Tempo em milissegundos
                long time = System.currentTimeMillis();
                // Faz uma reply vazia e edita ela com o tempo de resposta
                event.reply("").flatMap(v -> event.getHook().editOriginalFormat("Tempo de resposta: %dms", System.currentTimeMillis() - time)).queue();
            } break;

            // COMANDO PARA ATIVAR A ATIVIDADE FALAS
            case "falas": {
                // Se já não tiver uma atividade ativa, instancia novos valores para o campeão e a fala
                if (!map.containsKey(userId)) {
                    values = Instantiators.getRandomChamp(event.getUser().getName());
                    // Armazena o ID do usuário e a resposta da sua instância 
                    // permitindo multiplas instâncias diferentes ao mesmo tempo
                    map.put(userId, values[0]); 

                    // Exibe o embed
                    event.deferReply().addEmbeds(
                        Instantiators.setEmbed(
                        "Que campeão fala isso? :thinking:", 
                        "_\"" + values[1] + "\"_",
                        false,
                        "",
                        "",
                        false,
                        event.getUser().getName(),
                        event.getUser().getAvatarUrl())
                    .build()).queue();
                } else {
                    // Exibe uma mensagem para guiar o usuário na desativação da fala anterior.
                    event.reply("").flatMap(v -> event.getHook().editOriginal("**Já há uma atividade para você jogar " + event.getUser().getAsMention() + "!, verifique se ela está nesse ou em outros canais ou servidores.**" + 
                    "\n\nSe deseja trocar a atividade, ou reiniciá-la, utilize o comando **/setoff**")).queue();
                } } break;
            
            // COMANDO PARA ATIVAR A ATIVIDADE EMOTES
            case "emotes": {
                // Se já não tiver uma fala ativa, instancia novos valores para o campeão e os emotes
                if (!map.containsKey(userId)) {
                    values = Instantiators.getRandomChamp(event.getUser().getName());
                    // Armazena o ID do usuário e a resposta da sua instância 
                    // permitindo multiplas instâncias diferentes ao mesmo tempo
                    map.put(userId, values[0]); 
        
                    // Exibe o embed
                    event.deferReply().addEmbeds(
                        Instantiators.setEmbed(
                        "Que campeão é esse? :thinking:", 
                        "",
                        false,
                        "",
                        "",
                        false,
                        event.getUser().getName(),
                        event.getUser().getAvatarUrl())
                        .build()).queue();

                    event.getChannel().sendMessage(values[2]).queue();
                } else {
                    // Exibe uma mensagem para guiar o usuário na desativação da fala anterior.
                    event.reply("").flatMap(v -> event.getHook().editOriginal("**Já há uma atividade para você jogar " + event.getUser().getAsMention() + "!, verifique se ela está nesse ou em outros canais ou servidores.**" + 
                    "\n\nSe deseja trocar a atividade, ou reiniciá-la, utilize o comando **/setoff**")).queue();
                } } break;  

            default: break;
        }    
    }
}
