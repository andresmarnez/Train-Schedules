package com.andresmarnez.domain;

import jakarta.persistence.metamodel.CollectionAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import javax.annotation.processing.Generated;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Line.class)
public abstract class Line_ {

	public static volatile SingularAttribute<Line, Station> origin;
	public static volatile SingularAttribute<Line, Station> end;
	public static volatile SingularAttribute<Line, Long> id;
	public static volatile CollectionAttribute<Line, Connection> connections;

	public static final String ORIGIN = "origin";
	public static final String END = "end";
	public static final String ID = "id";
	public static final String CONNECTIONS = "connections";

}

