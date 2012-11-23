package net.loadingchunks.plugins.PushEnder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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

public class EventListener implements Listener {
	
	private Pushover mMessageSender;
	private Set<String> mKickedPlayers;
	
	public EventListener(Pushover messageSender) {
		mMessageSender = messageSender;
		mKickedPlayers = new HashSet<String>(); 
	}
	
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
    	//mMessageSender.SendMessages("Player joined", ChatColor.stripColor(event.getPlayer().getDisplayName()) + " joined the game.");
    }
    
    @EventHandler
    public void onPlayerKick(PlayerKickEvent event) {
    	mMessageSender.SendMessages(ChatColor.stripColor(event.getPlayer().getName()) + " was kicked", "`" + ChatColor.stripColor(event.getReason()) + "`");
    	mKickedPlayers.add(event.getPlayer().getName());
    }
	
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
    	if (mKickedPlayers.contains(event.getPlayer().getName())) {
    		mKickedPlayers.remove(event.getPlayer().getName());
    	}
    	else {
    		//mMessageSender.SendMessages("Player quit", ChatColor.stripColor(event.getPlayer().getDisplayName()) + " left the game.");
    	}
    }
    
    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
    	List<Short> bossEntities = new ArrayList<Short>();
    	bossEntities.add(EntityType.ENDER_DRAGON.getTypeId());
    	bossEntities.add(EntityType.WITHER.getTypeId());
    	
    	Player killer = event.getEntity().getKiller();
    	
    	for (int i = 0; i < bossEntities.size(); i++) {
    		if (bossEntities.get(i).equals(event.getEntityType().getTypeId())) {
    			if (killer != null)
    				mMessageSender.SendMessages("Boss killed", "The " + event.getEntityType().getName() + " was killed by " + ChatColor.stripColor(event.getEntity().getKiller().getDisplayName()) + "!");
//    			else
//    				mMessageSender.SendMessages("Boss killed", "The " + event.getEntityType().getName() + " was killed!");
    		}
    	}
    }
}
