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
                case "add":
                    addCommand(event);
                    break;
                case "find":
                    findCommand(event);
                    break;
                default:
                    event.reply("Ein fehler ist aufgetreten!").queue();
                    break;
            }
        }
    }

    private static void addCommand(SlashCommandEvent event){
        event.reply(event.getOptions().get(0).getAsString()).queue();
        event.getChannel().sendMessage(event.getOptions().get(1).getAsMember().getId()).queue();
        //TODO write data into db
    }

    private static void findCommand(SlashCommandEvent event){
        //TODO select data from db
    }

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
    //TODO write functions for each command
}
