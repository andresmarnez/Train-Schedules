package com.andresmarnez.app;

import com.andresmarnez.exceptions.TrainException;
import com.andresmarnez.util.HibernateUtil;
import org.hibernate.Session;

public class App {

	private final Session session;

	public App() throws TrainException {

		this.session = HibernateUtil.getSession();
	}


	public static void main(String[] args) {


	}
}
