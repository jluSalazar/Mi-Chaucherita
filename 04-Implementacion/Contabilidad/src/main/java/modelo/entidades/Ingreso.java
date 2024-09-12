package modelo.entidades;

import java.io.Serializable;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;


@Entity
@DiscriminatorValue("INGRESO")
public class Ingreso extends Movimiento implements Serializable {

    private static final long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(name = "destination_id")
    private Cuenta destination;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoriaIngreso category;

	public Ingreso() {
	}

	public Cuenta getDestination() {
		return destination;
	}

	public void setDestination(Cuenta source) {
		this.destination = source;
	}

	public CategoriaIngreso getCategory() {
		return category;
	}

	public void setCategory(CategoriaIngreso category) {
		this.category = category;
	}

	public Cuenta getSource() {
		return null;
	}
	
	@Override
	public String toString() {
        return "Ingreso{" +
        		"id="+getId()+
                "description='" + getDescription() + '\'' +
                ", value=" + getValue() +
                ", date=" + getDate() +
                ", hour=" + getHour() +
                ", destination=" + getDestination().getName() +
                ", category=" + getCategory().getName() +
                '}';
    }

}