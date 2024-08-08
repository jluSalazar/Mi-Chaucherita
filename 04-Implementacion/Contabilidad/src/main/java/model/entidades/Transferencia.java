package model.entidades;

import java.io.Serializable;
import java.util.List;

public class Transferencia extends Movimiento implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Cuenta source;
	private Cuenta destination;
	private CategoriaTransferencia category;
	
	private List<Transferencia> transferMovements;
	
	public Transferencia() {}

	public Cuenta getSource() {
		return source;
	}

	public void setSource(Cuenta source) {
		this.source = source;
	}

	public Cuenta getDestination() {
		return destination;
	}

	public void setDestination(Cuenta destination) {
		this.destination = destination;
	}

	public CategoriaTransferencia getCategory() {
		return category;
	}

	public void setCategory(CategoriaTransferencia category) {
		this.category = category;
	}

	public List<Transferencia> getTransferMovements() {
		return transferMovements;
	}

	public void setTransferMovements(List<Transferencia> transferMovements) {
		this.transferMovements = transferMovements;
	}
	
	

}
