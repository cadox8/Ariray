package me.cadox8.ariray.user;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import me.cadox8.ariray.Ariray;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

@ToString
public class AUser {

    private final Ariray plugin = Ariray.getInstance();

    @Getter private final UUID uuid;
    @Getter @Setter private UserData userData;

    public AUser(OfflinePlayer p) {
        this(p.getUniqueId());
    }
    public AUser(UUID uuid) {
        this.uuid = uuid;
    }

    public void save() {

    }


    public OfflinePlayer getOfflinePlayer() {
        return plugin.getServer().getOfflinePlayer(uuid);
    }
    public Player getPlayer() {
        return plugin.getServer().getPlayer(uuid);
    }
    public boolean isOnline() {
        return getOfflinePlayer().isOnline();
    }
    public String getName() {
        return getOfflinePlayer().getName();
    }

    @Data
    public class UserData {

        private int level = 0;
        private float exp = 0;

        private double money = 0;


    }
}
