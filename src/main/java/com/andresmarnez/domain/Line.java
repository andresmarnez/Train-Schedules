package com.andresmarnez.domain;

import jakarta.persistence.*;

import java.util.Collection;
import java.util.Objects;

@Entity
@Table (name = "line")
public class Line {

	@Id
	@Column (nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "origin_station")
	private Station origin;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "end_station")
	private Station end;

	@OneToMany(
			mappedBy = "idLine",
			orphanRemoval = true
	)
	private Collection<Connection> connections;

	public Line() {
	}

	public Line(Station origin, Station end) {
		this.origin = origin;
		this.end = end;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;

		if (o == null || getClass() != o.getClass())
			return false;

		Line that = (Line) o;
		return Objects.equals(origin.getId(), that.origin.getId()) && Objects.equals(end.getId(), that.end.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hash(origin, end);
	}

	public Collection<Connection> getConnections() {
		return connections;
	}

	public void setConnections(Collection<Connection> connections) {
		this.connections = connections;
	}

	public Station getOrigin() {
		return origin;
	}

	public void setOrigin(Station origin) {
		this.origin = origin;
	}

	public Station getEnd() {
		return end;
	}

	public void setEnd(Station end) {
		this.end = end;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Line: " + origin.getName() + ", "+ origin.getCity() + " -> " + end.getName() + ", " + end.getCity();
	}
}
