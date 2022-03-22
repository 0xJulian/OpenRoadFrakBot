import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.json.JSONException;
import org.json.simple.parser.ParseException;

import java.awt.*;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

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
                    try {
                        addCommand(event);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
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


    private static void findCommand(SlashCommandEvent event){
        try {
            SQLCommands cmd = new SQLCommands();
            var opt = event.getOptions();
            if(opt.size() > 1 || opt.size() == 0){
                event.reply("Argument error").queue();
                return;
            }
            switch(opt.get(0).getName()){
                case "name":
                    ResultSet resultSet = cmd.selectData("*", "frak","name = '" + opt.get(0).getAsString()+"'");
                    getResult(event, resultSet);
                    break;
                case "leader":
                    ResultSet resultSet2 = cmd.selectData("*", "frak", "leader_dc_id = '" + opt.get(0).getAsMember().getId()+"'");
                    getResult(event, resultSet2);
                    break;
            }
        } catch (SQLException | ClassNotFoundException | JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
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
    private static void addCommand(SlashCommandEvent event) throws SQLException, JSONException, IOException, ParseException, ClassNotFoundException {
        SQLCommands cmd = new SQLCommands();
        var options = event.getOptions();
        cmd.insertData(options.get(0).getAsString(), options.get(1).getAsMember().getId(), Integer.parseInt(options.get(2).getAsString()));
        ResultSet resultSet = cmd.selectData("*", "frak","name = '" + options.get(0).getAsString()+"'");
        if(resultSet.next() == false){
            event.reply("Die Daten wurden erfolgreich eingefügt!").queue();
            return;
        }
        event.reply("Die Daten wurden erfolgreich eingefügt!").queue();
        getResult(event, resultSet);
    }

    private static void getResult(SlashCommandEvent event, ResultSet resultSet) throws SQLException {
        StringBuilder builder = new StringBuilder();
        EmbedBuilder items = new EmbedBuilder();
        items.setTitle("Ergebnis:");
        items.setColor(Color.decode("0xff9601"));
        if(resultSet.next()){
            builder.append("ID: " + resultSet.getInt("id") + "\n");
            builder.append("Name: " + resultSet.getString("name") + "\n");
            builder.append("Leader: " + event.getGuild()
                    .retrieveMemberById(resultSet.getString("leader_dc_id")).complete()
                    .getAsMention() + "\n");
            builder.append("Abzuege: " + resultSet.getInt("discount") + "%");
        }else{
            event.reply("Es konnten keine Daten gefunden werden!").queue();
            return;
        }
        resultSet.close();
        items.setDescription(builder.toString());
        event.getChannel().sendMessage(items.build()).queue();
    }
    //TODO write functions for each command
}
