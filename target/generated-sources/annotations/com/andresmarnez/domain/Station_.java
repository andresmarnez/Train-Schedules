package com.andresmarnez.domain;

import jakarta.persistence.metamodel.CollectionAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import javax.annotation.processing.Generated;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Station.class)
public abstract class Station_ {

	public static volatile SingularAttribute<Station, String> city;
	public static volatile CollectionAttribute<Station, Line> arrivalLines;
	public static volatile SingularAttribute<Station, String> name;
	public static volatile SingularAttribute<Station, String> coordinates;
	public static volatile SingularAttribute<Station, Long> id;
	public static volatile CollectionAttribute<Station, Line> departureLines;

	public static final String CITY = "city";
	public static final String ARRIVAL_LINES = "arrivalLines";
	public static final String NAME = "name";
	public static final String COORDINATES = "coordinates";
	public static final String ID = "id";
	public static final String DEPARTURE_LINES = "departureLines";

}

