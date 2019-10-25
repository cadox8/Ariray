package me.cadox8.ariray.classes;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import me.cadox8.ariray.Ariray;

@ToString
public abstract class AClass {

    protected final Ariray plugin = Ariray.getInstance();

    @Getter private final int id;
    @Getter private final String name;

    @Setter private ClassStats stats;

    public AClass(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public ClassStats getStats() {
        return stats == null ? stats = new ClassStats() : stats;
    }
}
