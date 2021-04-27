package es.cadox8.ariray.api;

import es.cadox8.ariray.channels.ChatChannel;
import lombok.NoArgsConstructor;
import org.bukkit.OfflinePlayer;

import java.util.UUID;

@NoArgsConstructor
public class ChatAPI {

    /**
     * Check if a player is currently talking in party chat.
     *
     * @param uuid The player's UUID to check
     * @return true if the player is using party chat, false otherwise
     */
    public static boolean isUsingPartyChat(UUID uuid) {
        return ArirayServer.getUser(uuid).getChatChannel() == ChatChannel.PARTY;
    }

    /**
     * Check if a player is currently talking in party chat.
     *
     * @param offlinePlayer OfflinePlayer to check
     * @return true if the player is using party chat, false otherwise
     */
    public static boolean isUsingPartyChat(OfflinePlayer offlinePlayer) {
        return isUsingPartyChat(offlinePlayer.getUniqueId());
    }
}
