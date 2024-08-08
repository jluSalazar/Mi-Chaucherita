package modelo.dao;

import java.util.ArrayList;
import java.util.List;

import model.entidades.CategoriaTransferencia;

public class CategoriaTransferenciaDAO {
	// **************** Metodos de Negocio *************//
	public List<CategoriaTransferencia> getAll() {
		CategoriaTransferencia categoriaTransferencia = new CategoriaTransferencia();
		List<CategoriaTransferencia> transferCategories = categoriaTransferencia.getTransferCategories();
		if (transferCategories == null) {
			transferCategories = new ArrayList<>();
			CategoriaTransferencia cat1 = new CategoriaTransferencia();
			cat1.setId(11);
			cat1.setName("Transferencia Entre Cuentas");

			transferCategories.add(cat1);
		}
		return transferCategories;
	}

	public CategoriaTransferencia getByID(int categoryID) {
		for (CategoriaTransferencia categoria : getAll()) {
			if (categoria.getId() == categoryID) {
				return categoria;
			}
		}
		return null;
	}
}
