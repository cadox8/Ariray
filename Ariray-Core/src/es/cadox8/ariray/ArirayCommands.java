package es.cadox8.ariray;

import com.mysql.cj.util.StringUtils;
import es.cadox8.ariray.api.ArirayServer;
import es.cadox8.ariray.api.ArirayUser;
import es.cadox8.ariray.cmd.ArirayCmd;
import es.cadox8.ariray.utils.Log;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.SimplePluginManager;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArirayCommands implements TabCompleter {

    private static List<ArirayCmd> cmds = new ArrayList<>();
    private static ArirayCommands ucmds;

    private static Ariray plugin = Ariray.getInstance();
    private static String name = "ariray-core:";

    public static void load() {

        ucmds = new ArirayCommands();

        cmds.forEach(ArirayCommands::register);
    }

    public static void register(ArirayCmd... cmdList) {
        Arrays.asList(cmdList).forEach(ArirayCommands::register);
    }

    public static void register(ArirayCmd cmd) {
        final CommandMap commandMap = getCommandMap();
        PluginCommand command = getCmd(cmd.getName());

        if (command.isRegistered()) command.unregister(commandMap);

        command.setAliases(cmd.getAliases());
        command.setTabCompleter(ucmds);

        if (commandMap == null) return;

        commandMap.register(plugin.getDescription().getName(), command);

        //Añadir a la lista por si se registra desde otro plugin:
        if (!cmds.contains(cmd)) cmds.add(cmd);

        if (plugin.getServer().getPluginCommand(name + cmd.getName()) == null) {
            Log.log(Log.Level.ERROR, "Failed to load command /" + cmd.getName());
        }
    }

    private static PluginCommand getCmd(String name) {
        PluginCommand command = null;
        try {
            Constructor<PluginCommand> c = PluginCommand.class.getDeclaredConstructor(String.class, Plugin.class);
            c.setAccessible(true);

            command = c.newInstance(name, plugin);
        } catch (Exception e) {
            Log.debugLog("Can not obtain command");
        }
        return command;
    }

    public static void onCmd(final CommandSender sender, Command cmd, String label, final String[] args) {
        if (label.startsWith(name)) {
            label = label.replaceFirst(name, "");
        }
        for (ArirayCmd cmdr : cmds) {
            if (label.equals(cmdr.getName()) || cmdr.getAliases().contains(label)) {
                if (sender instanceof ConsoleCommandSender) {
                    ConsoleCommandSender cs = (ConsoleCommandSender) sender;
                    cmdr.run(cs, label, args);
                    break;
                }
                if (sender instanceof Player) {
                    ArirayUser p = ArirayServer.getUser((Player) sender);

                    if (cmdr.isHasPermission()) {
                        if (p.hasPermissions(cmdr.getPermission())) {
                            cmdr.run(p, label, args);
                        } else {
                            // ToDo: no perms
                        }
                    } else {
                        cmdr.run(p, label, args);
                    }
                    return;
                }
                cmdr.run(sender, label, args);
            }
        }
    }

    private static CommandMap getCommandMap() {
        CommandMap commandMap = null;
        try {
            if (Bukkit.getPluginManager() instanceof SimplePluginManager) {
                Field f = SimplePluginManager.class.getDeclaredField("commandMap");
                f.setAccessible(true);
                commandMap = (CommandMap) f.get(Bukkit.getPluginManager());
            }
        } catch (SecurityException | IllegalArgumentException | IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
        return commandMap;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        List<String> rtrn = null;
        if (label.startsWith(name)) {
            label = label.replaceFirst(name, "");
        }
        /*
         * Auto Complete normal para cada comando si está declarado
         */
        for (ArirayCmd cmdr : cmds) {
            if (cmdr.getName().equals(label) || cmdr.getAliases().contains(label)) {
                try {
                    if ((sender instanceof Player) && (cmdr.isHasPermission() && (!ArirayServer.getUser((Player) sender).hasPermissions(cmdr.getPermission())))) {
                        return new ArrayList<>();
                    }
                    rtrn = cmdr.onTabComplete(sender, cmd, label, args, args[args.length - 1], args.length - 1);
                } catch (Exception ex) {
                    Log.log(Log.Level.ERROR, "" + label);
                }
                break;
            }
        }
        /*
         * Si el autocomplete es null, que devuelva jugadores
         */
        if (rtrn == null) {
            rtrn = new ArrayList<>();
            for (Player p : Bukkit.getOnlinePlayers()) {
                rtrn.add(p.getName());
            }
        }
        /*
         * Autocomplete para cada argumento
         */
        ArrayList<String> rtrn2 = new ArrayList<>(rtrn);
        rtrn = rtrn2;
        if (!(args[args.length - 1].isEmpty() || args[args.length - 1] == null)) {
            List<String> remv = new ArrayList<>();
            for (String s : rtrn) {
                if (!StringUtils.startsWithIgnoreCase(s, args[args.length - 1])) {
                    remv.add(s);
                }
            }
            rtrn.removeAll(remv);
        }
        return rtrn;
    }
}
