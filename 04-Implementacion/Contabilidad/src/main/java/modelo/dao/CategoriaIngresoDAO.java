package modelo.dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import model.entidades.CategoriaIngreso;
import model.entidades.CategoriaTotalDTO;
import model.entidades.Ingreso;

public class CategoriaIngresoDAO {
	public List<CategoriaIngreso> getAll(){
		
		List<CategoriaIngreso> incomeCategories =  new ArrayList<>();
			CategoriaIngreso cat1 = new CategoriaIngreso();
			cat1.setId(6);
			cat1.setName("Salario");
            
			CategoriaIngreso cat2 = new CategoriaIngreso();
			cat2.setId(7);
			cat2.setName("Inversiones");
			
            CategoriaIngreso cat3 = new CategoriaIngreso();
            cat3.setId(8);
			cat3.setName("Regalos");
			
            CategoriaIngreso cat4 = new CategoriaIngreso();
            cat4.setId(9);
			cat4.setName("Ventas");
			
            CategoriaIngreso cat5 = new CategoriaIngreso();
            cat5.setId(10);
			cat5.setName("Otros");
			
            incomeCategories.add(cat1);
            incomeCategories.add(cat2);
            incomeCategories.add(cat3);
            incomeCategories.add(cat4);
            incomeCategories.add(cat5);
		
		return incomeCategories;
	}
	
	public CategoriaIngreso getByID(int categoryID){
		for (CategoriaIngreso categoria : getAll()) {
			if(categoria.getId() == categoryID) {
				return categoria;
			}
		}
		return null;
	}
	
	public List<CategoriaTotalDTO> getAllSummarized(LocalDate from, LocalDate to){
		IngresoDAO ingresoDAO = new IngresoDAO();
		CategoriaDAO categoriaDAO = new CategoriaDAO();
		
		List<CategoriaTotalDTO> categoriasTotal = new ArrayList<CategoriaTotalDTO>();
		CategoriaTotalDTO categoriaAux;
		
		for (CategoriaIngreso category : getAll()) {
			List<Ingreso> movements = ingresoDAO.getAllByCategory(category.getId());
			categoriaAux = new CategoriaTotalDTO();
			
			categoriaAux.setId(category.getId());
			categoriaAux.setName(category.getName());
			categoriaAux.setTotal(categoriaDAO.getTotal(movements));
			
			categoriasTotal.add(categoriaAux);
		}
		
		return categoriasTotal;
	}
}
