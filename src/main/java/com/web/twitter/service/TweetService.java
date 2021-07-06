package com.web.twitter.service;

import java.util.List;

import com.web.twitter.dto.TweetDTO;

import javassist.NotFoundException;
import twitter4j.Status;

public interface TweetService {
	
	public List<TweetDTO> findTweets();
	
	public void validateTweet(Long id) throws NotFoundException ;
	
	public List<TweetDTO> findValidTweetsByUsername(String username);
	
	public List<String> findMostUsedHashtags(int limit) throws Exception;
	
	public void saveTweet(Status status);

}
