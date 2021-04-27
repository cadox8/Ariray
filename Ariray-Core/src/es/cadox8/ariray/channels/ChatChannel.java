package es.cadox8.ariray.channels;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ChatChannel {
    ADMIN(true),
    STAFF(true),
    GLOBAL(false),
    PARTY(false);

    @Getter private final boolean restricted;

    public String getPermission() {
        return "ariray.channel." + this.name().toLowerCase();
    }
}
