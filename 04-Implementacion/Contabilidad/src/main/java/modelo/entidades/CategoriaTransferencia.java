package modelo.entidades;

import java.io.Serializable;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("CategoriaTransferencia")
public class CategoriaTransferencia extends Categoria implements Serializable {
	
	private static final long serialVersionUID = 1L;


	public CategoriaTransferencia() {
	}

}
