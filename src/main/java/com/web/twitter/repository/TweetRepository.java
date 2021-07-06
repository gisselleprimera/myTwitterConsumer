package com.web.twitter.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.web.twitter.entity.Tweet;

@Repository
public interface TweetRepository  extends JpaRepository<Tweet, Long> {

	List<Tweet> findByUsernameAndValidTrue(String username);
	
}
