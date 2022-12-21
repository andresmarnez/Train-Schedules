package com.andresmarnez.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "trains")
public class Train {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "num_wagons")
	private Long numWagons;

	@Column(name = "checked_time")
	private LocalDateTime lastCheck;

	@Column(name = "retired_time")
	private LocalDateTime retireDate;

	@OneToOne(orphanRemoval = true)
	@JoinColumn(name = "id")
	private TrainRoute trainRoute;

	public Train() {
	}

	public Train(Long id, Long numWagons, LocalDateTime lastCheck, LocalDateTime retireDate) {
		this.id = id;
		this.numWagons = numWagons;
		this.lastCheck = lastCheck;
		this.retireDate = retireDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getNumWagons() {
		return numWagons;
	}

	public TrainRoute getTrainRoute() {
		return trainRoute;
	}

	public void setTrainRoute(TrainRoute trainRoute) {
		this.trainRoute = trainRoute;
	}

	public void setNumWagons(Long numWagons) {
		this.numWagons = numWagons;
	}

	public LocalDateTime getLastCheck() {
		return lastCheck;
	}

	public void setLastCheck(LocalDateTime lastCheck) {
		this.lastCheck = lastCheck;
	}

	public LocalDateTime getRetireDate() {
		return retireDate;
	}

	public void setRetireDate(LocalDateTime retireDate) {
		this.retireDate = retireDate;
	}

	@Override
	public String toString() {
		return "TRAIN NUM."+ id +
				"\nWagons: " + numWagons +
				"\nLast Revision: " + lastCheck +
				((retireDate == null)?"" : "\nRetirement Date: "  + retireDate) +
				((trainRoute == null)?"" : trainRoute) + "\n";
	}
}
