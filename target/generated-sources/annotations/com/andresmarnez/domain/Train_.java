package com.andresmarnez.domain;

import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.time.LocalDateTime;
import javax.annotation.processing.Generated;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Train.class)
public abstract class Train_ {

	public static volatile SingularAttribute<Train, LocalDateTime> lastCheck;
	public static volatile SingularAttribute<Train, Long> numWagons;
	public static volatile SingularAttribute<Train, Long> id;
	public static volatile SingularAttribute<Train, LocalDateTime> retireDate;
	public static volatile SingularAttribute<Train, TrainRoute> trainRoute;

	public static final String LAST_CHECK = "lastCheck";
	public static final String NUM_WAGONS = "numWagons";
	public static final String ID = "id";
	public static final String RETIRE_DATE = "retireDate";
	public static final String TRAIN_ROUTE = "trainRoute";

}

