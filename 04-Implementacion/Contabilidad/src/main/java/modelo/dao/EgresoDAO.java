package modelo.dao;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import model.entidades.Egreso;
import model.entidades.Movimiento;

public class EgresoDAO {
	public List<Egreso> getAll(){
		Egreso egreso = new Egreso();
		CuentaDAO cuentaDAO = new CuentaDAO();
		CategoriaEgresoDAO categoriaEgresoDAO = new CategoriaEgresoDAO();
		
		List<Egreso> expenseMovements = egreso.getExpenseMovements();
		if(expenseMovements == null) {
		    expenseMovements = new ArrayList<>();

			Egreso expense1 = new Egreso();
			expense1.setId(1);
			expense1.setDescription("Compra de materiales de oficina");
			expense1.setValue(150.0);
			expense1.setDate(LocalDate.of(2024, 7, 15));
			expense1.setHour(LocalTime.of(9, 30));
			expense1.setSource(cuentaDAO.getByID(1));
			expense1.setCategory(categoriaEgresoDAO.getByID(1));
			expenseMovements.add(expense1);
	
			Egreso expense2 = new Egreso();
			expense2.setId(2);
			expense2.setDescription("Pago de servicios de Internet");
			expense2.setValue(75.0);
			expense2.setDate(LocalDate.of(2024, 7, 16));
			expense2.setHour(LocalTime.of(14, 00));
			expense2.setSource(cuentaDAO.getByID(2));
			expense2.setCategory(categoriaEgresoDAO.getByID(2));
			expenseMovements.add(expense2);
	
			Egreso expense3 = new Egreso();
			expense3.setId(3);
			expense3.setDescription("Compra de equipo de cómputo");
			expense3.setValue(1200.0);
			expense3.setDate(LocalDate.of(2024, 7, 20));
			expense3.setHour(LocalTime.of(11, 15));
			expense3.setSource(cuentaDAO.getByID(2));
			expense3.setCategory(categoriaEgresoDAO.getByID(3));
			expenseMovements.add(expense3);
	
			Egreso expense4 = new Egreso();
			expense4.setId(4);
			expense4.setDescription("Reparación de aire acondicionado");
			expense4.setValue(250.0);
			expense4.setDate(LocalDate.of(2024, 7, 22));
			expense4.setHour(LocalTime.of(16, 45));
			expense4.setSource(cuentaDAO.getByID(1));
			expense4.setCategory(categoriaEgresoDAO.getByID(2));
			expenseMovements.add(expense4);
	
			Egreso expense5 = new Egreso();
			expense5.setId(5);
			expense5.setDescription("Compra de artículos de limpieza");
			expense5.setValue(60.0);
			expense5.setDate(LocalDate.of(2024, 8, 2));
			expense5.setHour(LocalTime.of(10, 00));
			expense5.setSource(cuentaDAO.getByID(1));
			expense5.setCategory(categoriaEgresoDAO.getByID(5));
			expenseMovements.add(expense5);
		}
		return expenseMovements;
		
	}
	
	public List<Egreso> getAllByDate(LocalDate from, LocalDate to) {
		List<Egreso> expenseMovementsByDate = new ArrayList<>();
		LocalDate date;
		for (Egreso expense : getAll()) {
			date = expense.getDate();
			if (!date.isBefore(from) && !date.isAfter(to)) {
				expenseMovementsByDate.add(expense);
            }
		}
		return expenseMovementsByDate;
	}

	public List<Egreso> getAllByAccount(int accountID) {
		List<Egreso> expenseMovementsByAccount = new ArrayList<>();
		for (Egreso expense : getAll()) {
            if (expense.getSource().getId() == accountID) {
				expenseMovementsByAccount.add(expense);
            }
		}
		return expenseMovementsByAccount;
	}

	public List<Egreso> getAllByCategory(int categoryID) {
		List<Egreso> expenseMovementsByAccount = new ArrayList<>();
		for (Egreso expense : getAll()) {
            if (expense.getCategory().getId() == categoryID) {
				expenseMovementsByAccount.add(expense);
            }
		}
		return expenseMovementsByAccount;
	}

	public Egreso getByID(int movementID) {
		for (Egreso expense : getAll()) {
			if(expense.getId() == movementID) {
				return expense;
			}
		}
		return null;
	}
	
	public boolean newExpense(Egreso expense) {
		MovimientoDAO movimientoDAO = new MovimientoDAO();
		
	    int maxID = 0;
	    for (Movimiento movement : movimientoDAO.getAll()) {
	        if (movement.getId() > maxID) {
	            maxID = movement.getId();
	        }
	    }
	    maxID += 1;
	    expense.setId(maxID);
	    boolean flag = getAll().add(expense);
	    return flag;
	}

	public boolean updateExpense(Egreso updatedExpense) {
	    for (Egreso expense : getAll()) {
	        if (expense.getId() == updatedExpense.getId()) {
	            expense.setDescription(updatedExpense.getDescription());
	            expense.setValue(updatedExpense.getValue());
	            expense.setDate(updatedExpense.getDate());
	            expense.setHour(updatedExpense.getHour());
	            expense.setSource(updatedExpense.getSource());  
	            expense.setCategory(updatedExpense.getCategory());
	            return true;
	        }
	    }
	    return false;
	}

	public boolean deleteExpense(int movementID) {
		
		List<Egreso> expenseMovements = this.getAll();
	    Egreso expense = getByID(movementID);
	    if (expense != null) {
	        expenseMovements.remove(expense);
	        return true;
	    }
	    return false;
	}
}
