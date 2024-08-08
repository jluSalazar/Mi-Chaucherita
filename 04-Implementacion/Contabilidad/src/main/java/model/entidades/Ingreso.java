package model.entidades;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Ingreso")
public class Ingreso extends Movimiento implements Serializable {

	private static final long serialVersionUID = 1L;

	@ManyToOne
    @JoinColumn(name = "destination_id")
	private Cuenta destination;
	@ManyToOne
    @JoinColumn(name = "category_id")
	private CategoriaIngreso category;

	private List<Ingreso> incomeMovements;

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


	public List<Ingreso> getIncomeMovements() {
		return incomeMovements;
	}

	public void setIncomeMovements(List<Ingreso> incomeMovements) {
		this.incomeMovements = incomeMovements;
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
