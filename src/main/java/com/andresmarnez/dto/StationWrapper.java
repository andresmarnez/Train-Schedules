package com.andresmarnez.dto;

public class StationWrapper {

	private final Long id;
	private final String name;
	private final String city;


	public StationWrapper(Long id, String name, String city) {
		this.id = id;
		this.name = name;
		this.city = city;
	}

	@Override
	public String toString() {
		return id +	": " + name + ", " + city;
	}
}
