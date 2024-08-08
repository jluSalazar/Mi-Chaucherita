package modelo.dao;

import java.util.ArrayList;
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
		List<Cuenta> accounts = (List<Cuenta>)query.getResultList();
		
		return accounts;
	}

	public Cuenta getByID(int accountID) {
		return em.find(Cuenta.class, accountID);
	}
	
	public void update(Cuenta cuenta) {
		em.getTransaction().begin();
			em.merge(cuenta); //insertarCuenta
		em.getTransaction().commit();
	}

	public boolean updateByID(Movimiento movement) {
		Cuenta aux;
		if (movement instanceof Ingreso) {
			Ingreso ingreso = (Ingreso) movement;
			aux = getByID(ingreso.getDestination().getId());
			aux.setTotal(aux.getTotal()+movement.getValue());
			return true;
		}else if (movement instanceof Egreso) {
			Egreso egreso = (Egreso) movement;
			aux = getByID(egreso.getSource().getId());
			aux.setTotal(aux.getTotal()-movement.getValue());
			return true;
		}else if (movement instanceof Transferencia) {
	        Transferencia transferencia = (Transferencia) movement;
			aux = getByID(transferencia.getDestination().getId());
			aux.setTotal(aux.getTotal()+movement.getValue());
			
			aux = getByID(transferencia.getSource().getId());
			aux.setTotal(aux.getTotal()-movement.getValue());
			return true;
		}
		return false;
	}

}
