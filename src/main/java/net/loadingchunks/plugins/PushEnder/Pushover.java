package net.loadingchunks.plugins.PushEnder;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.bukkit.ChatColor;

import com.omertron.pushoverapi.PushoverApi;


public class Pushover {
	
	private String mAppToken;
	private PushEnder plugin;
	
	public Pushover(PushEnder host) {
		this.plugin = host;
    	ReloadTokens(host);
	}
	
	public void ReloadTokens(PushEnder host) {
		mAppToken = host.getAppToken();
	}
	
	public void SendMessages(String message) {
    	for (PushUser user : plugin.getUsers(false)) {
			PushoverApi client = new PushoverApi(mAppToken, user.userToken);
			client.isDebug = plugin.isDebug;
			try {
				plugin.getLogger().info("Push: " + client.sendMessage(ChatColor.stripColor(message)));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}
	}
	
	public void SendMessages(String title, String message) {
    	for (PushUser user : plugin.getUsers(false)) {
			PushoverApi client = new PushoverApi(mAppToken, user.userToken);
			client.isDebug = plugin.isDebug;
			try {
				plugin.getLogger().info("Push: " + client.sendMessage(ChatColor.stripColor(message), ChatColor.stripColor(title)));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}
	}
	
	public void SendMessages(String title, String message, PushType type) {
    	for (PushUser user : plugin.getUsers(false)) {
    		if(user.eventConfig.containsKey(type.toString()) && user.eventConfig.get(type.toString())) {
    			PushoverApi client = new PushoverApi(mAppToken, user.userToken);
    			client.isDebug = plugin.isDebug;
    			try {
    				plugin.getLogger().info("Push: " + client.sendMessage(ChatColor.stripColor(message), ChatColor.stripColor(title)));
    			} catch (UnsupportedEncodingException e) {
    				e.printStackTrace();
    			} catch (IOException e) {
    				e.printStackTrace();
    			}
    		}
    	}		
	}
}
