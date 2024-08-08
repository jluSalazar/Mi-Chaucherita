package modelo.dao;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import model.entidades.Movimiento;
import model.entidades.Transferencia;

public class TransferenciaDAO {
	
	public List<Transferencia> getAll(){
		Transferencia transferencia = new Transferencia();
		CuentaDAO cuentaDAO = new CuentaDAO();
		CategoriaTransferenciaDAO categoriaTransferenciaDAO = new CategoriaTransferenciaDAO();
		
		List<Transferencia> transferMovements = transferencia.getTransferMovements();
		if (transferMovements == null) {
		    transferMovements = new ArrayList<>();
		    
		    Transferencia transferencia1 = new Transferencia();
		    transferencia1.setId(11);
		    transferencia1.setDescription("Pago de electricidad");
		    transferencia1.setValue(100.00);
		    transferencia1.setDate(LocalDate.of(2024, 7, 1));
		    transferencia1.setHour(LocalTime.of(10, 30));
		    transferencia1.setSource(cuentaDAO.getByID(1));
		    transferencia1.setDestination(cuentaDAO.getByID(2));
		    transferencia1.setCategory(categoriaTransferenciaDAO.getByID(11));
		    transferMovements.add(transferencia1);

		    Transferencia transferencia2 = new Transferencia();
		    transferencia2.setId(12);
		    transferencia2.setDescription("Transferencia interna");
		    transferencia2.setValue(500.00);
		    transferencia2.setDate(LocalDate.of(2024, 7, 2));
		    transferencia2.setHour(LocalTime.of(14, 45));
		    transferencia2.setSource(cuentaDAO.getByID(1));
		    transferencia2.setDestination(cuentaDAO.getByID(2));
		    transferencia2.setCategory(categoriaTransferenciaDAO.getByID(11));
		    transferMovements.add(transferencia2);

		    Transferencia transferencia3 = new Transferencia();
		    transferencia3.setId(13);
		    transferencia3.setDescription("Pago a proveedor X");
		    transferencia3.setValue(2000.00);
		    transferencia3.setDate(LocalDate.of(2024, 7, 3));
		    transferencia3.setHour(LocalTime.of(16, 15));
		    transferencia3.setSource(cuentaDAO.getByID(1));
		    transferencia3.setDestination(cuentaDAO.getByID(2));
		    transferencia3.setCategory(categoriaTransferenciaDAO.getByID(11));
		    transferMovements.add(transferencia3);

		    Transferencia transferencia4 = new Transferencia();
		    transferencia4.setId(14);
		    transferencia4.setDescription("Reintegro de préstamo");
		    transferencia4.setValue(1500.00);
		    transferencia4.setDate(LocalDate.of(2024, 7, 4));
		    transferencia4.setHour(LocalTime.of(9, 00));
		    transferencia4.setSource(cuentaDAO.getByID(1));
		    transferencia4.setDestination(cuentaDAO.getByID(2));
		    transferencia4.setCategory(categoriaTransferenciaDAO.getByID(11));
		    transferMovements.add(transferencia4);

		    Transferencia transferencia5 = new Transferencia();
		    transferencia5.setId(15);
		    transferencia5.setDescription("Pago de nómina");
		    transferencia5.setValue(3000.00);
		    transferencia5.setDate(LocalDate.of(2024, 7, 5));
		    transferencia5.setHour(LocalTime.of(12, 00));
		    transferencia5.setSource(cuentaDAO.getByID(2));
		    transferencia5.setDestination(cuentaDAO.getByID(1));
		    transferencia5.setCategory(categoriaTransferenciaDAO.getByID(11));
		    transferMovements.add(transferencia5);
		}
		return transferMovements;
	}
	public List<Transferencia> getAllByDate(LocalDate from, LocalDate to){
		List<Transferencia> transferMovementsByDate = new ArrayList<>();
		LocalDate date;
		for (Transferencia transfer : getAll()) {
			date = transfer.getDate();
			if (!date.isBefore(from) && !date.isAfter(to)) {
            	transferMovementsByDate.add(transfer);
            }
		}
		return transferMovementsByDate;
	}
	public List<Transferencia> getAllByAccount(int accountID){
		List<Transferencia> transferMovementsByAccount = new ArrayList<>();
		for (Transferencia transfer : getAll()) {
			if(transfer.getSource().getId() == accountID || transfer.getDestination().getId() == accountID) {
				transferMovementsByAccount.add(transfer);
			}
		}
		return transferMovementsByAccount;
	}
	public List<Transferencia> getAllByCategory(int categoryID){
		List<Transferencia> transferMovementsByCategory = new ArrayList<>();
		for (Transferencia transfer : getAll()) {
			if(transfer.getCategory().getId() == categoryID) {
				transferMovementsByCategory.add(transfer);
			}
		}
		return transferMovementsByCategory;
	}
	public Transferencia getByID(int movementID){
		for (Transferencia transfer : getAll()) {
			if(transfer.getId() == movementID) {
				return transfer;
			}
		}
		return null;
	}
		
	public boolean newTransfer(Transferencia transfer) {
		MovimientoDAO movimientoDAO = new MovimientoDAO();
		
	    int maxID = 0;
	    for (Movimiento movement : movimientoDAO.getAll()) {
	        if (movement.getId() > maxID) {
	            maxID = movement.getId();
	        }
	    }
	    
	    maxID += 1;
	    transfer.setId(maxID);
	    boolean flag = getAll().add(transfer);
	    return flag;
	}
	
	public boolean updateTransfer(Transferencia updatedTransfer) {
	    for (Transferencia transfer : getAll()) {
	        if (transfer.getId() == updatedTransfer.getId()) {
	            transfer.setDescription(updatedTransfer.getDescription());
	            transfer.setValue(updatedTransfer.getValue());
	            transfer.setDate(updatedTransfer.getDate());
	            transfer.setHour(updatedTransfer.getHour());
	            transfer.setSource(updatedTransfer.getSource());
	            transfer.setDestination(updatedTransfer.getDestination());
	            transfer.setCategory(updatedTransfer.getCategory());
	            return true;
	        }
	    }
	    return false;
	}
	
	public boolean deleteTransfer(int movementID) {
		
	    Transferencia transfer = getByID(movementID);
		List<Transferencia> transferMovements = this.getAll();

	    if (transfer != null) {
	        transferMovements.remove(transfer);
	        return true;
	    }
	    return false;
	}
}
