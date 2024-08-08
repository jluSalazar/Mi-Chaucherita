package model.entidades;

import java.io.Serializable;

public class CategoriaTotalDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String name;
    private double total;

    public CategoriaTotalDTO() {}

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

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

	@Override
	public String toString() {
		return "CategoriaTotalDTO [id=" + id + ", name=" + name + ", total=" + total + "]";
	}
    
    
}
