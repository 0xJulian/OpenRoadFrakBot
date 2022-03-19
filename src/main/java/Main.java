import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;

import javax.security.auth.login.LoginException;
import java.util.ArrayList;

public class Main extends ListenerAdapter {
    public static final String GuildID = "724281984893059123";
    public static ArrayList<SubcommandData> CommandList = new ArrayList<>();
    public static void main(String[] args) throws LoginException {
        JDABuilder.createLight(args[0], GatewayIntent.GUILD_MESSAGES, GatewayIntent.DIRECT_MESSAGES)
                .addEventListeners(new FrakCommandHandler())
                .addEventListeners(new Main())
                .setActivity(Activity.playing("Fraktionsbot"))
                .build();
    }
    @Override
    public void onReady(ReadyEvent event){
        CommandListUpdateAction commands = event.getJDA().getGuildById(GuildID).updateCommands();

        commands.addCommands(
                new CommandData("frak", "Fraktionsmanagement")
                        .addSubcommands(createSubcommandData("help", "Zeigt alle Befehle an"))
                        .addSubcommands(createSubcommandData("test", "description"))
                );
        commands.queue();
    }

    private static SubcommandData createSubcommandData(String name, String description){
        SubcommandData data = new SubcommandData(name, description);
        CommandList.add(data);
        return data;
    }
}
