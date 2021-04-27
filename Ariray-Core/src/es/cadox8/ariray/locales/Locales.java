package es.cadox8.ariray.locales;

import es.cadox8.ariray.Ariray;
import es.cadox8.ariray.utils.Log;
import es.cadox8.ariray.utils.Utils;

import java.io.FileNotFoundException;
import java.util.Objects;

public class Locales {

    private static final Ariray plugin = Ariray.getInstance();

    private static Locale locale;

    public static void loadLanguage() {
        try {
            locale = new Locale(plugin.getConfig().getString("language"));
        } catch (FileNotFoundException e) {
            Log.error(e.getMessage());
        }
    }

    public static String getMessage(String key, String... args) {
        String msg = locale.getYamlLang().getString(key);
        int index = 0;

        for (String arg : args) {
            msg = Objects.requireNonNull(msg).replaceAll("%" + index, arg);
            index++;
        }
        return Utils.colorize(msg);
    }
}
