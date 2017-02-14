package me.netshow.crawler.entity;

public class Empresa {
	
	private Long id;
	private String name;
	private String location;
	private int videosNumber;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public int getVideosNumber() {
		return videosNumber;
	}
	public void setVideosNumber(int videosNumber) {
		this.videosNumber = videosNumber;
	}
	@Override
	public String toString() {
		return "Empresa [id=" + id + ", name=" + name + ", location=" + location + ", videosNumber=" + videosNumber
				+ "]";
	}
	
	
	
}
