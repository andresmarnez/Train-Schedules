package com.andresmarnez.dao;

import com.andresmarnez.domain.Station;
import jakarta.persistence.Tuple;

import java.util.List;

public interface StationDAO extends GenericDAO<Station>{

	List<Station> findByCity(String city);

	Station findByName(String name);

	List<String> findNameByCity(String city);

	List<Object[]> findNameByCityObject(String city);

	List<Tuple> findNameByCityTuple(String city);
}
