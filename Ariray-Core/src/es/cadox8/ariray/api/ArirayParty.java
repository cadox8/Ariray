package es.cadox8.ariray.api;

import es.cadox8.ariray.Ariray;
import es.cadox8.ariray.utils.Log;
import lombok.*;
import org.bukkit.ChatColor;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;

public class ArirayParty {

    private final Ariray plugin = Ariray.getInstance();

    private final int partyId;
    @Getter private String partyName;

    @Setter @Getter private ChatColor color;

    private final HashMap<ArirayUser, ChatRole> members;

    @Getter private final PartySettings partySettings;

    public ArirayParty(int partyId, String partyName) {
        this(partyId, partyName, new HashMap<>());
    }
    public ArirayParty(int partyId, String partyName, HashMap<ArirayUser, ChatRole> members) {
        this.partyId = partyId;
        this.partyName = partyName;
        this.members = members;
        this.partySettings = new PartySettings();
    }

    // --- Party Management ---

    /**
     * Changes the name of the Party
     *
     * @param newName The new name
     */
    public void changeName(String newName) {
        // ToDo: Validator
        this.partyName = newName;
    }

    /**
     *
     * @param mod
     * @param uuid
     * @return
     */
    public boolean inviteMember(UUID mod, UUID uuid) {
        return this.inviteMember(ArirayServer.getUser(mod), ArirayServer.getUser(uuid));
    }

    /**
     *
     * @param mod
     * @param user
     * @return
     */
    public boolean inviteMember(ArirayUser mod, ArirayUser user) {
        if (this.getMemberRole(mod) == ChatRole.OWNER || (this.getPartySettings().isModCanInvite() && this.getMemberRole(mod) == ChatRole.MOD)) {
            if (this.hasMember(user)) return false;
            this.members.putIfAbsent(user, ChatRole.MEMBER);
            return true;
        }
        return false;
    }

    /**
     *
     * @param mod
     * @param user
     * @return
     */
    public boolean kickMember(ArirayUser mod, ArirayUser user) {
        return this.kickMember(mod.getUuid(), user.getUuid());
    }

    /**
     *
     * @param mod
     * @param uuid
     * @return
     */
    public boolean kickMember(UUID mod, UUID uuid) {
        if (this.getMemberRole(mod) == ChatRole.OWNER || (this.getPartySettings().isModCanKick() && this.getMemberRole(mod) == ChatRole.MOD)) {
            if (!this.hasMember(uuid)) return false;

            final ArirayUser user = this.getMember(uuid);
            final ChatRole modRole = this.getMemberRole(mod);

            if (modRole == ChatRole.OWNER) {
                this.members.remove(user);
                return true;
            }

            if (this.getMemberRole(user) == modRole || this.getMemberRole(user) == ChatRole.OWNER) return false;

            this.members.remove(user);
            return true;
        }
        return false;
    }

    /**
     *
     * @param user
     * @return
     */
    public boolean hasMember(ArirayUser user) {
        return this.hasMember(user.getUuid());
    }

    /**
     *
     * @param uuid
     * @return
     */
    public boolean hasMember(UUID uuid) {
        return this.members.keySet().stream().anyMatch(u -> u.getUuid().equals(uuid));
    }

    /**
     *
     * @param uuid
     * @return
     */
    private ArirayUser getMember(UUID uuid) {
        return this.members.keySet().stream().filter(u -> u.getUuid().equals(uuid)).findAny().get();
    }

    /**
     *
     * @param user
     * @return
     */
    public ChatRole getMemberRole(ArirayUser user) {
        return this.getMemberRole(user.getUuid());
    }

    /**
     *
     * @param uuid
     * @return
     */
    public ChatRole getMemberRole(UUID uuid) {
        if (!this.hasMember(uuid)) return ChatRole.NONE;
        return this.members.get(this.getMember(uuid));
    }

    /**
     *
     * @param mod
     * @param target
     * @param newRole
     * @return
     */
    public boolean updateRole(ArirayUser mod, ArirayUser target, ChatRole newRole) {
        return this.updateRole(mod, target.getUuid(), newRole);
    }

    /**
     *
     * @param mod
     * @param target
     * @param newRole
     * @return
     */
    public boolean updateRole(ArirayUser mod, UUID target, ChatRole newRole) {
        if (this.getMemberRole(mod) != ChatRole.OWNER) return false;
        final ChatRole oldRole = this.getMemberRole(target);

        if (oldRole == newRole) return false;
        if (newRole == ChatRole.OWNER) this.members.replace(this.getMember(mod.getUuid()), ChatRole.MOD);

        this.members.replace(this.getMember(target), newRole);
        return true;
    }
    // --- ---

    // --- Party Methods ---

    /**
     *
     * @param sender
     * @param message
     */
    public void sendMessageToMembers(ArirayUser sender, String message) {
        this.members.keySet().stream().filter(u -> !u.getUuid().equals(sender.getUuid())).forEach(u -> {
            u.sendPartyMessage(this.partyName, this.color, sender.getName(), message);
        });
    }

    public void updateSettings(PartySettings settings) {
        this.getPartySettings().setPublicParty(settings.isPublicParty());
        this.getPartySettings().setShowOnList(settings.isShowOnList());

        this.getPartySettings().setModCanInvite(settings.isModCanInvite());
        this.getPartySettings().setModCanKick(settings.isModCanKick());
        this.saveSettings();
    }
    // --- ---

    // --- Party Database Methods ---

    /**
     *
     */
    public void saveSettings() {
        this.plugin.getServer().getScheduler().runTaskAsynchronously(this.plugin, () -> {
            try {
                final PreparedStatement statement = this.plugin.getMysql().getConnection().prepareStatement("update `party_settings` set `publicParty`=?, `showOnList`=?, " +
                        "`modCanInvite`=?, `modCanKick=`? where `party`=?");
                statement.setBoolean(1, this.getPartySettings().isPublicParty());
                statement.setBoolean(2, this.getPartySettings().isShowOnList());
                statement.setBoolean(3, this.getPartySettings().isModCanInvite());
                statement.setBoolean(4, this.getPartySettings().isModCanKick());
                statement.setInt(5, this.partyId);
                statement.executeUpdate();
            } catch (SQLException e) {
                Log.error(e.getMessage());
                e.printStackTrace();
            }
        });
    }


    // --- ---

    // --- Party Utils ---
        public enum ChatRole {
            NONE, MEMBER, MOD, OWNER
        }

    @Data
    public final class PartySettings {
        private boolean publicParty = false;
        private boolean showOnList = true;

        private boolean modCanInvite = true;
        private boolean modCanKick = false;
    }
    // --- ---
}
