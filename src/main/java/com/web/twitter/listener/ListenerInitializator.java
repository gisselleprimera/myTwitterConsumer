package com.web.twitter.listener;


import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;

@Component
public class ListenerInitializator {
	
	@Value("${oauth.consumerKey}")
	private String consumerKey;

	@Value("${oauth.consumerSecret}")
	private String consumerSecret;

	@Value("${oauth.accessToken}")
	private String accessToken;

	@Value("${oauth.accessSecretToken}")
	private String accessSecretToken;
	
	TwitterStream twitterStream;
	
	@Autowired
	TwitterListener listener;
	
	@EventListener(ApplicationReadyEvent.class)
	public void persistTweets() { 
		
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true).setOAuthConsumerKey(consumerKey).setOAuthConsumerSecret(consumerSecret)
				.setOAuthAccessToken(accessToken).setOAuthAccessTokenSecret(accessSecretToken);
		
		twitterStream = new TwitterStreamFactory(cb.build()).getInstance();		
		twitterStream.addListener(listener);
		twitterStream.sample();		
	}
	
	@PreDestroy
	public void shutDownListener() {
		twitterStream.shutdown();
	}

}
