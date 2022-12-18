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


	public StationDataService() {
		this.genericDAO = new GenericDAOImpl<>(Station.class);
		this.stationDAO = new StationDAOImpl();
	}


	public List<Station> getStationsByCity(String city) throws TrainException {
		if (city != null) {

			List<Station> stations = stationDAO.findByCity(city);
			if (stations == null || stations.isEmpty())
				throw new TrainException(TRAINCODE.NO_STATION_ON_CITY);

			return stations;
		} else {
			throw new TrainException(TRAINCODE.NO_CITY_PARAM);
		}
	}

	public Station getStationByName(String city) throws TrainException {
		if (city != null && !city.isBlank()) {

			return stationDAO.findByName(city);

		} else {
			return null;
		}
	}

	public void deleteById(Long id) {

		try {

			genericDAO.deleteById(id);

		} catch (TrainException e) {

		}
	}

	public Station findById(Long id) {

		try {

			return stationDAO.findById(id);

		} catch (TrainException e) {

			return null;
		}
	}

	public List<Station> getAllStations() throws TrainException {

		List<Station> stations = stationDAO.getAll();
		if (stations == null || stations.isEmpty())
			return null;

		return stations;
	}
}
