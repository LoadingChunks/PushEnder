package net.loadingchunks.plugins.PushEnder;

public enum PushType {
	ENDER_DRAGON("dragonKill"),
	WITHER("witherKill"),
	JOIN("join"),
	KICK("kick"),
	BAN("ban"),
	QUIT("quit"),
	CALL_STAFF("callStaff")
	;
	
	private final String text;
	
	private PushType(final String text) {
		this.text = text;
	}
	
	@Override
	public String toString() {
		return text;
	}
}
