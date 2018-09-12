package com.laxtech.twitter;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import com.laxtech.twitter.context.TwitterSecureContext;

import java.net.URLEncoder;

public class TwitterRestApi {

	public static String postTweet(String status, TwitterSecureContext context) {

		String response = null;
		try {
			System.out.println("postTweet() - Status is:" + status);
			System.out.println("postTweet() - context is:" + context);
			OAuthConsumer oAuthConsumer = new CommonsHttpOAuthConsumer(context.getConsumerKey(), context.getConsumerSecret());
			oAuthConsumer.setTokenWithSecret(context.getAccessToken(), context.getAccessTokenSecret());
			String encodeUrl = "https://api.twitter.com/1.1/statuses/update.json?status=" + status.replaceAll(" ","%20");
			HttpPost httpPost = new HttpPost(encodeUrl);
			System.out.println("postTweet() - encoded url is:" + encodeUrl);
			oAuthConsumer.sign(httpPost);

			HttpClient httpClient = new DefaultHttpClient();
			HttpResponse httpResponse = httpClient.execute(httpPost);
			System.out.println("postTweet() - httpResponse is:" + httpResponse);

			int statusCode = httpResponse.getStatusLine().getStatusCode();
			//System.out.println(statusCode + ':' + httpResponse.getStatusLine().getReasonPhrase());
			response = IOUtils.toString(httpResponse.getEntity().getContent());
			System.out.println(response);
		} catch (Throwable exp) {
			System.out.println("Error Occured:" +exp.getMessage());
			exp.printStackTrace();
		}
		return response;
	}

	public static void main(String[] args) {
		
			System.out.println(postTweet("Hello%20Twitter%20World.", null));
	}

}
