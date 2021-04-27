package es.cadox8.ariray.utils;

import es.cadox8.ariray.Ariray;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class Log {

    @AllArgsConstructor
    public enum Level {
        DEBUG("DEBUG", '3'),
        SUCCESS("SUCCESS", '2'),
        ERROR("ERROR", 'c');

        @Getter private final String prefix;
        @Getter private final char color;
    }

    public static void log(Level type, String msg) {
        if (type == Level.DEBUG && !Ariray.isDebug()) return;
        final String format = "&7[&" + type.getColor() + type.getPrefix() + "&7] &" + type.getColor() + msg;
        Ariray.getInstance().getServer().getConsoleSender().sendMessage(Utils.colorize(format));
    }

    public static void error(String msg) {
        Log.log(Level.ERROR, msg);
    }

    public static void debugLog(String msg) {
        if (Ariray.isDebug()) log(Level.DEBUG, msg);
    }
}
