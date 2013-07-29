package net.loadingchunks.plugins.PushEnder;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PushEnderEventListener implements Listener {
	
	private Pushover mMessageSender;
	private Set<String> mKickedPlayers;
	private PushEnder plugin;
	
	public PushEnderEventListener(PushEnder plugin, Pushover messageSender) {
		this.plugin = plugin;
		mMessageSender = messageSender;
		mKickedPlayers = new HashSet<String>(); 
	}
	
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
    	mMessageSender.SendMessages("Player joined", ChatColor.stripColor(event.getPlayer().getDisplayName()) + " joined the game.", PushType.JOIN);
    }
    
    @EventHandler
    public void onPlayerKick(PlayerKickEvent event) {
    	mMessageSender.SendMessages(ChatColor.stripColor(event.getPlayer().getName()) + " was kicked", "`" + ChatColor.stripColor(event.getReason()) + "`", PushType.KICK);
    	mKickedPlayers.add(event.getPlayer().getName());
    }
	
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
    	if (mKickedPlayers.contains(event.getPlayer().getName())) {
    		mKickedPlayers.remove(event.getPlayer().getName());
    	}
    	else {
        	mMessageSender.SendMessages("Player quit", ChatColor.stripColor(event.getPlayer().getDisplayName()) + " left the game.", PushType.QUIT);
    	}
    }
    
    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
    	HashMap<Short, PushType> bossEntities = new HashMap<Short, PushType>();
    	
    	bossEntities.put(EntityType.ENDER_DRAGON.getTypeId(), PushType.ENDER_DRAGON);
    	bossEntities.put(EntityType.WITHER.getTypeId(), PushType.WITHER);

    	Player killer = event.getEntity().getKiller();
    	
    	if(killer == null)
    		return;
    	
    	for(Entry<Short, PushType> set : bossEntities.entrySet()) {
    		if(set.getKey().equals(event.getEntityType().getTypeId())) {
    			mMessageSender.SendMessages("Boss killed", "The " + event.getEntityType().getName() + " was killed by " + ChatColor.stripColor(event.getEntity().getKiller().getDisplayName()) + "!", set.getValue());
    		}
    	}
    }
}
