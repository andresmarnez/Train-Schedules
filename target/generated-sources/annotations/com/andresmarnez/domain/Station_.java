package com.andresmarnez.domain;

import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import javax.annotation.processing.Generated;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Station.class)
public abstract class Station_ {

	public static volatile SingularAttribute<Station, String> coordenates;
	public static volatile SingularAttribute<Station, String> city;
	public static volatile SingularAttribute<Station, String> name;
	public static volatile SingularAttribute<Station, Long> id;

	public static final String COORDENATES = "coordenates";
	public static final String CITY = "city";
	public static final String NAME = "name";
	public static final String ID = "id";

}

