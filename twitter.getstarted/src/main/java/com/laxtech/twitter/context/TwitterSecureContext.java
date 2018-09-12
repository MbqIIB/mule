package com.laxtech.twitter.context;

public class TwitterSecureContext {

	private String consumerKey;
	private String consumerSecret;
	private String accessToken;
	private String accessTokenSecret;

	public TwitterSecureContext(String consumerKey, String consumerSecret, String accessToken,
			String accessTokenSecret) {
		super();
		this.consumerKey = consumerKey;
		this.consumerSecret = consumerSecret;
		this.accessToken = accessToken;
		this.accessTokenSecret = accessTokenSecret;
	}

	public String getConsumerKey() {
		return consumerKey;
	}

	public String getConsumerSecret() {
		return consumerSecret;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public String getAccessTokenSecret() {
		return accessTokenSecret;
	}

	@Override
	public String toString() {
		return "TwitterSecureContext [consumerKey=" + consumerKey + ", consumerSecret=" + consumerSecret
				+ ", accessToken=" + accessToken + ", accessTokenSecret=" + accessTokenSecret + "]";
	}
}
