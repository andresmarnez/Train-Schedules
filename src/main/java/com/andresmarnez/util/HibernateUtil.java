package com.andresmarnez.util;

import com.andresmarnez.exceptions.TRAINCODE;
import com.andresmarnez.exceptions.TrainException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateUtil {

	private static SessionFactory sessionFactory;

	/**
	 * Checks whether a session has been created already or not and if it is alive initializes.
	 * @return Session
	 */
	public static SessionFactory getSessionFactory() throws TrainException{

		if (sessionFactory == null || !sessionFactory.isOpen()){

			final StandardServiceRegistry REGISTRY = new StandardServiceRegistryBuilder()
					.configure("META-INF/hibernate.cfg.xml") // This should load the setting of META-INF/hibernate.cfg.xml
					.build();

			try{

				sessionFactory = new MetadataSources(REGISTRY) // Using the registry we further communicate the Database model we are using
						.buildMetadata()
						.buildSessionFactory();

			} catch (Exception ex){

				/*
				* The SessionFactory, once build would then destroy the RESGISTRY,
				* As we failed to build the Session Factory, this object must be destroyed manually.
				*/
				StandardServiceRegistryBuilder.destroy(REGISTRY);
				throw new TrainException("We failed to build the SessionFactory.\n" + ex.getMessage(), TRAINCODE.SESSION_FACTORY_FAILURE);
			}
		}

		return sessionFactory;
	}

	public static void closeSession(){
		if (sessionFactory !=null && sessionFactory.isOpen()){
			sessionFactory.close();
		}
	}
}
