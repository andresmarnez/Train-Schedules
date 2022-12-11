package com.andresmarnez.dao;

import com.andresmarnez.exceptions.TrainException;

public interface GenericDAO<S> {

	void create(S object) throws TrainException;
	void update(S object) throws TrainException;
	void delete(S object) throws TrainException;
	void deleteById(Long id) throws TrainException;
	S findById(Long id) throws TrainException;
	void showAll() throws TrainException;
}
