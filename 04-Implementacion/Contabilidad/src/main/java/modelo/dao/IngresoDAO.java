package modelo.dao;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import model.entidades.Ingreso;
import model.entidades.Movimiento;

public class IngresoDAO {
	
	public List<Ingreso> getAll() {
		CuentaDAO cuentaDAO = new CuentaDAO();
		CategoriaIngresoDAO categoriaIngresoDAO = new CategoriaIngresoDAO();
		
		List<Ingreso> incomeMovements = new ArrayList<>();
			Ingreso income1 = new Ingreso();
			income1.setId(6);
			income1.setDescription("Venta de producto A");
			income1.setValue(500.0);
			income1.setDate(LocalDate.of(2024, 7, 10));
			income1.setHour(LocalTime.of(15, 30));
			income1.setDestination(cuentaDAO.getByID(2));
			income1.setCategory(categoriaIngresoDAO.getByID(6));
			incomeMovements.add(income1);

			Ingreso income2 = new Ingreso();
			income2.setId(7);
			income2.setDescription("Ingreso por servicio de consultoría");
			income2.setValue(800.0);
			income2.setDate(LocalDate.of(2024, 7, 12));
			income2.setHour(LocalTime.of(9, 00));
			income2.setDestination(cuentaDAO.getByID(1));
			income2.setCategory(categoriaIngresoDAO.getByID(7));
			incomeMovements.add(income2);

			Ingreso income3 = new Ingreso();
			income3.setId(8);
			income3.setDescription("Pago de suscripción anual");
			income3.setValue(1200.0);
			income3.setDate(LocalDate.of(2024, 6, 15));
			income3.setHour(LocalTime.of(13, 45));
			income3.setDestination(cuentaDAO.getByID(2));
			income3.setCategory(categoriaIngresoDAO.getByID(8));
			incomeMovements.add(income3);

			Ingreso income4 = new Ingreso();
			income4.setId(9);
			income4.setDescription("Intereses de inversión");
			income4.setValue(200.0);
			income4.setDate(LocalDate.of(2024, 6, 18));
			income4.setHour(LocalTime.of(16, 30));
			income4.setDestination(cuentaDAO.getByID(2));
			income4.setCategory(categoriaIngresoDAO.getByID(8));
			incomeMovements.add(income4);

			Ingreso income5 = new Ingreso();
			income5.setId(10);
			income5.setDescription("Reembolso de gastos");
			income5.setValue(150.0);
			income5.setDate(LocalDate.of(2024, 7, 22));
			income5.setHour(LocalTime.of(10, 00));
			income5.setDestination(cuentaDAO.getByID(1));
			income5.setCategory(categoriaIngresoDAO.getByID(6));
			incomeMovements.add(income5);

		return incomeMovements;

	}

	public List<Ingreso> getAllByDate(LocalDate from, LocalDate to) {
		List<Ingreso> incomeMovementsByDate = new ArrayList<>();
		LocalDate date;
		for (Ingreso income : this.getAll()) {
			date = income.getDate();
			if (!date.isBefore(from) && !date.isAfter(to)) {
				incomeMovementsByDate.add(income);
			}
		}
		return incomeMovementsByDate;
	}

	public List<Ingreso> getAllByAccount(int accountID) {
		List<Ingreso> incomeMovementsByAccount = new ArrayList<>();
		for (Ingreso income : this.getAll()) {
			if (income.getDestination().getId() == accountID) {
				incomeMovementsByAccount.add(income);
			}
		}
		return incomeMovementsByAccount;
	}

	public List<Ingreso> getAllByCategory(int categoryID) {
		List<Ingreso> incomeMovementsByCategory = new ArrayList<>();
		for (Ingreso income : this.getAll()) {
			if (income.getCategory().getId() == categoryID) {
				incomeMovementsByCategory.add(income);
			}
		}
		return incomeMovementsByCategory;
	}

	public Ingreso getByID(int movementID) {
		for (Ingreso income : this.getAll()) {
			if (income.getId() == movementID) {
				return income;
			}
		}
		return null;
	}

	public boolean newIncome(Ingreso income) {
		MovimientoDAO movimientoDAO = new MovimientoDAO();
		
		int maxID = 0;
		for (Movimiento movement : movimientoDAO.getAll()) {
			if (movement.getId() > maxID) {
				maxID = movement.getId();
			}
		}

	    maxID += 1;
		income.setId(maxID);
		boolean flag = this.getAll().add(income);
		return flag;
	}

	public boolean updateIncome(Ingreso updatedIncome) {
		for (Ingreso ingreso : this.getAll()) {
			if (ingreso.getId() == updatedIncome.getId()) {
					ingreso.setDescription(updatedIncome.getDescription());
					ingreso.setValue(updatedIncome.getValue());
					ingreso.setDate(updatedIncome.getDate());
					ingreso.setHour(updatedIncome.getHour());
					ingreso.setDestination(updatedIncome.getDestination());
					ingreso.setCategory(updatedIncome.getCategory());
					return true;
			}
		}
		return false;
	}

	public boolean deleteIncome(int movementID) {
		Ingreso income = getByID(movementID);
		List<Ingreso> incomeMovements = this.getAll();

		if (income != null) {
			incomeMovements.remove(income);
			return true;
		}
		return false;
	}
}
