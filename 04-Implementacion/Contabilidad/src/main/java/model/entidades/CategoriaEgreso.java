package model.entidades;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "CategoriaEgreso")
public class CategoriaEgreso extends Categoria implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private  List<CategoriaEgreso> expenseCategories;
	
	public CategoriaEgreso() {}
	

	public  List<CategoriaEgreso> getExpenseCategories() {
		return expenseCategories;
	}

	public  void setExpenseCategories(List<CategoriaEgreso> expenseCategories) {
		this.expenseCategories = expenseCategories;
	}



	

}
