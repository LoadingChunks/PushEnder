package net.loadingchunks.plugins.PushEnder;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.*;

public class PushoverMessage {

	private String _Token;
	private String _User;
	private String _Message;
	private String _Device;
	private String _Title;
	private String _Url;
	private String _UrlTitle;
	private Integer _Priority;
	private Integer _TimeStamp;
	
	public PushoverMessage(String appToken, String userToken, String message) {
		_Token = appToken;
		_User = userToken;
		_Message = message;
		_Device = null;
		_Title = null;
		_Url = null;
		_UrlTitle = null;
		_Priority = 0;
		_TimeStamp = null;
	}
	
	public String getToken() { return _Token; }
	public String getUser() { return _User; }
	public String getMessage() { return _Message; }
	public String getDevice() { return _Device; }
	public String getTitle() { return _Title; }
	public String getUrl() { return _Url; }
	public String getUrlTitle() { return _UrlTitle; }
	public Integer getPriority() { return _Priority; }
	public Integer getTimeStamp() { return _TimeStamp; }
	
	public PushoverMessage setToken(String token) { _Token = token; return this; }
	public PushoverMessage setUser(String user) { _User = user; return this; }
	public PushoverMessage setMessage(String message) { _Message = message; return this; }
	public PushoverMessage setDevice(String device) { _Device = device; return this; }
	public PushoverMessage setTitle(String title) { _Title = title; return this; }
	public PushoverMessage setUrl(String url) { _Url = url; return this; }
	public PushoverMessage setUrlTitle(String urltitle) { _UrlTitle = urltitle; return this; }
	public PushoverMessage setPriority(Integer priority) { _Priority = priority; return this; }
	public PushoverMessage setTimeStamp(Integer timestamp) { _TimeStamp = timestamp; return this; }
	
	public void push() {
		try {
			URL url;
		    URLConnection urlConn;
		    DataOutputStream printout;
		    DataInputStream input;
		    // URL of CGI-Bin script.
		    url = new URL("https://api.pushover.net/1/messages.json");
		    // URL connection channel.
		    urlConn = url.openConnection();
		    // Let the run-time system (RTS) know that we want input.
		    urlConn.setDoInput(true);
		    // Let the RTS know that we want to do output.
		    urlConn.setDoOutput(true);
		    // No caching, we want the real thing.
		    urlConn.setUseCaches(false);
		    // Specify the content type.
		    urlConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		    // Send POST output.
		    printout = new DataOutputStream(urlConn.getOutputStream ());
		    
		    String content = "";
		    content += "token=" + URLEncoder.encode(_Token, "UTF-8");
		    content += "&user=" + URLEncoder.encode(_User, "UTF-8");
		    content += "&message=" + URLEncoder.encode(_Message, "UTF-8");
		    if (_Device != null)
			    content += "&device=" + URLEncoder.encode(_Device, "UTF-8");
		    if (_Title != null)
			    content += "&title=" + URLEncoder.encode(_Title, "UTF-8");
			if (_Url != null)
				content += "&url=" + URLEncoder.encode(_Url, "UTF-8");
			if (_UrlTitle != null)
				content += "&url_title=" + URLEncoder.encode(_UrlTitle, "UTF-8");
			if (_Priority != 0)
				content += "&priority=" + URLEncoder.encode(_Priority.toString(), "UTF-8");
			if (_TimeStamp != null)
				content += "&timestamp=" + URLEncoder.encode(_TimeStamp.toString(), "UTF-8");
				
		    printout.writeBytes (content);
		    printout.flush();
		    printout.close();
		    // Get response data.
		    input = new DataInputStream(urlConn.getInputStream());
		    String str;
		    while (null != ((str = input.readLine())))
		    	;//System.out.println (str);
		    input.close ();
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}
}