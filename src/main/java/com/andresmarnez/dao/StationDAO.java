package com.andresmarnez.dao;

import com.andresmarnez.domain.Station;
import com.andresmarnez.dto.StationWrapper;
import jakarta.persistence.Tuple;

import java.util.List;

public interface StationDAO extends GenericDAO<Station>{

	List<Station> findByCity(String city);

	List<String> findNameByCity(String city);

	List<Object[]> findNameByCityObject(String city);

	List<Tuple> findNameByCityTuple(String city);

	List<StationWrapper> findNameByCityWrapper(String city);
}
