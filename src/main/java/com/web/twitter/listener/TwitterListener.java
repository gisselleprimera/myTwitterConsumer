package com.web.twitter.listener;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.web.twitter.service.TweetService;

import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;

@Component
public class TwitterListener implements StatusListener{
	
	@Autowired
	TweetService tweetService;
	
	@Value("${languages}")
	private Set<String> languages;
	
	@Value("${user.followers}")
	private int followers;
	
    private static final Logger log = LoggerFactory.getLogger(TwitterListener.class);

	@Override
	public void onStatus(Status status) {
		
		//Only tweets that meet the conditions are saved in the database
		if (languages.contains(status.getLang()) && status.getUser().getFollowersCount() > followers) {			
			tweetService.saveTweet(status);
		}		
	}	
	@Override
	public void onException(Exception ex) {
		log.error(ex.getMessage());
	}

	@Override
	public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
		
	}

	@Override
	public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
		
	}

	@Override
	public void onScrubGeo(long userId, long upToStatusId) {
		
	}

	@Override
	public void onStallWarning(StallWarning warning) {
		
	}

}
