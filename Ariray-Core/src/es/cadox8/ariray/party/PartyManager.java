package es.cadox8.ariray.party;

import es.cadox8.ariray.Ariray;
import es.cadox8.ariray.api.ArirayUser;
import es.cadox8.ariray.channels.ChatChannel;
import es.cadox8.ariray.datatype.ShareMode;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.security.Permissions;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

public class PartyManager {

    private static final Ariray plugin = Ariray.getInstance();

    private static final List<Party> parties = new ArrayList<>();

    private PartyManager() {}


    public static boolean existsParty(int party) {
        return parties.stream().anyMatch(p -> p.getId() == party);
    }

    public static boolean isPartyFull(Party targetParty) {
        return targetParty.getMembers().size() >= plugin.getConfig().getInt("party.max_members");
    }

    public static Party getParty(int id) {
        return parties.stream().filter(p -> p.getId() == id).findAny().orElse(null);
    }

    public static boolean joinParty(ArirayUser user, int id) {
        if (user.getPartyId() != -1) removeFromParty(user, getParty(user.getPartyId()));
        user.setPartyId(id);
        getParty(id).addMember(user);
        return true;
    }

    public static boolean removeFromParty(ArirayUser user, Party party) {
        user.setPartyId(-1);
        return party.removeMember(user);
    }

    public static boolean isSameParty(ArirayUser user1, ArirayUser user2) {
        return false;
    }

}
