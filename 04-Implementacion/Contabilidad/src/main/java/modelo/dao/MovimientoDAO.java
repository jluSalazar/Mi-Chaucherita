package modelo.dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import model.entidades.Egreso;
import model.entidades.Ingreso;
import model.entidades.Movimiento;
import model.entidades.Transferencia;

public class MovimientoDAO {
	
	public List<? extends Movimiento> getAll() {
		Movimiento movimiento = new Movimiento();
		IngresoDAO ingresoDAO = new IngresoDAO();
		EgresoDAO egresoDAO = new EgresoDAO();
		TransferenciaDAO transferenciaDAO = new TransferenciaDAO();
		
		List<Movimiento> movements = movimiento.getMovements();
		if (movements == null) {
			movements = new ArrayList<>();
			List<Ingreso> incomeMovements = ingresoDAO.getAll();
			List<Egreso> expenseMovements = egresoDAO.getAll();
			List<Transferencia> transferMovements = transferenciaDAO.getAll();

			movements.addAll(incomeMovements);
			movements.addAll(expenseMovements);
			movements.addAll(transferMovements);
		}

		return movements;
	}
	
	public List<? extends Movimiento> getAllByDate(LocalDate from, LocalDate to) {
		List<Movimiento> movementsByDate = new ArrayList<>();
		LocalDate date;
		for (Movimiento movement : getAll()) {
			date = movement.getDate();
			if ((date.isEqual(from) || date.isAfter(from)) && (date.isEqual(to) || date.isBefore(to))) {
				movementsByDate.add(movement);
				
			}
		}
		
		return sortMovementsByDate(movementsByDate);
	}

	public List<? extends Movimiento> getAllByAccount(int accountID) {
		IngresoDAO ingresoDAO = new IngresoDAO();
		EgresoDAO egresoDAO = new EgresoDAO();
		TransferenciaDAO transferenciaDAO = new TransferenciaDAO();
		
		List<Movimiento> movementsByAccount = new ArrayList<>();
		
		List<Ingreso> incomeMovements = ingresoDAO.getAllByAccount(accountID);
		List<Egreso> expenseMovements = egresoDAO.getAllByAccount(accountID);
		List<Transferencia> transferMovements = transferenciaDAO.getAllByAccount(accountID);
		
		movementsByAccount.addAll(incomeMovements);
		movementsByAccount.addAll(expenseMovements);
		movementsByAccount.addAll(transferMovements);
		
		return sortMovementsByDate(movementsByAccount);
	}

	public List<? extends Movimiento> getAllByCategory(int categoryID) {
		IngresoDAO ingresoDAO = new IngresoDAO();
		EgresoDAO egresoDAO = new EgresoDAO();
		TransferenciaDAO transferenciaDAO = new TransferenciaDAO();
		
		List<Movimiento> movementsByCategory = new ArrayList<>();
		
		List<Ingreso> incomeMovements = ingresoDAO.getAllByCategory(categoryID);
		List<Egreso> expenseMovements = egresoDAO.getAllByCategory(categoryID);
		List<Transferencia> transferMovements = transferenciaDAO.getAllByCategory(categoryID);
		
		movementsByCategory.addAll(incomeMovements);
		movementsByCategory.addAll(expenseMovements);
		movementsByCategory.addAll(transferMovements);
		
		return sortMovementsByDate(movementsByCategory);
	}

	public Movimiento getByID(int movementID) {
		for (Movimiento movimiento : getAll()) {
			if(movimiento.getId() == movementID) {
				return movimiento;
			}
		}
		return null;
	}
	
	public void updateMovement(Movimiento movement) {
		Movimiento movementUpdated = getByID(movement.getId());
		movementUpdated.setDescription(movement.getDescription());
		movementUpdated.setValue(movement.getValue());
		movementUpdated.setDate(movement.getDate());
		movementUpdated.setHour(movement.getHour());
	}
	
	public void deleteMovement(Movimiento movement) {
		Movimiento movimiento = new Movimiento();
		
		List<Movimiento> movements = movimiento.getMovements();
		movements.remove(movement);
	}
	
	/* Metodos que no estan en el diseño*/
	public List<Movimiento> sortMovementsByDate(List<Movimiento> auxMovements){
		auxMovements.sort(Comparator.comparing(Movimiento::getDate).reversed());
		return auxMovements;
	}
	
	public List<Movimiento> filterMovementByDate(List<Movimiento> allMovements, LocalDate from, LocalDate to){
		List<Movimiento> filteredMovements = new ArrayList<>();
		for (Movimiento movimiento : allMovements) {
			LocalDate date = movimiento.getDate();
			if (!date.isBefore(from) && !date.isAfter(to)) {
				filteredMovements.add(movimiento);
			}
		}
		return filteredMovements;
	}

}
