package modelo.dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import modelo.entidades.Movimiento;

public class MovimientoDAO {
	private EntityManager em;

	public MovimientoDAO() {
		this.em = Persistence.createEntityManagerFactory("ContabilidadMySQL").createEntityManager();
	}

	public List<? extends Movimiento> getAll() {
		String sentenciaJPQL = "SELECT m FROM Movimiento m";
		Query query = this.em.createQuery(sentenciaJPQL);
		return (List<Movimiento>) query.getResultList();
	}

	
	public List<? extends Movimiento> getAllByAccount(int accountID) {
		String sentenciaJPQL = "SELECT m FROM Movimiento m WHERE (m.source.id = :accountID OR m.destination.id = :accountID)";
		Query query = this.em.createQuery(sentenciaJPQL);
		query.setParameter("accountID", accountID);
		List<Movimiento> movementsByAccount = (List<Movimiento>) query.getResultList();
		return sortMovementsByDate(movementsByAccount);
	}

	public List<? extends Movimiento> getAllByCategory(int categoryID) {
		List<Movimiento> movementsByCategory = new ArrayList<>();
		IngresoDAO ingresoDAO = new IngresoDAO();
		EgresoDAO egresoDAO = new EgresoDAO();
		TransferenciaDAO transferenciaDAO = new TransferenciaDAO();
		
		movementsByCategory.addAll(ingresoDAO.getAllByCategory(categoryID));
		movementsByCategory.addAll(egresoDAO.getAllByCategory(categoryID));
		movementsByCategory.addAll(transferenciaDAO.getAllByCategory(categoryID));

		return sortMovementsByDate(movementsByCategory);
		/*
		String sentenciaJPQL = "SELECT m FROM Movimiento m WHERE m.category.id = :categoryID";
		Query query = this.em.createQuery(sentenciaJPQL);
		query.setParameter("categoryID", categoryID);
		List<Movimiento> movementsByCategory = (List<Movimiento>) query.getResultList();
		return sortMovementsByDate(movementsByCategory);*/
	}

	public Movimiento getByID(int movementID) {
		return this.em.find(Movimiento.class, movementID);
	}

	public boolean updateMovement(Movimiento movement) {
		try {
			this.em.getTransaction().begin();
			Movimiento existingMovement = this.em.find(Movimiento.class, movement.getId());
			if (existingMovement != null) {
				existingMovement.setDescription(movement.getDescription());
				existingMovement.setValue(movement.getValue());
				existingMovement.setDate(movement.getDate());
				existingMovement.setHour(movement.getHour());
				this.em.getTransaction().commit();
				return true;
			}
			this.em.getTransaction().rollback();
			return false;
		} catch (Exception e) {
			if (this.em.getTransaction().isActive()) {
				this.em.getTransaction().rollback();
			}
			e.printStackTrace();
			return false;
		}
	}

	public boolean deleteMovement(int movementID) {
		try {
			this.em.getTransaction().begin();
			Movimiento movement = this.em.find(Movimiento.class, movementID);
			if (movement != null) {
				this.em.remove(movement);
				this.em.getTransaction().commit();
				return true;
			}
			this.em.getTransaction().rollback();
			return false;
		} catch (Exception e) {
			if (this.em.getTransaction().isActive()) {
				this.em.getTransaction().rollback();
			}
			e.printStackTrace();
			return false;
		}
	}


	public List<Movimiento> sortMovementsByDate(List<Movimiento> auxMovements) {
		auxMovements.sort(Comparator.comparing(Movimiento::getDate).reversed());
		return auxMovements;
	}

	public List<Movimiento> filterMovementByDate(List<Movimiento> allMovements, LocalDate from, LocalDate to) {
		List<Movimiento> filteredMovements = new ArrayList<>();
		for (Movimiento movimiento : allMovements) {
			LocalDate date = movimiento.getDate();
			if (!date.isBefore(from) && !date.isAfter(to)) {
				filteredMovements.add(movimiento);
			}
		}
		return filteredMovements;
	}

}
