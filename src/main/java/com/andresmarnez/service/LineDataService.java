package com.andresmarnez.service;

import com.andresmarnez.dao.GenericDAO;
import com.andresmarnez.dao.GenericDAOImpl;
import com.andresmarnez.domain.Line;
import com.andresmarnez.domain.Line_;
import com.andresmarnez.domain.Station;
import com.andresmarnez.exceptions.TrainException;
import com.andresmarnez.util.HibernateUtil;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import org.hibernate.Session;

import java.util.List;


public class LineDataService {

	private final GenericDAO<Line> genericDAO;

	public LineDataService() {
		this.genericDAO = new GenericDAOImpl<>(Line.class);
	}

	public void createLineByPoints(Long originId, Long endId) throws TrainException {
		StationDataService service = new StationDataService();

		Station originStation = service.findById(originId);
		Station endStation = service.findById(endId);

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {

			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<Line> criteria = builder.createQuery(Line.class);
			Root<Line> root = criteria.from(Line.class);

			Predicate[] predicates = new Predicate[2];
			predicates[0] = builder.equal(root.get(Line_.ORIGIN), originStation);
			predicates[1] = builder.equal(root.get(Line_.END), endStation);

			criteria.select(root)
					.where(predicates);

			List<Line> lines = session.createQuery(criteria).getResultList();
			System.out.println(lines);

			if(lines.isEmpty()){
				session.getTransaction().begin();
				session.persist(new Line(originStation,endStation));
				session.getTransaction().commit();
				System.out.println("New line created between " +
						originStation.getName() +
						" and " +
						endStation.getName());

			} else {
				System.out.println();
			System.out.println("Line could not be created as there already exists one between: " +
					originStation.getName() +
					" and " +
					endStation.getName());
			}
		}
	}
}
