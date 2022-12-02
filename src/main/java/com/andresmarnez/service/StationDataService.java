package com.andresmarnez.service;

import com.andresmarnez.dao.GenericDAO;
import com.andresmarnez.dao.GenericDAOImpl;
import com.andresmarnez.dao.StationDAO;
import com.andresmarnez.dao.StationDAOImpl;
import com.andresmarnez.domain.Station;
import com.andresmarnez.exceptions.TRAINCODE;
import com.andresmarnez.exceptions.TrainException;

import java.util.List;

public class StationDataService {

	private final GenericDAO<Station> genericDAO;
	private final StationDAO stationDAO;


	public StationDataService(GenericDAO<Station> genericDAO, StationDAO stationDAO) {
		this.genericDAO = new GenericDAOImpl<>(Station.class);
		this.stationDAO = new StationDAOImpl();
	}


	List<Station> getStationsByCity(String city) throws TrainException {
		if (city != null){

			List<Station> stations = stationDAO.findByCity(city);
			if (stations == null || stations.isEmpty())
				throw new TrainException(TRAINCODE.NO_STATION_ON_CITY);

			return stations;
		} else {
			throw new TrainException(TRAINCODE.NO_CITY_PARAM);
		}
	}

}
