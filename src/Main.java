import javax.security.auth.login.LoginException;

import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class Main {

    // Variável que instanciará a classe JDA da API do Discord
    public static JDA jda;

    // Variável para configuração do .env
    public static Dotenv config;

    public static void main(String[] args) throws LoginException, InterruptedException{

        // Retornando valor do TOKEN no .env
        config = Dotenv.configure().load();
        String TOKEN = config.get("TOKEN");

        // Instanciando a variável como um objeto JDA, buildando o bot e ativando a intent de leitura de conteúdo de mensagens dos membros do servidor do bot.
        jda = JDABuilder.createDefault(TOKEN).enableIntents(GatewayIntent.MESSAGE_CONTENT).build();
        jda.awaitReady();

        // Definindo atributos de status do bot 
        jda.getPresence().setStatus(OnlineStatus.ONLINE); 
        jda.getPresence().setActivity(Activity.playing("/info")); 

        // Passando a classe Commands como um event listener para o JDA.
        jda.addEventListener(new BotCommands());
    }
}
