package es.cadox8.ariray.api;

import lombok.NonNull;
import org.bukkit.OfflinePlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ArirayServer {

    private static final List<ArirayUser> users = new ArrayList<>();

    public static ArirayUser addUser(@NonNull UUID uuid) {
        if (hasUser(uuid)) return getUser(uuid);
        final ArirayUser user = new ArirayUser(uuid);
        addUser(user);
        return user;
    }
    public static boolean addUser(@NonNull ArirayUser user) {
        if (hasUser(user)) return false;
        return users.add(user);
    }

    public static boolean hasUser(@NonNull ArirayUser user) {
        return hasUser(user.getUuid());
    }
    public static boolean hasUser(@NonNull UUID uuid) {
        return users.stream().anyMatch(u -> u.getUuid().equals(uuid));
    }

    public static ArirayUser getUser(@NonNull OfflinePlayer offlinePlayer) {
        return getUser(offlinePlayer.getUniqueId());
    }
    public static ArirayUser getUser(@NonNull UUID uuid) {
        return users.stream().filter(u -> u.getUuid().equals(uuid)).findAny().orElse(null);
    }

    public static boolean removeUser(@NonNull ArirayUser user) {
        return removeUser(user.getUuid());
    }
    public static boolean removeUser(@NonNull UUID uuid) {
        return users.removeIf(u -> u.getUuid().equals(uuid));
    }

    public static void saveAll() {
        users.forEach(ArirayUser::save);
    }

    public static void broadcast(String msg) {
        users.forEach(u -> u.sendRawMessage("&c BROADCAST &r" + msg));
    }
}
