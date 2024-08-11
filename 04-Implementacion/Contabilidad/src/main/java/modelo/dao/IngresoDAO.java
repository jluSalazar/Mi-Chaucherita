package modelo.dao;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import model.entidades.Egreso;
import model.entidades.Ingreso;
import model.entidades.Movimiento;

public class IngresoDAO {

    private EntityManager em;

    public IngresoDAO() {
        this.em = Persistence.createEntityManagerFactory("ContabilidadMySQL").createEntityManager();
    }

    public List<Ingreso> getAll() {
        String sentenciaJPQL = "SELECT i FROM Ingreso i";
        Query query = this.em.createQuery(sentenciaJPQL);
        return (List<Ingreso>) query.getResultList();
    }

    public List<Ingreso> getAllByDate(LocalDate from, LocalDate to) {
        String sentenciaJPQL = "SELECT i FROM Ingreso i WHERE i.date BETWEEN :fromDate AND :toDate";
        Query query = this.em.createQuery(sentenciaJPQL);
        query.setParameter("fromDate", from);
        query.setParameter("toDate", to);
        return (List<Ingreso>) query.getResultList();
    }

    public List<Ingreso> getAllByAccount(int accountID) {
        String sentenciaJPQL = "SELECT i FROM Ingreso i WHERE i.destination.id = :accountID";
        Query query = this.em.createQuery(sentenciaJPQL);
        query.setParameter("accountID", accountID);
        return (List<Ingreso>) query.getResultList();
    }

    public List<Ingreso> getAllByCategory(int categoryID) {
        String sentenciaJPQL = "SELECT i FROM Ingreso i WHERE i.category.id = :categoryID";
    	Query query = this.em.createQuery(sentenciaJPQL);
        query.setParameter("categoryID", categoryID);
        return (List<Ingreso>) query.getResultList();
    }

    public Ingreso getByID(int movementID) {
        return this.em.find(Ingreso.class, movementID);
    }

    public boolean newIncome(Ingreso income) {
        try {
            this.em.getTransaction().begin();
            this.em.persist(income);
            this.em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            this.em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateIncome(Ingreso updatedIncome) {
        try {
            this.em.getTransaction().begin();
            this.em.merge(updatedIncome);
            /*
            Ingreso existingIncome = this.em.find(Ingreso.class, updatedIncome.getId());
            if (existingIncome != null) {
                existingIncome.setDescription(updatedIncome.getDescription());
                existingIncome.setValue(updatedIncome.getValue());
                existingIncome.setDate(updatedIncome.getDate());
                existingIncome.setHour(updatedIncome.getHour());
                existingIncome.setDestination(updatedIncome.getDestination());
                existingIncome.setCategory(updatedIncome.getCategory());
                this.em.getTransaction().commit();
                return true;
            }
            this.em.getTransaction().rollback();*/

            this.em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            this.em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteIncome(int movementID) {
        try {
            this.em.getTransaction().begin();
            Ingreso income = this.em.find(Ingreso.class, movementID);
            if (income != null) {
                this.em.remove(income);
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