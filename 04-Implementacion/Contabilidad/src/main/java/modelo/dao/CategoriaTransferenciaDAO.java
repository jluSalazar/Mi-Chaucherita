package modelo.dao;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import modelo.entidades.CategoriaTransferencia;

public class CategoriaTransferenciaDAO {
	private EntityManager em;

	public CategoriaTransferenciaDAO() {
		this.em = Persistence.createEntityManagerFactory("ContabilidadMySQL").createEntityManager();
	}

	public List<CategoriaTransferencia> getAll() {
		String sentenciJPQL = "SELECT c FROM Categoria c WHERE TYPE(c) = CategoriaTransferencia";
        Query query = this.em.createQuery(sentenciJPQL);
        return (List<CategoriaTransferencia>)query.getResultList();
	}

	public CategoriaTransferencia getByID(int categoryID) {
		return em.find(CategoriaTransferencia.class, categoryID);
	}
}
