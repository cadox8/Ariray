package es.cadox8.ariray.party;

import es.cadox8.ariray.Ariray;
import es.cadox8.ariray.api.ArirayServer;
import es.cadox8.ariray.api.ArirayUser;
import es.cadox8.ariray.datatype.Formula;
import lombok.Getter;
import lombok.Setter;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class Party {

    private final Ariray plugin = Ariray.getInstance();

    @Getter private final int id;
    @Getter @Setter private String name;

    @Getter @Setter private String password;
    @Getter @Setter private boolean locked;
    @Getter @Setter private int ally_party;
    @Getter @Setter private int level;
    @Getter @Setter private float xp;

    @Getter private final UUID owner;

    @Getter private final List<ArirayUser> members;

    // Config

    public Party(String name, UUID owner) {
        this.id = 0; // Randomly Generated
        this.name = name;
        this.owner = owner;

        this.members = new ArrayList<>();

        this.ally_party = -1;
        this.password = "";
        this.locked = false;
        this.level = 0;
        this.xp = 0f;
    }

    public Party(String name, UUID owner, String password) {
        this.id = 0; // Randomly Generated
        this.name = name;
        this.owner = owner;

        this.members = new ArrayList<>();

        this.ally_party = -1;
        this.password = password;
        this.locked = true;
        this.level = 0;
        this.xp = 0f;
    }

    public List<ArirayUser> getOnlineMembers() {
        return this.members.stream().filter(ArirayUser::isOnline).collect(Collectors.toList());
    }

    public boolean addMember(ArirayUser member) {
        if (this.isMember(member)) return false;
        return this.members.add(member);
    }

    public boolean isMember(ArirayUser member) {
        return this.members.stream().anyMatch(m -> m.getUuid().equals(member.getUuid()));
    }

    public boolean removeMember(ArirayUser member) {
        return this.members.removeIf(m -> m.getUuid().equals(member.getUuid()));
    }

    public void addXP(float xp) {
        this.setXp(this.getXp() + xp);
    }

    protected float levelUp() {
        final float xpRemoved = this.getXpToLevel();
        this.setLevel(this.getLevel() + 1);
        this.setXp(this.getXp() - xpRemoved);
        return xpRemoved;
    }

    public int getXpToLevel() {
        return (mcMMO.getFormulaManager().getXPtoNextLevel(level, Formula.EXPONENTIAL)) * (getOnlineMembers().size() + mcMMO.p.getGeneralConfig().getPartyXpCurveMultiplier());
    }

    public String getXpToLevelPercentage() {
        return new DecimalFormat("##0.00%").format(this.getXp() / this.getXpToLevel());
    }

    public boolean isInLevelCap() {
        return this.getLevel() + 1 > this.plugin.getConfig().getInt("party.level_cap");
    }
}


