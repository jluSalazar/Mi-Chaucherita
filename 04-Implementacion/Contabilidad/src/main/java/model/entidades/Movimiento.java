package model.entidades;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;


public class Movimiento implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;
	private String description;
	private double value;
	private LocalDate date;
	private LocalTime hour;

	private List<Movimiento> movements;

	public Movimiento() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public LocalTime getHour() {
		return hour;
	}

	public void setHour(LocalTime hour) {
		this.hour = hour;
	}

	public List<Movimiento> getMovements() {
		return movements;
	}

	public void setMovements(List<Movimiento> movements) {
		this.movements = movements;
	}

	

}
