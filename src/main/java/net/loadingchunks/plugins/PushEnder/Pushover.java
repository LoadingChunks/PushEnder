package net.loadingchunks.plugins.PushEnder;

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
    	for (PushUser user : plugin.getUsers()) {
    		PushoverMessage msg = new PushoverMessage(mAppToken, user.userToken, message);
    		msg.push();
    	}
	}
	
	public void SendMessages(String title, String message) {
    	for (PushUser user : plugin.getUsers()) {
    		PushoverMessage msg = new PushoverMessage(mAppToken, user.userToken, message);
    		msg.setTitle(title);
    		msg.push();
    	}
	}
	
	public void SendMessages(String title, String message, PushType type) {
    	for (PushUser user : plugin.getUsers()) {
    		if(user.eventConfig.containsKey(type.toString()) && user.eventConfig.get(type.toString())) {
    			PushoverMessage msg = new PushoverMessage(mAppToken, user.userToken, message);
    			msg.setTitle(title);
    			msg.push();
    		}
    	}		
	}
}
