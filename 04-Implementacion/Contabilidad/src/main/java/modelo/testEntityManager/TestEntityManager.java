package modelo.testEntityManager;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import model.entidades.Cuenta;

public class TestEntityManager {

	public static void main(String[] args) {
		EntityManager em = Persistence.createEntityManagerFactory("ContabilidadMySQL").createEntityManager();
		
		Cuenta cuenta = new Cuenta();
		cuenta.setName("Bolsillito");
		cuenta.setTotal(300);
		/*
		em.getTransaction().begin();
		em.persist(cuenta); //insertarCuenta
		em.getTransaction().commit();
		*
		
		//Consulta con JPQL
		String sentenciJPQL = "SELECT c FROM Cuenta c";
		Query query = em.createQuery(sentenciJPQL);
		List<Cuenta> cuentas = (List<Cuenta>)query.getResultList();
		for (Cuenta cuenta : cuentas) {
			System.out.println(cuenta.getName());
		}*
		
		//fIND
		Cuenta cuenta = em.find(Cuenta.class, 1);
		System.out.println(cuenta.getName());
		*/
		//Update
		em.getTransaction().begin();
		em.merge(cuenta); //insertarCuenta
		em.getTransaction().commit();
	}
}
