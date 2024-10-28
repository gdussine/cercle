package bot.automation.role;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import bot.core.BotManager;
import bot.view.ExceptionView;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.interactions.commands.SlashCommandInteraction;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.selections.StringSelectInteraction;
import net.dv8tion.jda.api.interactions.components.selections.StringSelectMenu;

public class AutoRoleManager extends BotManager{

    private ObjectMapper mapper;

    public AutoRoleManager() {
        super(new AutoRoleListener());
        this.mapper = new ObjectMapper();
    }

    public void defaultTemplate(SlashCommandInteraction interaction) {
        try {
            AutoRoleTemplate template = AutoRoleTemplate.of();
            interaction.reply("```json\n%s\n```".formatted(
                    mapper.writerWithDefaultPrettyPrinter().writeValueAsString(template))).submit();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void template(SlashCommandInteraction interaction, Message message){
        StringSelectMenu menu = (StringSelectMenu) message.getActionRows().get(0).getComponents().get(0);
        MessageEmbed embed = message.getEmbeds().get(0);
        AutoRoleTemplate template = AutoRoleTemplate.of(menu, embed);
        try {
            interaction.reply("```json\n%s\n```".formatted(
                mapper.writerWithDefaultPrettyPrinter().writeValueAsString(template))).submit();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }

    public void transform(SlashCommandInteraction interaction, Message message) {
        try {
            AutoRoleTemplate result = mapper.readValue(message.getContentRaw(), AutoRoleTemplate.class);
            message.getChannel().sendMessageEmbeds(result.toEmbed()).addComponents(ActionRow.of(result.toMenu())).submit();
            interaction.reply("Done !").setEphemeral(true).queue();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public void autorole(StringSelectInteraction interaction) {
        Member member = interaction.getMember();
        List<Role> currentRoles = member.getRoles();
        List<Role> selectedRoles = interaction.getSelectedOptions().stream()
                .map(x -> interaction.getGuild().getRoleById(x.getValue())).toList();
        List<Role> excludedRoles = interaction.getSelectMenu().getOptions().stream()
                .map(x -> interaction.getGuild().getRoleById(x.getValue())).toList();
        List<Role> result = new ArrayList<>();
        result.addAll(currentRoles);
        result.removeAll(excludedRoles);
        result.addAll(selectedRoles);
        var futureRole = interaction.getGuild().modifyMemberRoles(member, result).submit();
        futureRole.handle((v, ex) ->{
            if(ex != null){
                interaction.replyEmbeds(new ExceptionView(ex).toEmbed()).submit();
                return v;
            }
            interaction.reply("Done !").setEphemeral(true).submit();
            return v;
        });
    }

}
