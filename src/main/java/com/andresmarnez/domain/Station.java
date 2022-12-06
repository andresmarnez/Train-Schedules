package com.andresmarnez.domain;

import jakarta.persistence.*;
import java.util.Collection;
import java.util.Objects;

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
	private String coordinates;

	@OneToMany(
			mappedBy = "origin",
			cascade = CascadeType.ALL,
			orphanRemoval = true
	)
	private Collection<Line> departureLines;

	@OneToMany(
			mappedBy = "end",
			cascade = CascadeType.ALL,
			orphanRemoval = true
	)
	private Collection<Line> arrivalLines;

	public Collection<Line> getDepartureLines() {
		return departureLines;
	}

	public void setDepartureLines(Collection<Line> departureLines) {
		this.departureLines = departureLines;
	}

	public Collection<Line> getArrivalLines() {
		return arrivalLines;
	}

	public void setArrivalLines(Collection<Line> arrivalLines) {
		this.arrivalLines = arrivalLines;
	}

	public Collection<Line> getLines() {
		return departureLines;
	}

	public void setLines(Collection<Line> lines) {
		this.departureLines = lines;
	}

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

	public String getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(String coordinates) {
		this.coordinates = coordinates;
	}


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Station station = (Station) o;
		return Objects.equals(name, station.name) && Objects.equals(city, station.city);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}

	@Override
	public String toString() {
		return name + ": " + city + " (" + coordinates + ")";
	}
}
