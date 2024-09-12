package modelo.dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import modelo.entidades.CategoriaEgreso;
import modelo.entidades.CategoriaTotalDTO;
import modelo.entidades.Egreso;
import modelo.entidades.Ingreso;

public class CategoriaEgresoDAO{

	private EntityManager em;

	public CategoriaEgresoDAO() {
		this.em = Persistence.createEntityManagerFactory("ContabilidadMySQL").createEntityManager();
	}
	
	public List<CategoriaEgreso> getAll(){
		
		String sentenciJPQL = "SELECT c FROM Categoria c WHERE TYPE(c) = CategoriaEgreso";
        Query query = this.em.createQuery(sentenciJPQL);
        return (List<CategoriaEgreso>)query.getResultList();
        
	}
	
	public CategoriaEgreso getByID(int categoryID){
		return em.find(CategoriaEgreso.class, categoryID);
	}
	
	public List<CategoriaTotalDTO> getAllSummarized(LocalDate from, LocalDate to){
		EgresoDAO egresoDAO = new EgresoDAO();
		CategoriaDAO categoriaDAO = new CategoriaDAO();
		
		List<CategoriaTotalDTO> categoriasTotal = new ArrayList<CategoriaTotalDTO>();
		CategoriaTotalDTO categoriaAux;
		
		for (CategoriaEgreso category : getAll()) {
			List<Egreso> movements = egresoDAO.getAllByCategory(category.getId());
			List<Egreso> filteredMovements = new ArrayList<>();
			
			for (Egreso movement : movements) {
				LocalDate date = movement.getDate();
				if (!date.isBefore(from) && !date.isAfter(to)) {
					filteredMovements.add(movement);
				}
			}

			categoriaAux = new CategoriaTotalDTO();
			categoriaAux.setId(category.getId());
			categoriaAux.setName(category.getName());
			categoriaAux.setTotal(categoriaDAO.getTotal(filteredMovements));
			
			categoriasTotal.add(categoriaAux);
		}
		
		return categoriasTotal;
	}
}
