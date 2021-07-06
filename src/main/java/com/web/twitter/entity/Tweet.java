package com.web.twitter.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;


@Entity
public class Tweet {
	
	@Id
	private Long id;
	private String username;
	@Column(length=1000)
	private String text;
    @OneToOne()
	private GeoLocation location;
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
	public GeoLocation getLocation() {
		return location;
	}
	public void setLocation(GeoLocation location) {
		this.location = location;
	}
	public boolean isValid() {
		return valid;
	}
	public void setValid(boolean valid) {
		this.valid = valid;
	}

}
