package model.entidades;

import java.io.Serializable;
import java.util.List;

public class Categoria implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;
	private String name;

	private List<Categoria> categories;

	public Categoria() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public List<Categoria> getCategories() {
		return categories;
	}

	public void setCategories(List<Categoria> categories) {
		this.categories = categories;
	}

	@Override
	public String toString() {
		return "Categoria [id=" + id + ", name=" + name + "]";
	}

}
