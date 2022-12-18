package com.andresmarnez.service;

import com.andresmarnez.dao.GenericDAO;
import com.andresmarnez.dao.GenericDAOImpl;
import com.andresmarnez.domain.Train;
import com.andresmarnez.exceptions.TrainException;

import java.time.LocalDateTime;
import java.util.List;

public class TrainDataService {

	private final GenericDAO<Train> trainGenericDAO;

	public TrainDataService() {
		this.trainGenericDAO = new GenericDAOImpl<>(Train.class);
	}

	public List<Train> getAllTrains() throws TrainException{

		List<Train> trains = trainGenericDAO.getAll();
		if (trains == null || trains.isEmpty())
			return null;

		return trains;
	}

	public Train getTrainById(Long id){
		try {

			return trainGenericDAO.findById(id);

		} catch (TrainException e) {

			return null;
		}
	}

	public void updateCheckedTimeById(LocalDateTime time, Long id){

		if (time!=null){
			Train train = null;

			try {
				train = trainGenericDAO.findById(id);

				if (train != null) {
					if (time.isBefore(LocalDateTime.now()))
						train.setTrainRoute(null);
					train.setLastCheck(time);
					trainGenericDAO.update(train);
				}

			} catch (TrainException e) {
				System.out.println("Train's revision couldn't be updated.");
			}
		}
	}

	public void updateRetirementById(LocalDateTime time, Long id){

		if (time!=null){

			Train train = null;

			try {

				train = trainGenericDAO.findById(id);

				if (train != null) {
					if (time.isBefore(LocalDateTime.now()))
						train.setTrainRoute(null);
					train.setRetireDate(time);
					trainGenericDAO.update(train);
				}

			} catch (TrainException e) {
				System.out.println("Train's retirement date couldn't be updated.");
			}
		}
	}

	public void deleteRetiredTrains() throws TrainException{

		List<Train> trains = getAllTrains();
		int totalTrains = 0;

		for (Train train : trains) {

			if (train.getRetireDate()!=null && train.getRetireDate().isBefore(LocalDateTime.now())){

				try {
					trainGenericDAO.deleteById(train.getId());
					System.out.println("Train " + train.getId() + " has been destroyed.");
					totalTrains++;

				} catch (TrainException e) {
					System.out.println("Couldn't delete retired train " + train.getId());
				}
			}
		}

		System.out.println(totalTrains + " trains destroyed totally.");
	}

}
