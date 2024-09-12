package modelo.dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import modelo.entidades.CategoriaIngreso;
import modelo.entidades.CategoriaTotalDTO;
import modelo.entidades.Ingreso;
import modelo.entidades.Movimiento;

public class CategoriaIngresoDAO {
	
	EntityManager em;
	
	public CategoriaIngresoDAO(){
		this.em = Persistence.createEntityManagerFactory("ContabilidadMySQL").createEntityManager();
	}
	
	public List<CategoriaIngreso> getAll(){
		String sentenciJPQL = "SELECT c FROM Categoria c WHERE TYPE(c) = CategoriaIngreso";
        Query query = this.em.createQuery(sentenciJPQL);
        return (List<CategoriaIngreso>)query.getResultList();
	}
	
	public CategoriaIngreso getByID(int categoryID){
		return em.find(CategoriaIngreso.class, categoryID);
	}
	
	public List<CategoriaTotalDTO> getAllSummarized(LocalDate from, LocalDate to){
		IngresoDAO ingresoDAO = new IngresoDAO();
		CategoriaDAO categoriaDAO = new CategoriaDAO();
		
		List<CategoriaTotalDTO> categoriasTotal = new ArrayList<>();
		CategoriaTotalDTO categoriaAux;
		
		for (CategoriaIngreso category : getAll()) {
			List<Ingreso> movements = ingresoDAO.getAllByCategory(category.getId());
			List<Ingreso> filteredMovements = new ArrayList<>();
			
			for (Ingreso movement : movements) {
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
