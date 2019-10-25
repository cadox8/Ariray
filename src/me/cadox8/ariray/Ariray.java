package me.cadox8.ariray;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public class Ariray extends JavaPlugin {

    @Getter private static Ariray instance;

    public void onEnable() {
        instance = this;
    }
}
