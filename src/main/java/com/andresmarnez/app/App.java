package com.andresmarnez.app;

import com.andresmarnez.domain.*;
import com.andresmarnez.exceptions.TrainException;
import com.andresmarnez.util.HibernateUtil;
import org.hibernate.Session;

import java.time.LocalDateTime;


public class App {


	public App() throws TrainException {
	}


	public static void main(String[] args) {


		try(Session session = HibernateUtil.getSessionFactory().openSession()){

			session.getTransaction().begin();


			Train str = session.find(Train.class,101);
			System.out.println(str);


			session.getTransaction().commit();

		} catch (TrainException e) {
			System.out.println(e.getMessage());
		}
	}
}
