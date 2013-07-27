package net.loadingchunks.plugins.PushEnder;

import java.util.List;

public class Pushover {
	
	private String mAppToken;
	private List<String> mUserTokens;
	
	public Pushover(PushEnder host) {
    	ReloadTokens(host);
	}
	
	public void ReloadTokens(PushEnder host) {
		mAppToken = host.getAppToken();
    	mUserTokens = host.getUserTokens();
	}
	
	public void SendMessages(String message) {
    	for (String userToken : mUserTokens) {
    		PushoverMessage msg = new PushoverMessage(mAppToken, userToken, message);
    		msg.push();
    	}
	}
	
	public void SendMessages(String title, String message) {
    	for (String userToken : mUserTokens) {
    		PushoverMessage msg = new PushoverMessage(mAppToken, userToken, message);
    		msg.setTitle(title);
    		msg.push();
    	}
	}
}
