package model.entidades;

import java.io.Serializable;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("CategoriaEgreso")
public class CategoriaEgreso extends Categoria implements Serializable{

	private static final long serialVersionUID = 1L;
	
	
	public CategoriaEgreso() {}

}
