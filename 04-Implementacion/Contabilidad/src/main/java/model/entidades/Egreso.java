package model.entidades;

import java.io.Serializable;
import java.util.List;


public class Egreso extends Movimiento implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Cuenta source;
	private CategoriaEgreso category;
	
	private List<Egreso> expenseMovements;
	
	public Egreso() {}

	
	public Cuenta getSource() {
		return source;
	}

	public void setSource(Cuenta source) {
		this.source = source;
	}

	public CategoriaEgreso getCategory() {
		return category;
	}

	public void setCategory(CategoriaEgreso category) {
		this.category = category;
	}
	
	public List<Egreso> getExpenseMovements() {
		return expenseMovements;
	}


	public void setExpenseMovements(List<Egreso> expenseMovements) {
		this.expenseMovements = expenseMovements;
	}


	public Cuenta getDestination() {
		// TODO Auto-generated method stub
		return null;
	}

	
}
