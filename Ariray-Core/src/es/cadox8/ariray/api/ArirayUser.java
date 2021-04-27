package es.cadox8.ariray.api;

import es.cadox8.ariray.Ariray;
import es.cadox8.ariray.channels.ChatChannel;
import es.cadox8.ariray.locales.Locales;
import es.cadox8.ariray.utils.Utils;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

public class ArirayUser {

    private final Ariray plugin = Ariray.getInstance();

    @Getter private final UUID uuid;

    // Data
    @Getter @Setter private ChatChannel chatChannel = ChatChannel.GLOBAL;
    @Getter @Setter private int partyId = 0;

    //

    public ArirayUser(@NonNull OfflinePlayer player) {
        this(player.getUniqueId());
    }
    public ArirayUser(@NonNull UUID uuid) {
        this.uuid = uuid;
    }


    public void save() {
        // ToDo: MySQL Save
        ArirayServer.removeUser(this.getUuid());
        // ToDo: MySQL Load
        ArirayServer.addUser(this);
    }

    // Getters
    public OfflinePlayer getOfflinePlayer() {
        return this.plugin.getServer().getOfflinePlayer(this.uuid);
    }

    public Player getPlayer() {
        return this.getOfflinePlayer().getPlayer();
    }

    public boolean isOnline() {
        return this.getOfflinePlayer().isOnline();
    }

    public String getName() {
        return this.getOfflinePlayer().getName();
    }

    // Setters

    // Methods
    public boolean hasPermissions(String permission) {
        return this.getPlayer().hasPermission(permission);
    }

    public void sendMessage(String key, String... args) {
        this.getPlayer().sendMessage(Ariray.getPrefix() + Locales.getMessage(key, args));
    }
    public void sendRawMessage(String msg) {
        this.getPlayer().sendMessage(Ariray.getPrefix() + Utils.colorize(msg));
    }

    public void updateChatChannel(ChatChannel newChannel) {
        if (newChannel == this.getChatChannel()) return;
        if (newChannel.isRestricted()) {
            if (this.hasPermissions(newChannel.getPermission())) {
                this.setChatChannel(newChannel);
            } else {
                this.sendMessage("no_permissions");
            }
        } else {
            this.setChatChannel(newChannel);
        }
    }
}
