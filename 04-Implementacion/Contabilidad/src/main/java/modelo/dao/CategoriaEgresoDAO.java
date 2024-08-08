package modelo.dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import model.entidades.CategoriaEgreso;
import model.entidades.CategoriaTotalDTO;
import model.entidades.Egreso;

public class CategoriaEgresoDAO{

	public List<CategoriaEgreso> getAll(){
		CategoriaEgreso categoriasEgreso = new CategoriaEgreso();
		List<CategoriaEgreso>  expenseCategories = categoriasEgreso.getExpenseCategories();
		if(expenseCategories == null) {
			expenseCategories = new ArrayList<>();
			CategoriaEgreso cat1 = new CategoriaEgreso();
			cat1.setId(1);
			cat1.setName("Alimentación");
            
			CategoriaEgreso cat2 = new CategoriaEgreso();
			cat2.setId(2);
			cat2.setName("Transporte");
			
            CategoriaEgreso cat3 = new CategoriaEgreso();
            cat3.setId(3);
			cat3.setName("Entretenimiento");
			
            CategoriaEgreso cat4 = new CategoriaEgreso();
            cat4.setId(4);
			cat4.setName("Salud");
			
            CategoriaEgreso cat5 = new CategoriaEgreso();
            cat5.setId(5);
			cat5.setName("Educación");
			
            expenseCategories.add(cat1);
            expenseCategories.add(cat2);
            expenseCategories.add(cat3);
            expenseCategories.add(cat4);
            expenseCategories.add(cat5);
		}
		return expenseCategories;
	}
	
	public CategoriaEgreso getByID(int categoryID){
		for (CategoriaEgreso category : getAll()) {
            if (category.getId() == categoryID) {
                return category;
            }
        }
        return null;
	}
	
	public List<CategoriaTotalDTO> getAllSummarized(LocalDate from, LocalDate to){
		EgresoDAO egresoDAO = new EgresoDAO();
		CategoriaDAO categoriaDAO = new CategoriaDAO();
		
		List<CategoriaTotalDTO> categoriasTotal = new ArrayList<CategoriaTotalDTO>();
		CategoriaTotalDTO categoriaAux;
		
		for (CategoriaEgreso category : getAll()) {
			List<Egreso> movements = egresoDAO.getAllByCategory(category.getId());
			categoriaAux = new CategoriaTotalDTO();
			
			categoriaAux.setId(category.getId());
			categoriaAux.setName(category.getName());
			categoriaAux.setTotal(categoriaDAO.getTotal(movements));
			
			categoriasTotal.add(categoriaAux);
		}
		
		return categoriasTotal;
	}
}
