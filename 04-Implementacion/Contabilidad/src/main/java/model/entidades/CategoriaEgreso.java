package model.entidades;

import java.io.Serializable;
import java.util.List;


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
