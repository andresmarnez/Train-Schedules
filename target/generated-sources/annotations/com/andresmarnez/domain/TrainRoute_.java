package com.andresmarnez.domain;

import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import javax.annotation.processing.Generated;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(TrainRoute.class)
public abstract class TrainRoute_ {

	public static volatile SingularAttribute<TrainRoute, Connection> connection;
	public static volatile SingularAttribute<TrainRoute, Train> train;

	public static final String CONNECTION = "connection";
	public static final String TRAIN = "train";

}

