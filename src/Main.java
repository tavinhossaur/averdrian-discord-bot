import javax.security.auth.login.LoginException;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class Main {

    // Variável que instanciará a classe JDA da API do Discord
    public static JDA jda;

    // Definindo o prefixo utilizado para instanciar os comandos do bot nos canais de texto
    public static String prefix = "!";

    public static void main(String[] args) throws LoginException{
        // Instanciando a variável como um objeto JDA, buildando o bot e ativando a intent de leitura de conteúdo de mensagens dos membros do servidor do bot.
        jda = JDABuilder.createDefault("token-do-bot").enableIntents(GatewayIntent.MESSAGE_CONTENT).build();

        // Definindo atributos de status do bot 
        jda.getPresence().setStatus(OnlineStatus.INVISIBLE); 
        jda.getPresence().setActivity(Activity.playing("!info")); 

        // Passando a classe Commands como um event listener para o JDA.
        jda.addEventListener(new Commands());
    }
}
