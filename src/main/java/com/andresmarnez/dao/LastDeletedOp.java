package com.andresmarnez.dao;

import com.andresmarnez.exceptions.TrainException;

public interface LastDeletedOp{

	void saveLast(Object obj);
	Object getLast();
	void restoreLast() throws TrainException;
}
