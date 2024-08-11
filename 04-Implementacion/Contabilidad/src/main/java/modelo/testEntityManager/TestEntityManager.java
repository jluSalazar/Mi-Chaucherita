package modelo.testEntityManager;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import model.entidades.CategoriaEgreso;
import model.entidades.CategoriaIngreso;
import model.entidades.CategoriaTransferencia;
import model.entidades.Cuenta;
import model.entidades.Egreso;
import model.entidades.Ingreso;
import model.entidades.Movimiento;
import model.entidades.Transferencia;
import modelo.dao.CategoriaEgresoDAO;
import modelo.dao.CategoriaIngresoDAO;
import modelo.dao.CategoriaTransferenciaDAO;
import modelo.dao.CuentaDAO;
import modelo.dao.EgresoDAO;
import modelo.dao.IngresoDAO;
import modelo.dao.MovimientoDAO;
import modelo.dao.TransferenciaDAO;

public class TestEntityManager {

	public static void main(String[] args) {

		EntityManager em = Persistence.createEntityManagerFactory("ContabilidadMySQL").createEntityManager();
/*
		// Guardar CATEGORIAS

		try {
			em.getTransaction().begin();
			// INGRESO
			CategoriaIngreso cat1 = new CategoriaIngreso();
			cat1.setName("Nomina");
			CategoriaIngreso cat2 = new CategoriaIngreso();
			cat2.setName("Inversiones");
			CategoriaIngreso cat3 = new CategoriaIngreso();
			cat3.setName("Regalos");
			CategoriaIngreso cat4 = new CategoriaIngreso();
			cat4.setName("Ventas");
			CategoriaIngreso cat5 = new CategoriaIngreso();
			cat5.setName("Otros");

			em.persist(cat1);
			em.persist(cat2);
			em.persist(cat3);
			em.persist(cat4);
			em.persist(cat5);

			// EGRESO
			CategoriaEgreso cat6 = new CategoriaEgreso();
			cat6.setName("Alimentación");
			CategoriaEgreso cat7 = new CategoriaEgreso();
			cat7.setName("Transporte");
			CategoriaEgreso cat8 = new CategoriaEgreso();
			cat8.setName("Entretenimiento");
			CategoriaEgreso cat9 = new CategoriaEgreso();
			cat9.setName("Salud");
			CategoriaEgreso cat10 = new CategoriaEgreso();
			cat10.setName("Libros");

			em.persist(cat6);
			em.persist(cat7);
			em.persist(cat8);
			em.persist(cat9);
			em.persist(cat10);

			// TRANSFERENCIA
			CategoriaTransferencia cat11 = new CategoriaTransferencia();
			cat11.setName("Transferencia entre Cuentas");

			em.persist(cat11);

			em.getTransaction().commit();
		} catch (RuntimeException e) {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			throw e; // o maneja la excepción según sea necesario
		}

		// GUARDAR CUENTAS
		em.getTransaction().begin();

		// Crear y configurar cuentas
		Cuenta cuenta1 = new Cuenta();
		cuenta1.setName("Efectivo");
		cuenta1.setTotal(1000.00);

		Cuenta cuenta2 = new Cuenta();
		cuenta2.setName("Banco Pichincha");
		cuenta2.setTotal(2000.00);

		Cuenta cuenta3 = new Cuenta();
		cuenta3.setName("Ahorros");
		cuenta3.setTotal(1500.00);

		Cuenta cuenta4 = new Cuenta();
		cuenta4.setName("Cuenta Corriente");
		cuenta4.setTotal(2500.00);

		Cuenta cuenta5 = new Cuenta();
		cuenta5.setName("Cuenta Tesoreria");
		cuenta5.setTotal(3000.00);

		// Persistir cuentas
		em.persist(cuenta1);
		em.persist(cuenta2);
		em.persist(cuenta3);
		em.persist(cuenta4);
		em.persist(cuenta5);

		// Confirmar transacción
		em.getTransaction().commit();

		// GUARDAR MOVIMIENTOS
		// Crear DAO instances
		CuentaDAO cuentaDAO = new CuentaDAO();
		CategoriaIngresoDAO categoriaIngresoDAO = new CategoriaIngresoDAO();
		CategoriaEgresoDAO categoriaEgresoDAO = new CategoriaEgresoDAO();
		CategoriaTransferenciaDAO categoriaTransferenciaDAO = new CategoriaTransferenciaDAO();

		// Iniciar transacción
		em.getTransaction().begin();

		// Crear listas para los movimientos
		List<Ingreso> incomeMovements = new ArrayList<>();
		List<Egreso> expenseMovements = new ArrayList<>();
		List<Transferencia> transferMovements = new ArrayList<>();

		// Crear objetos de Ingreso
		Ingreso income1 = new Ingreso();
		income1.setDescription("Venta de productos");
		income1.setValue(500.0);
		income1.setDate(LocalDate.of(2024, 7, 10));
		income1.setHour(LocalTime.of(15, 30));
		income1.setDestination(cuentaDAO.getByID(2));
		income1.setCategory(categoriaIngresoDAO.getByID(6));
		incomeMovements.add(income1);

		Ingreso income2 = new Ingreso();
		income2.setDescription("Intereses de inversión");
		income2.setValue(150.0);
		income2.setDate(LocalDate.of(2024, 7, 12));
		income2.setHour(LocalTime.of(14, 0));
		income2.setDestination(cuentaDAO.getByID(3));
		income2.setCategory(categoriaIngresoDAO.getByID(8));
		incomeMovements.add(income2);

		Ingreso income3 = new Ingreso();
		income3.setDescription("Devolución de factura");
		income3.setValue(75.0);
		income3.setDate(LocalDate.of(2024, 7, 14));
		income3.setHour(LocalTime.of(16, 45));
		income3.setDestination(cuentaDAO.getByID(2));
		income3.setCategory(categoriaIngresoDAO.getByID(7));
		incomeMovements.add(income3);

		Ingreso income4 = new Ingreso();
		income4.setDescription("Ganancias por ventas");
		income4.setValue(1000.0);
		income4.setDate(LocalDate.of(2024, 7, 18));
		income4.setHour(LocalTime.of(11, 0));
		income4.setDestination(cuentaDAO.getByID(1));
		income4.setCategory(categoriaIngresoDAO.getByID(10));
		incomeMovements.add(income4);

		Ingreso income5 = new Ingreso();
		income5.setDescription("Cobro de alquiler");
		income5.setValue(600.0);
		income5.setDate(LocalDate.of(2024, 7, 20));
		income5.setHour(LocalTime.of(9, 0));
		income5.setDestination(cuentaDAO.getByID(4));
		income5.setCategory(categoriaIngresoDAO.getByID(9));
		incomeMovements.add(income5);

		Ingreso income6 = new Ingreso();
		income6.setDescription("Pago de Nomina");
		income6.setValue(600.0);
		income6.setDate(LocalDate.of(2024, 7, 20));
		income6.setHour(LocalTime.of(9, 0));
		income6.setDestination(cuentaDAO.getByID(3));
		income6.setCategory(categoriaIngresoDAO.getByID(9));
		incomeMovements.add(income6);

		// Agregar más ingresos según el patrón
		// ...

		// Crear objetos de Egreso
		Egreso expense1 = new Egreso();
		expense1.setDescription("Compra de materiales de oficina");
		expense1.setValue(2000.0);
		expense1.setDate(LocalDate.of(2024, 8, 2));
		expense1.setHour(LocalTime.of(8, 30));
		expense1.setSource(cuentaDAO.getByID(1));
		expense1.setCategory(categoriaEgresoDAO.getByID(1));
		expenseMovements.add(expense1);

		Egreso expense2 = new Egreso();
		expense2.setDescription("Pago de suscripción de software");
		expense2.setValue(200.0);
		expense2.setDate(LocalDate.of(2024, 7, 17));
		expense2.setHour(LocalTime.of(13, 15));
		expense2.setSource(cuentaDAO.getByID(2));
		expense2.setCategory(categoriaEgresoDAO.getByID(4));
		expenseMovements.add(expense2);

		Egreso expense3 = new Egreso();
		expense3.setDescription("Compra de mobiliario para oficina");
		expense3.setValue(750.0);
		expense3.setDate(LocalDate.of(2024, 7, 21));
		expense3.setHour(LocalTime.of(15, 0));
		expense3.setSource(cuentaDAO.getByID(3));
		expense3.setCategory(categoriaEgresoDAO.getByID(5));
		expenseMovements.add(expense3);

		Egreso expense4 = new Egreso();
		expense4.setDescription("Gastos de viaje de negocios");
		expense4.setValue(400.0);
		expense4.setDate(LocalDate.of(2024, 7, 25));
		expense4.setHour(LocalTime.of(10, 30));
		expense4.setSource(cuentaDAO.getByID(4));
		expense4.setCategory(categoriaEgresoDAO.getByID(2));
		expenseMovements.add(expense4);

		Egreso expense5 = new Egreso();
		expense5.setDescription("Compra de libros de referencia");
		expense5.setValue(120.0);
		expense5.setDate(LocalDate.of(2024, 7, 27));
		expense5.setHour(LocalTime.of(12, 45));
		expense5.setSource(cuentaDAO.getByID(5));
		expense5.setCategory(categoriaEgresoDAO.getByID(5));
		expenseMovements.add(expense5);

		Egreso expense6 = new Egreso();
		expense6.setDescription("Fiesta de Confirmacion");
		expense6.setValue(500.0);
		expense6.setDate(LocalDate.of(2024, 8, 5));
		expense6.setHour(LocalTime.of(12, 45));
		expense6.setSource(cuentaDAO.getByID(5));
		expense6.setCategory(categoriaEgresoDAO.getByID(5));
		expenseMovements.add(expense6);

		// Agregar más egresos según el patrón
		// ...

		// Crear objetos de Transferencia
		Transferencia transferencia1 = new Transferencia();
		transferencia1.setDescription("Pago de electricidad");
		transferencia1.setValue(100.00);
		transferencia1.setDate(LocalDate.of(2024, 7, 1));
		transferencia1.setHour(LocalTime.of(10, 30));
		transferencia1.setSource(cuentaDAO.getByID(1));
		transferencia1.setDestination(cuentaDAO.getByID(2));
		transferencia1.setCategory(categoriaTransferenciaDAO.getByID(11));
		transferMovements.add(transferencia1);

		Transferencia transferencia2 = new Transferencia();
		transferencia2.setDescription("Transferencia para ahorro");
		transferencia2.setValue(200.00);
		transferencia2.setDate(LocalDate.of(2024, 7, 5));
		transferencia2.setHour(LocalTime.of(14, 0));
		transferencia2.setSource(cuentaDAO.getByID(2));
		transferencia2.setDestination(cuentaDAO.getByID(4));
		transferencia2.setCategory(categoriaTransferenciaDAO.getByID(11));
		transferMovements.add(transferencia2);

		Transferencia transferencia3 = new Transferencia();
		transferencia3.setDescription("Transferencia entre cuentas de empresa");
		transferencia3.setValue(500.00);
		transferencia3.setDate(LocalDate.of(2024, 7, 8));
		transferencia3.setHour(LocalTime.of(16, 15));
		transferencia3.setSource(cuentaDAO.getByID(3));
		transferencia3.setDestination(cuentaDAO.getByID(5));
		transferencia3.setCategory(categoriaTransferenciaDAO.getByID(11));
		transferMovements.add(transferencia3);

		Transferencia transferencia4 = new Transferencia();
		transferencia4.setDescription("Transferencia para inversión");
		transferencia4.setValue(300.00);
		transferencia4.setDate(LocalDate.of(2024, 8, 8));
		transferencia4.setHour(LocalTime.of(11, 30));
		transferencia4.setSource(cuentaDAO.getByID(4));
		transferencia4.setDestination(cuentaDAO.getByID(5));
		transferencia4.setCategory(categoriaTransferenciaDAO.getByID(11));
		transferMovements.add(transferencia4);

		Transferencia transferencia5 = new Transferencia();
		transferencia5.setDescription("Transferencia para pagos");
		transferencia5.setValue(150.00);
		transferencia5.setDate(LocalDate.of(2024, 7, 20));
		transferencia5.setHour(LocalTime.of(9, 0));
		transferencia5.setSource(cuentaDAO.getByID(2));
		transferencia5.setDestination(cuentaDAO.getByID(3));
		transferencia5.setCategory(categoriaTransferenciaDAO.getByID(11));
		transferMovements.add(transferencia5);

		// Persistir los movimientos
		for (Ingreso income : incomeMovements) {
			em.persist(income);
		}

		for (Egreso expense : expenseMovements) {
			em.persist(expense);
		}

		for (Transferencia transferencia : transferMovements) {
			em.persist(transferencia);
		}

		// Confirmar transacción
		em.getTransaction().commit();*/

		// -------------------------//
		// ACTUALIZAR TOTAL CUENTA
		CuentaDAO cuentaDAO = new CuentaDAO();
		IngresoDAO ingresoDAO = new IngresoDAO();
		EgresoDAO egresoDAO = new EgresoDAO();
		TransferenciaDAO transferenciaDAO = new TransferenciaDAO();
		
		for (Cuenta cuenta : cuentaDAO.getAll()) {
			double total = 0;
			List<Movimiento> movimientosByAccount = new ArrayList<>();
			movimientosByAccount.addAll(ingresoDAO.getAllByAccount(cuenta.getId()));
			movimientosByAccount.addAll(egresoDAO.getAllByAccount(cuenta.getId()));
			movimientosByAccount.addAll(transferenciaDAO.getAllByAccount(cuenta.getId()));
			
			for (Movimiento movimiento : movimientosByAccount) {
				if (movimiento instanceof Ingreso) {
					total += movimiento.getValue();
				} else if (movimiento instanceof Egreso) {
					total -= movimiento.getValue();
				} else if (movimiento instanceof Transferencia) {
					if (((Transferencia) movimiento).getSource() == cuenta) {
						total -= movimiento.getValue();
					} else if (((Transferencia) movimiento).getDestination() == cuenta) {
						total += movimiento.getValue();
					}
				}
			}
			cuenta.setTotal(total);
			em.getTransaction().begin();
			em.merge(cuenta);
			em.getTransaction().commit();
		}

	}
}
