package modelo.entidades;

import java.io.Serializable;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
@DiscriminatorValue("EGRESO")
public class Egreso extends Movimiento implements Serializable {

    private static final long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(name = "source_id")
    private Cuenta source;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoriaEgreso category;
	
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


	public Cuenta getDestination() {
		// TODO Auto-generated method stub
		return null;
	}

	
}
