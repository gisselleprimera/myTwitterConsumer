package com.web.twitter.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.web.twitter.dto.TweetDTO;
import com.web.twitter.service.TweetService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import javassist.NotFoundException;

@RestController
@RequestMapping("/tweets")
public class TweetController {

	@Autowired
	TweetService tweetService;

	@ApiOperation(value = "Find tweets that are persisted in database")
	@GetMapping()
	public ResponseEntity<List<TweetDTO>> findTweets() {

		try {			
			return new ResponseEntity<>(tweetService.findTweets(), HttpStatus.OK);
		} catch (Exception exc) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@ApiOperation(value = "Validate a tweet by id")
	@PutMapping("/validate/{id}")
	public ResponseEntity<Void> validateTweet(
			@ApiParam("Id of the tweet to be validated. Required.")
			@PathVariable(name = "id", required = true) Long id) {

		 try {
			tweetService.validateTweet(id);
			return new ResponseEntity<>(HttpStatus.OK);
		 } catch (NotFoundException exc) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		 } catch (Exception exc) {
				throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
		 }
	}

	@ApiOperation(value = "Find validated tweets by username")
	@GetMapping("/findByUsername")
	public ResponseEntity<List<TweetDTO>> findValidTweetsByUsername(
			@ApiParam("Username of the twitter account. Required.")
			@RequestParam(name = "username", required = true) String username) {
		
		try {
			return new ResponseEntity<>(tweetService.findValidTweetsByUsername(username), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@ApiOperation(value = "Find worldwide most trending topics")
	@GetMapping("/findHashtags")
	public ResponseEntity<List<String>> findMostUsedHashtags(
			@ApiParam("Limit of trending topics.")
			@RequestParam(defaultValue = "10") int limit) {

		try {
			return new ResponseEntity<>(tweetService.findMostUsedHashtags(limit), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
