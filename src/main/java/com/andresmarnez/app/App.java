package com.andresmarnez.app;

import com.andresmarnez.dao.StationDAO;
import com.andresmarnez.dao.StationDAOImpl;
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


			StationDAO stationDAO = new StationDAOImpl();


			System.out.println(stationDAO.findNameByCityWrapper("Elche"));

		} catch (TrainException e) {
			System.out.println(e.getMessage());
		}
	}
}
