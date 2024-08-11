package model.entidades;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@DiscriminatorValue("TRANSFERENCIA")
public class Transferencia extends Movimiento implements Serializable {

    private static final long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(name = "source_id")
    private Cuenta source;

    @ManyToOne
    @JoinColumn(name = "destination_id")
    private Cuenta destination;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoriaTransferencia category;
    
	
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

}
