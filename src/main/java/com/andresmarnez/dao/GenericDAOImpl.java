package com.andresmarnez.dao;

import com.andresmarnez.exceptions.TRAINCODE;
import com.andresmarnez.exceptions.TrainException;
import com.andresmarnez.util.HibernateUtil;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.JDBCException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class GenericDAOImpl<T> implements GenericDAO<T>{

	private Class<T> entityClass;

	public GenericDAOImpl(Class<T> trainScheduleClass) {
		entityClass = trainScheduleClass;
	}

	@Override
	public void create(T object) throws TrainException{

		try(Session session = HibernateUtil.getSessionFactory().openSession()){

			Transaction transaction = session.getTransaction();
			try{
				transaction.begin();
				session.persist(object);
				transaction.commit();

			} catch (JDBCException e) {
				if (transaction.isActive())
					transaction.rollback();
				throw new TrainException("Could not be updated.",TRAINCODE.ON_UPDATE);
			}

		} catch (Exception ex){
			throw new TrainException("Error trying to recover the dataase",TRAINCODE.ROLLBACK);
		}
	}

	@Override
	public void update(T object) throws TrainException{

		try(Session session = HibernateUtil.getSessionFactory().openSession()){

			Transaction transaction = session.getTransaction();
			try{
				transaction.begin();
				session.merge(object);
				transaction.commit();

			} catch (JDBCException e) {
				if (transaction.isActive())
					transaction.rollback();
				throw new TrainException("Could not be updated.",TRAINCODE.ON_UPDATE);
			}
		} catch (Exception ex){
			throw new TrainException("Error trying to recover the database.",TRAINCODE.ROLLBACK);
		}
	}

	@Override
	public void delete(T object) throws TrainException{
		try(Session session = HibernateUtil.getSessionFactory().openSession()){

			Transaction transaction = session.getTransaction();
			try{
				transaction.begin();
				session.remove(object);
				transaction.commit();

			} catch (JDBCException e) {
				if (transaction.isActive())
					transaction.rollback();
				throw new TrainException("Could not be removed.",TRAINCODE.ON_DELETE);
			}

		} catch (Exception ex){
			throw new TrainException("Error trying to recover the database.",TRAINCODE.ROLLBACK);
		}
	}

	@Override
	public void deleteById(Long id) throws TrainException{
		try(Session session = HibernateUtil.getSessionFactory().openSession()){

			Transaction transaction = session.getTransaction();
			try{
				transaction.begin();
				T data = session.find(entityClass,id);

				if (data == null)
					throw new TrainException("Id doesn't exists", TRAINCODE.ON_DELETE);
				session.remove(data);

				transaction.commit();

			} catch (JDBCException e) {
				if (transaction.isActive())
					transaction.rollback();
				throw new TrainException("Failed to delete.",TRAINCODE.ON_DELETE);
			}

		} catch (Exception ex){
			throw new TrainException("Error trying to recover the database.",TRAINCODE.ROLLBACK);
		}
	}

	@Override
	public T findById(Long id) throws TrainException {
		try(Session session = HibernateUtil.getSessionFactory().openSession()){

			Transaction transaction = session.getTransaction();
			try{
				transaction.begin();
				T data = session.find(entityClass,id);

				if (data == null)
					throw new TrainException("Id doesn't exists", TRAINCODE.ON_FIND_BY_ID);
				transaction.commit();

				return data;

			} catch (JDBCException e) {
				if (transaction.isActive())
					transaction.rollback();
				throw new TrainException("Failed to make rollback.",TRAINCODE.ON_FIND_BY_ID);
			}

		} catch (Exception ex){
			throw new TrainException(ex.getMessage(),TRAINCODE.ON_FIND_BY_ID);
		}
	}

	@Override
	public List<T> getAll() throws TrainException {

		try(Session session = HibernateUtil.getSessionFactory().openSession()){

			CriteriaBuilder builder = session.getCriteriaBuilder();

			CriteriaQuery<T> criteria = builder.createQuery(entityClass);
			Root<T> root = criteria.from(entityClass);
			criteria.select(root);

			return session.createQuery(criteria).getResultList();

		} catch (Exception te){

			throw new TrainException(te.getMessage(),TRAINCODE.FIND_ALL);
		}
	}

}
