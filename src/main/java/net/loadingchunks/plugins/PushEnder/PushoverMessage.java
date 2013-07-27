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

	private boolean _HasTimeStamp = false;
	
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
	
	public void setToken(String token) { _Token = token; }
	public void setUser(String user) { _User = user; }
	public void setMessage(String message) { _Message = message; }
	public void setDevice(String device) { _Device = device; }
	public void setTitle(String title) { _Title = title; }
	public void setUrl(String url) { _Url = url; }
	public void setUrlTitle(String urltitle) { _UrlTitle = urltitle; }
	public void setPriority(Integer priority) { _Priority = priority; }
	public void setTimeStamp(Integer timestamp) { _TimeStamp = timestamp; }
	
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
