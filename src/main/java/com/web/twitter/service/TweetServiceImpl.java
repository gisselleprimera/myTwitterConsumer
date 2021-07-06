package com.web.twitter.service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.web.twitter.dto.TweetDTO;
import com.web.twitter.entity.GeoLocation;
import com.web.twitter.entity.Tweet;
import com.web.twitter.repository.TweetRepository;

import javassist.NotFoundException;
import twitter4j.Status;
import twitter4j.Trend;
import twitter4j.Trends;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.TwitterStream;
import twitter4j.conf.ConfigurationBuilder;

/**
 * The Class TweetServiceImpl.
 */
@Service
public class TweetServiceImpl implements TweetService {

	@Autowired
	TweetRepository tweetRepository;

	/** consumer key. */
	@Value("${oauth.consumerKey}")
	private String consumerKey;

	/** consumer secret. */
	@Value("${oauth.consumerSecret}")
	private String consumerSecret;

	/** access token. */
	@Value("${oauth.accessToken}")
	private String accessToken;

	/** secret access token. */
	@Value("${oauth.accessSecretToken}")
	private String accessSecretToken;

	Twitter twitter;

	TwitterStream twitterStream;

	ModelMapper modelMapper;

	ConfigurationBuilder cb;

	TwitterFactory tf;

	Type listType;

	/**
	 * Find tweets method.
	 *
	 * @return TweetDTO list
	 */
	@Override
	public List<TweetDTO> findTweets() {

		List<Tweet> tweets = tweetRepository.findAll();
		modelMapper = new ModelMapper();
		listType = new TypeToken<List<TweetDTO>>() {
		}.getType();
		List<TweetDTO> tweetsDTO = modelMapper.map(tweets, listType);
		return tweetsDTO;
	}

	/**
	 * Method that validates a tweet given the id.
	 *
	 * @param id - tweet id
	 * @throws NotFoundException when not found resource
	 */
	@Override
	public void validateTweet(Long id) throws NotFoundException {

		Tweet tweet = tweetRepository.findById(id).orElseThrow(() -> new NotFoundException(""));

		// Only update if the tweet has not been previously validated
		if (!tweet.isValid()) {
			tweet.setValid(true);
			tweetRepository.save(tweet);
		}
	}

	/**
	 * Method that finds valid tweets by username.
	 *
	 * @param username username of the user account
	 * @return TweetDTO list
	 */
	@Override
	public List<TweetDTO> findValidTweetsByUsername(String username) {

		List<Tweet> tweets = tweetRepository.findByUsernameAndValidTrue(username);
		modelMapper = new ModelMapper();
		Type listType = new TypeToken<List<TweetDTO>>() {
		}.getType();
		List<TweetDTO> tweetsDTO = modelMapper.map(tweets, listType);
		return tweetsDTO;
	}

	/**
	 * Method that finds most used twitter hashtags.
	 *
	 * @param limit - amount of hashtags to return
	 * @return String list
	 * @throws Exception
	 */
	@Override
	public List<String> findMostUsedHashtags(int limit) throws Exception {

		// Set Twitter Credentials
		cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true).setOAuthConsumerKey(consumerKey).setOAuthConsumerSecret(consumerSecret)
				.setOAuthAccessToken(accessToken).setOAuthAccessTokenSecret(accessSecretToken);
		tf = new TwitterFactory(cb.build());
		twitter = tf.getInstance();

		List<String> trendsList = new ArrayList<>();
		Trends trends = null;
		// Find worldwide trends
		trends = twitter.getPlaceTrends(1);
		if (trends == null)
			throw new Exception();

		Trend[] trendsArray = trends.getTrends();

		// Update limit attribute if the returned trend list is smaller
		if (limit > trendsArray.length)
			limit = trendsArray.length;

		for (int i = 0; i < limit; i++) {
			trendsList.add(trendsArray[i].getName());
		}

		return trendsList;

	}

	/**
	 * Save tweet to database.
	 *
	 * @param status - Object that contains tweet information
	 */
	@Override
	public void saveTweet(Status status) {

		Tweet tweet = new Tweet();
		tweet.setId(status.getId());
		tweet.setText(status.getText());
		tweet.setUsername(status.getUser().getScreenName());
		if (status.getGeoLocation() != null) {
			GeoLocation location = new GeoLocation(status.getGeoLocation().getLatitude(),
					status.getGeoLocation().getLongitude());
			tweet.setLocation(location);
		}
		tweetRepository.save(tweet);
	}

}
