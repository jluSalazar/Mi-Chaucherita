package model.entidades;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "CategoriaTransferencia")
public class CategoriaTransferencia extends Categoria implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private  List<CategoriaTransferencia> transferCategories;

	public CategoriaTransferencia() {
	}

	public  List<CategoriaTransferencia> getTransferCategories() {
		return transferCategories;
	}

	public  void setTransferCategories(List<CategoriaTransferencia> transferCategories) {
		this.transferCategories = transferCategories;
	}
	

}
