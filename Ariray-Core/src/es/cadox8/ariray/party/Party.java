package es.cadox8.ariray.party;

import es.cadox8.ariray.api.ArirayServer;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Party {

    @Getter private final int id;
    @Getter @Setter private String name;

    @Getter private final UUID owner;

    @Getter private final List<UUID> members;

    public Party(UUID owner) {
        this.id = 0; // Randomly Generated
        this.name = ArirayServer.getUser(owner).getName();
        this.owner = owner;

        this.members = new ArrayList<>();
    }


}


