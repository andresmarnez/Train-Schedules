package com.andresmarnez.dao;

import com.andresmarnez.domain.*;

import com.andresmarnez.exceptions.TrainException;
import com.andresmarnez.util.HibernateUtil;

import jakarta.persistence.Tuple;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;

import java.util.List;

public class StationDAOImpl extends GenericDAOImpl<Station> implements StationDAO{


	public StationDAOImpl() {
		super(Station.class);
	}

	@Override
	public List<Station> findByCity(String city) throws TrainException {
		try(Session session = HibernateUtil.getSessionFactory().openSession()){

			CriteriaBuilder builder = session.getCriteriaBuilder();

			CriteriaQuery<Station> criteria = builder.createQuery(Station.class);
			Root<Station> root = criteria.from(Station.class);
			criteria.select(root)
					.where(builder.equal(root.get(Station_.CITY), city));

			return session.createQuery(criteria).getResultList();
		}
	}

	@Override
	public Station findByName(String name) throws TrainException {

		try(Session session = HibernateUtil.getSessionFactory().openSession()) {

			CriteriaBuilder builder = session.getCriteriaBuilder();

			CriteriaQuery<Station> criteria = builder.createQuery(Station.class);
			Root<Station> root = criteria.from(Station.class);
			criteria.select(root)
					.where(builder.equal(root.get(Station_.NAME), name));

			return session.createQuery(criteria).getSingleResultOrNull();
		}
	}

	@Override
	public List<String> findNameByCity(String city) throws TrainException {
		try(Session session = HibernateUtil.getSessionFactory().openSession()){

			CriteriaBuilder builder = session.getCriteriaBuilder();

			CriteriaQuery<String> criteria = builder.createQuery(String.class);
			Root<Station> root = criteria.from(Station.class);
			criteria.select(root.get(Station_.NAME))
					.where(builder.equal(root.get(Station_.CITY), city));

			return session.createQuery(criteria).getResultList();
		}
	}

	@Override
	public List<Object[]> findNameByCityObject(String city) throws TrainException {
		try(Session session = HibernateUtil.getSessionFactory().openSession()){

			CriteriaBuilder builder = session.getCriteriaBuilder();

			CriteriaQuery<Object[]> criteria = builder.createQuery(Object[].class);
			Root<Station> root = criteria.from(Station.class);

			Path<Long> id = root.get(Station_.id);
			Path<Long> name = root.get(Station_.NAME);
			Path<Long> cityPath = root.get(Station_.CITY);

			criteria.multiselect(id, name, cityPath)
					.where(builder.equal(cityPath, city));

			return session.createQuery(criteria).getResultList();
		}
	}

	@Override
	public List<Tuple> findNameByCityTuple(String city) throws TrainException {
		try(Session session = HibernateUtil.getSessionFactory().openSession()){

			CriteriaBuilder builder = session.getCriteriaBuilder();

			CriteriaQuery<Tuple> criteria = builder.createQuery(Tuple.class);
			Root<Station> root = criteria.from(Station.class);

			Path<Long> id = root.get(Station_.id);
			Path<Long> name = root.get(Station_.NAME);
			Path<Long> cityPath = root.get(Station_.CITY);

			criteria.multiselect(id, name, cityPath)
					.where(builder.equal(cityPath, city));

			return session.createQuery(criteria).getResultList();

		}
	}

}
