package net.loadingchunks.plugins.PushEnder;

import net.pushover.client.PushoverClient;
import net.pushover.client.PushoverException;
import net.pushover.client.PushoverMessage;
import net.pushover.client.PushoverRestClient;
import net.pushover.client.Status;


public class Pushover {
	
	private String mAppToken;
	private PushEnder plugin;
	private PushoverClient pClient;
	
	public Pushover(PushEnder host) {
		this.plugin = host;
    	ReloadTokens(host);
    	pClient = new PushoverRestClient();
	}
	
	public void ReloadTokens(PushEnder host) {
		mAppToken = host.getAppToken();
	}
	
	public void SendMessages(String message) {
    	for (PushUser user : plugin.getUsers()) {
    		try {
				Status result = pClient.pushMessage(PushoverMessage.builderWithApiToken(mAppToken)
						.setUserId(user.userToken)
						.setMessage(message)
						.build());
				plugin.getLogger().info(String.format("status: %d, request id: %s", result.getStatus(), result.getRequestId()));
			} catch (PushoverException e) {
				e.printStackTrace();
			}
    	}
	}
	
	public void SendMessages(String title, String message) {
    	for (PushUser user : plugin.getUsers()) {
    		try {
				Status result = pClient.pushMessage(PushoverMessage.builderWithApiToken(mAppToken)
						.setUserId(user.userToken)
						.setMessage(message)
						.setTitle(title)
						.build());
				plugin.getLogger().info(String.format("status: %d, request id: %s", result.getStatus(), result.getRequestId()));
			} catch (PushoverException e) {
				e.printStackTrace();
			}
    	}
	}
	
	public void SendMessages(String title, String message, PushType type) {
    	for (PushUser user : plugin.getUsers()) {
    		if(user.eventConfig.containsKey(type.toString()) && user.eventConfig.get(type.toString())) {
    	    	try {
					Status result = pClient.pushMessage(PushoverMessage.builderWithApiToken(mAppToken)
							.setUserId(user.userToken)
							.setMessage(message)
							.setTitle(title)
							.build());
					plugin.getLogger().info(String.format("status: %d, request id: %s", result.getStatus(), result.getRequestId()));
				} catch (PushoverException e) {
					e.printStackTrace();
				}
    		}
    	}		
	}
}
