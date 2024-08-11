package model.entidades;

import java.io.Serializable;
/*
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
*/
//@Entity
//@Table(name = "CategoriaTotalDTO")
public class CategoriaTotalDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    //@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    //@Column
    private String name;
    //@Column
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
