package com.andresmarnez.dao;

import com.andresmarnez.exceptions.TrainException;
import java.util.List;

public interface GenericDAO<S> extends LastDeletedOp {

	void create(S object) throws TrainException;
	void update(S object) throws TrainException;
	void delete(S object) throws TrainException;
	void deleteById(Long id) throws TrainException;
	S findById(Long id) throws TrainException;
	List<S> getAll() throws TrainException;
}
