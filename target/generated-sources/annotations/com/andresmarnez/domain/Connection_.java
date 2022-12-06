package com.andresmarnez.domain;

import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.sql.Time;
import javax.annotation.processing.Generated;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Connection.class)
public abstract class Connection_ {

	public static volatile SingularAttribute<Connection, Time> departureTime;
	public static volatile SingularAttribute<Connection, Time> arrivalTime;
	public static volatile SingularAttribute<Connection, Long> id;
	public static volatile SingularAttribute<Connection, Line> idLine;

	public static final String DEPARTURE_TIME = "departureTime";
	public static final String ARRIVAL_TIME = "arrivalTime";
	public static final String ID = "id";
	public static final String ID_LINE = "idLine";

}

