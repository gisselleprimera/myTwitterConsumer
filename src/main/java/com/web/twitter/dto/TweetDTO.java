package com.web.twitter.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class TweetDTO implements java.io.Serializable {
	
	private static final long serialVersionUID = -2674541722267270050L;
	private Long id;
	private String username;
	private String text;
	private GeoLocationDTO location;
	private boolean valid;		
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}	
	public GeoLocationDTO getLocation() {
		return location;
	}
	public void setLocation(GeoLocationDTO location) {
		this.location = location;
	}
	public boolean isValid() {
		return valid;
	}
	public void setValid(boolean valid) {
		this.valid = valid;
	}

}
