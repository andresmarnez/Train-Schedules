package com.andresmarnez.domain;

import jakarta.persistence.*;

@Entity
@Table (name = "trains_route")
public class TrainRoute {

	@Id
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_train")
	private Train train;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_connection")
	private Connection connection;

	public TrainRoute() {
	}

	public Train getTrain() {
		return train;
	}

	public void setTrain(Train train) {
		this.train = train;
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
