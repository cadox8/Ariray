package es.cadox8.ariray.cmd;

import es.cadox8.ariray.Ariray;
import es.cadox8.ariray.api.ArirayUser;
import es.cadox8.ariray.utils.Utils;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public abstract class ArirayCmd {

    protected static transient Ariray plugin = Ariray.getInstance();
    @Getter private final transient String name;
    @Getter private final transient List<String> aliases;
    @Getter @Setter private boolean hasPermission;
    @Getter private transient String permission = "";

    public ArirayCmd(final String name, final boolean hasPermission, final String permission, final List<String> aliases) {
        this.name = name.toLowerCase();
        this.hasPermission = hasPermission;
        this.permission = permission;
        this.aliases = aliases;
    }

    public ArirayCmd(final String name) {
        this(name, false, "", new ArrayList<>());
    }

    public void run(ConsoleCommandSender sender, String label, String[] args) {
        run((CommandSender) sender, label, args);
    }

    public void run(ArirayUser user, String label, String[] args) {
        run(user.getPlayer(), label, args);
    }

    public void run(CommandSender sender, String label, String[] args) {
        sender.sendMessage(Utils.colorize("&cEste comando no está funcional para este sender"));
    }

    public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args, String curs, Integer curn) {
        return new ArrayList<>();
    }

}
