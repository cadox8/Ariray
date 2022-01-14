package es.cadox8.ariray.party;

import es.cadox8.ariray.Ariray;
import es.cadox8.ariray.api.ArirayUser;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserManager {

    private final Ariray plugin;

    public final List<ArirayUser> users;

    public UserManager(Ariray instance) {
        this.plugin = instance;
        this.users = new ArrayList<>();
    }

    public ArirayUser addUser(@NonNull ArirayUser user) {
        if (this.hasUser(user)) return this.getUser(user.getUuid());
        this.users.add(user);
        return user;
    }

    public ArirayUser addUser(@NonNull UUID uuid) {
        if (this.hasUser(uuid)) return this.getUser(uuid);
        final ArirayUser user = new ArirayUser(uuid);
        this.users.add(user);
        return user;
    }

    public boolean removeUser(@NonNull ArirayUser user) {
        return this.removeUSer(user.getUuid());
    }

    public boolean removeUSer(@NonNull UUID uuid) {
        if (!this.hasUser(uuid)) return false;
        this.users.removeIf(u -> u.getUuid().equals(uuid));
        return true;
    }

    public ArirayUser getUser(@NonNull UUID uuid) {
        if (!this.hasUser(uuid)) return null;
        return this.users.stream().filter(u -> u.getUuid().equals(uuid)).findAny().orElse(null);
    }

    public boolean hasUser(@NonNull ArirayUser user) {
        return this.hasUser(user.getUuid());
    }

    public boolean hasUser(@NonNull UUID uuid) {
        return this.users.stream().anyMatch(u -> u.getUuid().equals(uuid));
    }
    // --- ---

    // --- Methods ---

    public void broadcast(String msg) {
        this.users.forEach(u -> u.sendRawMessage("&c BROADCAST &r" + msg));
    }

    public void saveUsers() {
        this.users.forEach(ArirayUser::save);
    }
}
