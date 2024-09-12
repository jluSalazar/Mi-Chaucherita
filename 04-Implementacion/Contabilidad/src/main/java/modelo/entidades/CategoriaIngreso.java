package modelo.entidades;

import java.io.Serializable;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("CategoriaIngreso")
public class CategoriaIngreso extends Categoria implements Serializable {

	private static final long serialVersionUID = 1L;
	
	
	public CategoriaIngreso() {}
	
}
