package model.entidades;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "CategoriaIngreso")
public class CategoriaIngreso extends Categoria implements Serializable {

	private static final long serialVersionUID = 1L;
	
	//private List<CategoriaIngreso> incomeCategories;
	
	public CategoriaIngreso() {}
/*
	public List<CategoriaIngreso> getIncomeCategories() {
		return incomeCategories;
	}

	public void setIncomeCategories(List<CategoriaIngreso> incomeCategories) {
		this.incomeCategories = incomeCategories;
	}
	
	
*/	
	
}
