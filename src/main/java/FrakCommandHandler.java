import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;

public class FrakCommandHandler extends ListenerAdapter {

    @Override
    public void onSlashCommand(SlashCommandEvent event)
    {
        if (event.getName().equals("frak")) {
            switch(event.getSubcommandName()){
                case "help":
                    helpCommand(event);
                    break;
                default:
                    event.reply("Ein fehler ist aufgetreten!");
                    break;
            }
        }
    }
    //TODO write functions for each command
    private static void helpCommand(SlashCommandEvent event){
        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle("Verfügbare Befehle:");
        builder.setColor(Color.decode("0xff9601"));
        for(int i = 0; i < Main.CommandList.size(); i++){
            var a = Main.CommandList.get(i);
            StringBuilder items = new StringBuilder();
            items.append(a.getDescription());
            builder.addField(a.getName(), items.toString(), false);
        }
        event.getChannel().sendMessage(builder.build()).queue();
    }
}
