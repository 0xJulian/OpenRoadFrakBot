import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import javax.security.auth.login.LoginException;

public class Main extends ListenerAdapter {
    public static void main(String[] args) throws LoginException {
        //final JDA jda = JDABuilder.createDefault(args[0]).build();
        //jda.addEventListener(new Main());
        JDABuilder.createLight(args[0], GatewayIntent.GUILD_MESSAGES, GatewayIntent.DIRECT_MESSAGES)
                .addEventListeners(new Main())
                .setActivity(Activity.playing("Fraktionsbot"))
                .build();
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event)
    {
        if (event.isFromType(ChannelType.PRIVATE))
        {
            if(event.getAuthor().isBot()) return;
            String[] args = event.getMessage().getContentRaw().split("\s");
            if(!args[0].equals("!frak")) return;
            if(args.length > 2 || args.length < 2) return;
            MessageChannel c = event.getChannel();
            c.sendMessage("Frak was called with argument: " + args[1]).queue();
        }
    }
}
