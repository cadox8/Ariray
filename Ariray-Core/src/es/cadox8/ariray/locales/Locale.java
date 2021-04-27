package es.cadox8.ariray.locales;

import es.cadox8.ariray.Ariray;
import lombok.Getter;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;

public class Locale {

    @Getter private final YamlConfiguration yamlLang;

    public Locale(String lang) throws FileNotFoundException {
        final File langFile = new File(Ariray.getInstance().getDataFolder() + "/locales/" + lang + ".yml");

        if (!langFile.exists()) throw new FileNotFoundException("The file" + Ariray.getInstance().getDataFolder() + "/locales/" + lang + ".yml does not exists!");

        this.yamlLang = YamlConfiguration.loadConfiguration(langFile);
    }
}
