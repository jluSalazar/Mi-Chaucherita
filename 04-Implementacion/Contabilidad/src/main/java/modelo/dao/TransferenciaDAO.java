package modelo.dao;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import model.entidades.Ingreso;
import model.entidades.Movimiento;
import model.entidades.Transferencia;

public class TransferenciaDAO {

	private EntityManager em;

	public TransferenciaDAO() {
		this.em = Persistence.createEntityManagerFactory("ContabilidadMySQL").createEntityManager();
	}

	public List<Transferencia> getAll() {
		String sentenciaJPQL = "SELECT t FROM Transferencia t";
		Query query = this.em.createQuery(sentenciaJPQL);
		return (List<Transferencia>) query.getResultList();
	}

	public List<Transferencia> getAllByDate(LocalDate from, LocalDate to) {
		String sentenciaJPQL = "SELECT t FROM Transferencia t WHERE t.date BETWEEN :fromDate AND :toDate";
		Query query = this.em.createQuery(sentenciaJPQL);
		query.setParameter("fromDate", from);
		query.setParameter("toDate", to);
		return (List<Transferencia>) query.getResultList();
	}

	public List<Transferencia> getAllByAccount(int accountID) {
		String sentenciaJPQL = "SELECT t FROM Transferencia t WHERE (t.source.id = :accountID OR t.destination.id = :accountID)";
		Query query = this.em.createQuery(sentenciaJPQL);
		query.setParameter("accountID", accountID);
		return (List<Transferencia>) query.getResultList();
	}

	public List<Transferencia> getAllByCategory(int categoryID) {
		String sentenciaJPQL = "SELECT t FROM Transferencia t WHERE t.category.id = :categoryID";
		Query query = this.em.createQuery(sentenciaJPQL);
		query.setParameter("categoryID", categoryID);
		return (List<Transferencia>) query.getResultList();
	}

	public Transferencia getByID(int movementID) {
		return this.em.find(Transferencia.class, movementID);
	}

	public boolean newTransfer(Transferencia transfer) {
		try {
			this.em.getTransaction().begin();
			this.em.persist(transfer);
			this.em.getTransaction().commit();
			return true;
		} catch (Exception e) {
			if (this.em.getTransaction().isActive()) {
				this.em.getTransaction().rollback();
			}
			e.printStackTrace();
			return false;
		}
	}

	public boolean updateTransfer(Transferencia updatedTransfer) {
		try {
			this.em.getTransaction().begin();
			Transferencia existingTransfer = this.em.find(Transferencia.class, updatedTransfer.getId());
			if (existingTransfer != null) {
				existingTransfer.setDescription(updatedTransfer.getDescription());
				existingTransfer.setValue(updatedTransfer.getValue());
				existingTransfer.setDate(updatedTransfer.getDate());
				existingTransfer.setHour(updatedTransfer.getHour());
				existingTransfer.setSource(updatedTransfer.getSource());
				existingTransfer.setDestination(updatedTransfer.getDestination());
				existingTransfer.setCategory(updatedTransfer.getCategory());
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

	public boolean deleteTransfer(int movementID) {
		try {
			this.em.getTransaction().begin();
			Transferencia transfer = this.em.find(Transferencia.class, movementID);
			if (transfer != null) {
				this.em.remove(transfer);
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
}
