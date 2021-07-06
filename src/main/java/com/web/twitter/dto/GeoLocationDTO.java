package com.web.twitter.dto;

public class GeoLocationDTO implements java.io.Serializable {

	private static final long serialVersionUID = -2431478813114468535L;
	private double latitude;
    private double longitude;    
    
    public GeoLocationDTO(double latitude, double longitude) {
		super();
		this.latitude = latitude;
		this.longitude = longitude;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
    
    
}
