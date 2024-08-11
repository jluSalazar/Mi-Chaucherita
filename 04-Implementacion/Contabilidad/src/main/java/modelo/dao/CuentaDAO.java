package modelo.dao;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import model.entidades.Cuenta;
import model.entidades.Egreso;
import model.entidades.Ingreso;
import model.entidades.Movimiento;
import model.entidades.Transferencia;

public class CuentaDAO {
	private EntityManager em;

	public CuentaDAO() {
		this.em = Persistence.createEntityManagerFactory("ContabilidadMySQL").createEntityManager();
	}

	public List<Cuenta> getAll() {

		String sentenciJPQL = "SELECT c FROM Cuenta c";
		Query query = this.em.createQuery(sentenciJPQL);
		List<Cuenta> accounts = (List<Cuenta>) query.getResultList();

		return accounts;
	}

	public Cuenta getByID(int accountID) {
		return em.find(Cuenta.class, accountID);
	}

	public boolean update(Cuenta updatedCuenta) {
		try {
			em.getTransaction().begin();
			em.merge(updatedCuenta);
			em.getTransaction().commit();
			return true;
		} catch (Exception e) {
			this.em.getTransaction().rollback();
			e.printStackTrace();
			return false;
		}

	}

}
