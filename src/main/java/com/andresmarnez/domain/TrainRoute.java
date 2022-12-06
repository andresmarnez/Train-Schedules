package com.andresmarnez.domain;

import jakarta.persistence.*;

@Entity
@Table (name="trains_route")
public class TrainRoute {

	@Id
	@Column (name = "id_train")
	private Long idTrain;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_connection")
	private Connection connection;

	public TrainRoute() {
	}

	public Long getTrain() {
		return idTrain;
	}

	public void setTrain(Long train) {
		this.idTrain = train;
	}

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	@Override
	public String toString() {
		return "\nRoute: " + connection;
	}
}
