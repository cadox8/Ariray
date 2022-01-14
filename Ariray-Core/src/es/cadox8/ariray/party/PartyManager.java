package es.cadox8.ariray.party;

import es.cadox8.ariray.Ariray;
import es.cadox8.ariray.api.ArirayParty;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

public class PartyManager {

    private final Ariray plugin;

    private final List<ArirayParty> parties;

    public PartyManager(Ariray instance) {
        this(instance, new ArrayList<>());
    }

    public PartyManager(Ariray instance, @NonNull List<ArirayParty> parties) {
        this.plugin = instance;
        this.parties = parties;
    }

    public boolean newParty(@NonNull ArirayParty arirayParty) {
        if (this.existsParty(arirayParty)) return true;
        return this.parties.add(arirayParty);
    }

    public boolean removeParty(@NonNull ArirayParty arirayParty) {
        if (!this.existsParty(arirayParty)) return false;
        this.parties.removeIf(p -> p.getPartyId() == arirayParty.getPartyId());
        return true;
    }

    public boolean existsParty(@NonNull ArirayParty arirayParty) {
        return this.parties.stream().anyMatch(p -> p.getPartyId() == arirayParty.getPartyId());
    }

    // --- Database ---
    public void loadParties() {
    }

    public void saveParties() {
    }
}
