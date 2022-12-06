package com.andresmarnez.domain;

import jakarta.persistence.*;
import java.sql.Time;


@Entity
@Table (name = "connections")
public class Connection {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_line")
	private Line idLine;

	@Column(name = "departure_time")
	private Time departureTime;

	@Column(name = "arrival_time")
	private Time arrivalTime;

	public Connection() {
	}

	public Connection(Long id, Time departureTime, Time arrivalTime) {
		this.id = id;
		this.departureTime = departureTime;
		this.arrivalTime = arrivalTime;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Time getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(Time departureTime) {
		this.departureTime = departureTime;
	}

	public Time getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(Time arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	@Override
	public String toString() {
		long diff = arrivalTime.getTime() - departureTime.getTime();
		long diffMinutes = diff / (60 * 1000) % 60;
		long diffHours = diff / (60 * 60 * 1000) % 24;
		return "Scheduled: " + departureTime + " - " + arrivalTime + " (" + diffHours + ":" + diffMinutes + ")";
	}
}
