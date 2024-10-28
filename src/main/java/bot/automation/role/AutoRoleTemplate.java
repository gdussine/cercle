package bot.automation.role;

import java.util.ArrayList;
import java.util.List;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.interactions.components.selections.SelectOption;
import net.dv8tion.jda.api.interactions.components.selections.StringSelectMenu;
import net.dv8tion.jda.api.interactions.components.selections.StringSelectMenu.Builder;

public class AutoRoleTemplate {

    private String title;
    private String description;
    private int color;

    private String placeholder;
    private int minValue;
    private int maxValue;
    private List<AutoRoleOption> options;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getPlaceholder() {
        return placeholder;
    }

    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
    }

    public List<AutoRoleOption> getOptions() {
        return options;
    }

    public void setOptions(List<AutoRoleOption> options) {
        this.options = options;
    }

    public void addOption(AutoRoleOption option) {
        this.options.add(option);
    }

    public int getMinValue() {
        return minValue;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    public StringSelectMenu toMenu() {
        Builder builder = StringSelectMenu.create("autorole");
        builder.setPlaceholder(placeholder);
        builder.setRequiredRange(minValue, maxValue);
        for (AutoRoleOption option : options) {
            String roleId = option.getValue().replace("<@&", "").replace(">", "");
            builder.addOption(option.getLabel(), roleId, Emoji.fromFormatted(option.getEmoji()));
        }
        return builder.build();
    }

    public MessageEmbed toEmbed() {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle(title);
        eb.setColor(color);
        eb.setDescription(description);
        return eb.build();
    }

    public static AutoRoleTemplate of() {
        AutoRoleTemplate result = new AutoRoleTemplate();
        result.setTitle("Embed title");
        result.setDescription("Embed description");
        result.setColor(0x000001);
        result.setMinValue(1);
        result.setMaxValue(2);
        result.setPlaceholder("Menu placeholder");
        result.setOptions(new ArrayList<>());
        result.addOption(new AutoRoleOption(":warning:", "Attention", "1"));
        result.addOption(new AutoRoleOption(":no_entry:", "Interdit", "2"));
        return result;
    }

    public static AutoRoleTemplate of(StringSelectMenu menu, MessageEmbed embed) {
        AutoRoleTemplate result = new AutoRoleTemplate();
        result.setTitle(embed.getTitle());
        result.setDescription(embed.getDescription());
        result.setColor(embed.getColorRaw());
        result.setMinValue(menu.getMinValues());
        result.setMaxValue(menu.getMaxValues());
        result.setPlaceholder(menu.getPlaceholder());
        result.setOptions(new ArrayList<>());
        for (SelectOption option : menu.getOptions()) {
            result.addOption(new AutoRoleOption(option.getEmoji().getFormatted(), option.getLabel(),
                    "<@&" + option.getValue() + ">"));
        }
        return result;
    }

}
