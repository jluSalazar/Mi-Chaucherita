package modelo.dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import model.entidades.Categoria;
import model.entidades.CategoriaTotalDTO;
import model.entidades.Movimiento;

public class CategoriaDAO {
	
	public List<? extends Categoria> getAll() {
		Categoria categoria = new Categoria();
		CategoriaIngresoDAO categoriaIngresoDAO = new CategoriaIngresoDAO();
		CategoriaEgresoDAO categoriaEgresoDAO = new CategoriaEgresoDAO();
		List<Categoria> categories = categoria.getCategories();
		if (categories == null) {
			categories = new ArrayList<Categoria>();
			categories.addAll(categoriaIngresoDAO.getAll());
			categories.addAll(categoriaEgresoDAO.getAll());
		}

		return categories;
	}

	public Categoria getByID(int categoryID) {
		for (Categoria categoria : getAll()) {
			if (categoria.getId() == categoryID) {
				return categoria;
			}
		}
		return null;
	}

	public List<CategoriaTotalDTO> getAllSummarized(LocalDate from, LocalDate to) {
		List<CategoriaTotalDTO> categoriasTotal = new ArrayList<>();
		MovimientoDAO movimientoDAO = new MovimientoDAO();

		for (Categoria category : getAll()) {
			List<Movimiento> movements = new ArrayList<>(movimientoDAO.getAllByCategory(category.getId()));
			List<Movimiento> filteredMovements = new ArrayList<>();

			// Filtrar los movimientos dentro del rango de fechas
			for (Movimiento movement : movements) {
				LocalDate date = movement.getDate();
				if (!date.isBefore(from) && !date.isAfter(to)) {
					filteredMovements.add(movement);
				}
			}

			// Calcular el total para la categoría usando los movimientos filtrados
			CategoriaTotalDTO categoriaAux = new CategoriaTotalDTO();
			categoriaAux.setId(category.getId());
			categoriaAux.setName(category.getName());
			categoriaAux.setTotal(getTotal(filteredMovements));

			categoriasTotal.add(categoriaAux);
		}

		System.out.println("Categorías totales: " + categoriasTotal.toString());
		return categoriasTotal;
	}

	public CategoriaTotalDTO getSummarizedByID(int categoryID) {
		for (CategoriaTotalDTO category : this.getAllSummarized(
				LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth().getValue() - 1, 1),
				LocalDate.now())) {
			if (category.getId() == categoryID) {
				return category;
			}
		}
		return null;
	}
	
	//Metodos que no estan en el diseño
	protected double getTotal(List<? extends Movimiento> movements) {
		double total = 0;
		for (Movimiento movement : movements) {
			total += movement.getValue();
		}
		return total;
	}
}
