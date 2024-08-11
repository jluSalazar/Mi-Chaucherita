package modelo.dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import model.entidades.Categoria;
import model.entidades.CategoriaTotalDTO;
import model.entidades.Cuenta;
import model.entidades.Movimiento;

public class CategoriaDAO {
	
	private EntityManager em;

	public CategoriaDAO() {
		this.em = Persistence.createEntityManagerFactory("ContabilidadMySQL").createEntityManager();
	}
	
	public List<? extends Categoria> getAll() {
		
		String sentenciJPQL = "SELECT c FROM Categoria c";
		Query query = this.em.createQuery(sentenciJPQL);
		List<Categoria> categories = (List<Categoria>)query.getResultList();

		return categories;
	}

	public Categoria getByID(int categoryID) {
		return em.find(Categoria.class, categoryID);
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

		return categoriasTotal;
	}
	

	public CategoriaTotalDTO getSummarizedByID(int categoryID, LocalDate from, LocalDate to) {
		for (CategoriaTotalDTO category : getAllSummarized(from,to)) {
			if (category.getId() == categoryID) {
				return category;
			}
		}
		return null;
	}
	
	protected double getTotal(List<? extends Movimiento> movements) {
		double total = 0;
		for (Movimiento movement : movements) {
			total += movement.getValue();
		}
		return total;
	}
}
