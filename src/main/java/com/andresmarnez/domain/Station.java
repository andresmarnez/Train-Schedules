package com.andresmarnez.domain;

import jakarta.persistence.*;
import jdk.jfr.Name;

@Entity

@Table (name = "stations")
public class Station {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String city;

	@Column
	private String coordenates;

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

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCoordenates() {
		return coordenates;
	}

	public void setCoordenates(String coordenates) {
		this.coordenates = coordenates;
	}


	@Override
	public String toString() {
		return name + ": " + city + " (" + coordenates + ")";
	}
}
