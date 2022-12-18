package com.andresmarnez.dao;

import com.andresmarnez.domain.Station;
import com.andresmarnez.exceptions.TrainException;
import jakarta.persistence.Tuple;

import java.util.List;

public interface StationDAO extends GenericDAO<Station>{

	List<Station> findByCity(String city) throws TrainException;

	Station findByName(String name) throws TrainException;

	List<String> findNameByCity(String city) throws TrainException;

	List<Object[]> findNameByCityObject(String city) throws TrainException;

	List<Tuple> findNameByCityTuple(String city) throws TrainException;
}
