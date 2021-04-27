package es.cadox8.ariray;

import es.cadox8.ariray.api.ArirayServer;
import es.cadox8.ariray.database.MySQL;
import es.cadox8.ariray.locales.Locale;
import es.cadox8.ariray.locales.Locales;
import es.cadox8.ariray.utils.Log;
import es.cadox8.ariray.utils.Utils;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;

public final class Ariray extends JavaPlugin {

    @Getter private static Ariray instance;

    @Getter private static final String prefix = Utils.colorize("&cAriray &8>> &r");
    @Getter private static boolean debug;

    @Getter private MySQL mysql;

    @Override
    public void onEnable() {
        instance = this;

        // Check config

        //

        debug = getConfig().getBoolean("debug");

        // Load Languages
        Locales.loadLanguage();

        // Load Database
        try {
            Log.debugLog("Loading MySQL");
            this.mysql = new MySQL(getConfig().getString("MySQL.host"), getConfig().getString("MySQL.port"), getConfig().getString("MySQL.user"), getConfig().getString("MySQL.password"), getConfig().getString("MySQL.database"));
            mysql.openConnection();
        } catch (SQLException | ClassNotFoundException exc) {
            Log.log(Log.Level.ERROR,"Error while connecting with MySQL");
            Log.debugLog("Cause: " + exc.toString());
            Log.log(Log.Level.ERROR,"Ariray disabled");
            getServer().getPluginManager().disablePlugin(this);
        }
    }

    @Override
    public void onDisable() {
    }
}
