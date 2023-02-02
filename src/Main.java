import javax.security.auth.login.LoginException;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;

// Classe principal do bot
public class Main {
    // Definindo uma variável que instanciará a classe JDA da API do Discord
    public static JDA jda;

    // Definindo o prefixo utilizado para instanciar os comandos do bot nos canais de texto
    public static String prefixo = "!";

    // Método principal para fazer a execução do bot que, em caso de falha de login por incoerência no token, lança uma exception de erro de login
    public static void main(String[] args) throws LoginException{
        // Instanciando a variável como um objeto JDA, buildando o bot e ativando a intent de leitura de conteúdo de mensagens dos membros do servidor do bot.
        jda = JDABuilder.createDefault("token-do-bot").enableIntents(GatewayIntent.MESSAGE_CONTENT).build();

        // Definindo atributos de status do bot 
        jda.getPresence().setStatus(OnlineStatus.ONLINE); // Bot aparecerá como "ONLINE" no Discord.
        jda.getPresence().setActivity(Activity.playing("!info")); // Bot mostrará uma atividade em sua descrição

        // Passando a classe Commands como um event listener para o JDA.
        jda.addEventListener(new Commands());
    }
}
