package modelo.dao;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import modelo.entidades.Categoria;
import modelo.entidades.Egreso;
import modelo.entidades.Movimiento;

public class EgresoDAO {

	private EntityManager em;

	public EgresoDAO() {
        this.em = Persistence.createEntityManagerFactory("ContabilidadMySQL").createEntityManager();
    }

    public List<Egreso> getAll() {
        String sentenciaJPQL = "SELECT e FROM Egreso e";
        Query query = this.em.createQuery(sentenciaJPQL);
        return (List<Egreso>) query.getResultList();
    }

    public List<Egreso> getAllByDate(LocalDate from, LocalDate to) {
        String sentenciaJPQL = "SELECT e FROM Egreso e WHERE e.date BETWEEN :fromDate AND :toDate";
        Query query = this.em.createQuery(sentenciaJPQL);
        query.setParameter("fromDate", from);
        query.setParameter("toDate", to);
        return (List<Egreso>) query.getResultList();
    }

    public List<Egreso> getAllByAccount(int accountID) {
        String sentenciaJPQL = "SELECT e FROM Egreso e WHERE e.source.id = :accountID";
        Query query = this.em.createQuery(sentenciaJPQL);
        query.setParameter("accountID", accountID);
        return (List<Egreso>) query.getResultList();
    }

    public List<Egreso> getAllByCategory(int categoryID) {
        String sentenciaJPQL = "SELECT e FROM Egreso e WHERE e.category.id = :categoryID";
        Query query = this.em.createQuery(sentenciaJPQL);
        query.setParameter("categoryID", categoryID);
        return (List<Egreso>) query.getResultList();
    }

    public Egreso getByID(int movementID) {
        return this.em.find(Egreso.class, movementID);
    }

    public boolean newExpense(Egreso expense) {
        try {
            this.em.getTransaction().begin();
            this.em.persist(expense);
            this.em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            this.em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateExpense(Egreso updatedExpense) {
        try {
            this.em.getTransaction().begin();
            Egreso existingExpense = this.em.find(Egreso.class, updatedExpense.getId());
            if (existingExpense != null) {
                existingExpense.setDescription(updatedExpense.getDescription());
                existingExpense.setValue(updatedExpense.getValue());
                existingExpense.setDate(updatedExpense.getDate());
                existingExpense.setHour(updatedExpense.getHour());
                existingExpense.setSource(updatedExpense.getSource());
                existingExpense.setCategory(updatedExpense.getCategory());
                this.em.getTransaction().commit();
                return true;
            }
            this.em.getTransaction().rollback();
            return false;
        } catch (Exception e) {
            this.em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteExpense(int movementID) {
        try {
            this.em.getTransaction().begin();
            Egreso expense = this.em.find(Egreso.class, movementID);
            if (expense != null) {
                this.em.remove(expense);
                this.em.getTransaction().commit();
                return true;
            }
            this.em.getTransaction().rollback();
            return false;
        } catch (Exception e) {
            this.em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }
}