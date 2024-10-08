package controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modelo.dao.CategoriaDAO;
import modelo.dao.CategoriaEgresoDAO;
import modelo.dao.CategoriaIngresoDAO;
import modelo.dao.CategoriaTransferenciaDAO;
import modelo.dao.CuentaDAO;
import modelo.dao.EgresoDAO;
import modelo.dao.IngresoDAO;
import modelo.dao.MovimientoDAO;
import modelo.dao.TransferenciaDAO;
import modelo.entidades.Categoria;
import modelo.entidades.CategoriaEgreso;
import modelo.entidades.CategoriaIngreso;
import modelo.entidades.CategoriaTotalDTO;
import modelo.entidades.CategoriaTransferencia;
import modelo.entidades.Cuenta;
import modelo.entidades.Egreso;
import modelo.entidades.Ingreso;
import modelo.entidades.Movimiento;
import modelo.entidades.Transferencia;

@WebServlet("/ContabilidadController")
public class ContabilidadController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.router(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.router(req, resp);
	}

	private void router(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String ruta = (req.getParameter("ruta") == null) ? "showDashboard" : req.getParameter("ruta");
		switch (ruta) {
		case "showDashboard":
			this.showDahboard(req, resp);
			break;

		case "showAccount":
			this.showAccount(req, resp);
			break;

		case "showMovements":
			this.showMovements(req, resp);
			break;

		case "showUpdateForm":
			this.showUpdateForm(req, resp);
			break;

		case "updateMovement":
			this.updateMovement(req, resp);
			break;

		case "deleteMovement":
			this.deleteMovement(req, resp);
			break;

		case "showCategories":
			this.showCategories(req, resp);
			break;
		case "showIncomeForm":
			this.showIncomeForm(req, resp);
			break;
		case "newIncome":
			this.newIncome(req, resp);
			break;
		case "showExpenseForm":
			this.showExpenseForm(req, resp);
			break;
		case "newExpense":
			this.newExpense(req, resp);
			break;
		case "showTransferForm":
			this.showTransferForm(req, resp);
			break;
		case "newTransfer":
			this.newTransfer(req, resp);
			break;
		}
	}

	private void showDahboard(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 0. Instanciar los DAOs
		CuentaDAO cuentaDAO = new CuentaDAO();
		CategoriaIngresoDAO categoriaIngresoDAO = new CategoriaIngresoDAO();
		CategoriaEgresoDAO categoriaEgresoDAO = new CategoriaEgresoDAO();
		IngresoDAO ingresoDAO = new IngresoDAO();
		EgresoDAO egresoDAO = new EgresoDAO();
		TransferenciaDAO transferenciaDAO = new TransferenciaDAO();
		MovimientoDAO movimientoDAO = new MovimientoDAO();

		// 1. Obtener los parametros
		LocalDate to = LocalDate
				.parse((req.getParameter("to") == null) ? LocalDate.now().toString() : req.getParameter("to"));
		LocalDate from = LocalDate.parse((req.getParameter("from") == null)
				? LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth().getValue(), 1).toString()
				: req.getParameter("from"));

		// 2. Hablar con los modelos
		List<Cuenta> accounts = cuentaDAO.getAll();

		List<CategoriaTotalDTO> incomeCategories = categoriaIngresoDAO.getAllSummarized(from, to);
		List<CategoriaTotalDTO> expenseCategories = categoriaEgresoDAO.getAllSummarized(from, to);

		List<Movimiento> movements = new ArrayList<>();
		List<Ingreso> incomeMovements = ingresoDAO.getAllByDate(from, to);
		List<Egreso> expenseMovements = egresoDAO.getAllByDate(from, to);
		List<Transferencia> transferMovements = transferenciaDAO.getAllByDate(from, to);

		movements.addAll(incomeMovements);
		movements.addAll(expenseMovements);
		movements.addAll(transferMovements);

		movements = movimientoDAO.sortMovementsByDate(movements);

		// 3. Llamar a la vista
		req.setAttribute("accounts", accounts);
		req.setAttribute("incomeCategories", incomeCategories);
		req.setAttribute("expenseCategories", expenseCategories);
		req.setAttribute("movements", movements);
		req.setAttribute("from", from);
		req.setAttribute("to", to);

		req.getRequestDispatcher("jsp/verDashboard.jsp").forward(req, resp);

	}

	private void showAccount(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 0. Instanciar los DAOs
		CuentaDAO cuentaDAO = new CuentaDAO();
		IngresoDAO ingresoDAO = new IngresoDAO();
		EgresoDAO egresoDAO = new EgresoDAO();
		TransferenciaDAO transferenciaDAO = new TransferenciaDAO();
		MovimientoDAO movimientoDAO = new MovimientoDAO();

		// 1. Obtener los parametros
		int accountID = Integer.parseInt(req.getParameter("accountID"));

		LocalDate to = LocalDate
				.parse((req.getParameter("to") == null) ? LocalDate.now().toString() : req.getParameter("to"));
		LocalDate from = LocalDate.parse((req.getParameter("from") == null)
				? LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth().getValue(), 1).toString()
				: req.getParameter("from"));

		// 2. Hablar con los modelos
		Cuenta account = cuentaDAO.getByID(accountID);
		List<Movimiento> movements = new ArrayList<>();
		List<Ingreso> incomeMovements = ingresoDAO.getAllByAccount(accountID);
		List<Egreso> expenseMovements = egresoDAO.getAllByAccount(accountID);
		List<Transferencia> transferMovements = transferenciaDAO.getAllByAccount(accountID);

		movements.addAll(incomeMovements);
		movements.addAll(expenseMovements);
		movements.addAll(transferMovements);

		movements = movimientoDAO.filterMovementByDate(movements, from, to);
		movements = movimientoDAO.sortMovementsByDate(movements);
		// 3. Llamar a la vista
		req.setAttribute("account", account);
		req.setAttribute("movements", movements);
		req.setAttribute("from", from);
		req.setAttribute("to", to);

		req.getRequestDispatcher("jsp/verCuenta.jsp").forward(req, resp);
	}

	private void showMovements(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 0. Instanciar los DAOs
		IngresoDAO ingresoDAO = new IngresoDAO();
		EgresoDAO egresoDAO = new EgresoDAO();
		TransferenciaDAO transferenciaDAO = new TransferenciaDAO();
		MovimientoDAO movimientoDAO = new MovimientoDAO();

		// 1. Obtener los parametros
		LocalDate to = LocalDate
				.parse((req.getParameter("to") == null) ? LocalDate.now().toString() : req.getParameter("to"));
		LocalDate from = LocalDate.parse((req.getParameter("from") == null)
				? LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth().getValue(), 1).toString()
				: req.getParameter("from"));

		// 2. Hablar con los modelos
		List<Movimiento> movements = new ArrayList<>();
		List<Ingreso> incomeMovements = ingresoDAO.getAllByDate(from, to);
		List<Egreso> expenseMovements = egresoDAO.getAllByDate(from, to);
		List<Transferencia> transferMovements = transferenciaDAO.getAllByDate(from, to);

		movements.addAll(incomeMovements);
		movements.addAll(expenseMovements);
		movements.addAll(transferMovements);

		movements = movimientoDAO.sortMovementsByDate(movements);
		// 3. Llamar a la vista
		req.setAttribute("movements", movements);
		req.setAttribute("from", from);
		req.setAttribute("to", to);

		req.getRequestDispatcher("jsp/verMovimientos.jsp").forward(req, resp);
	}

	private void showUpdateForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 0. Instanciar los DAOs
		CuentaDAO cuentaDAO = new CuentaDAO();
		CategoriaIngresoDAO categoriaIngresoDAO = new CategoriaIngresoDAO();
		CategoriaEgresoDAO categoriaEgresoDAO = new CategoriaEgresoDAO();
		CategoriaTransferenciaDAO categoriaTransferenciaDAO = new CategoriaTransferenciaDAO();
		MovimientoDAO movimientoDAO = new MovimientoDAO();

		// 1. Obtener los parametros
		int movementID = Integer.parseInt(req.getParameter("movementID"));
		LocalDate to = LocalDate
				.parse((req.getParameter("to") == null) ? LocalDate.now().toString() : req.getParameter("to"));
		LocalDate from = LocalDate.parse((req.getParameter("from") == null)
				? LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth().getValue(), 1).toString()
				: req.getParameter("from"));

		// 2. Hablar con los modelos
		Movimiento movement = movimientoDAO.getByID(movementID);

		List<Cuenta> accounts = cuentaDAO.getAll();
		List<Categoria> categories = null;
		if (movement instanceof Ingreso) {
			categories = new ArrayList<>(categoriaIngresoDAO.getAll());
		} else if (movement instanceof Egreso) {
			categories = new ArrayList<>(categoriaEgresoDAO.getAll());
		} else if (movement instanceof Transferencia) {
			categories = new ArrayList<>(categoriaTransferenciaDAO.getAll());
		}

		// 3. Llamar a la vista
		req.setAttribute("movement", movement);
		req.setAttribute("accounts", accounts);
		req.setAttribute("categories", categories);
		req.setAttribute("from", from);
		req.setAttribute("to", to);

		req.getRequestDispatcher("jsp/verActualizarMovimiento.jsp").forward(req, resp);
	}

	private void updateMovement(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 0. Instanciar los DAOs
		CuentaDAO cuentaDAO = new CuentaDAO();
		CategoriaIngresoDAO categoriaIngresoDAO = new CategoriaIngresoDAO();
		CategoriaEgresoDAO categoriaEgresoDAO = new CategoriaEgresoDAO();
		CategoriaTransferenciaDAO categoriaTransferenciaDAO = new CategoriaTransferenciaDAO();
		IngresoDAO ingresoDAO = new IngresoDAO();
		EgresoDAO egresoDAO = new EgresoDAO();
		TransferenciaDAO transferenciaDAO = new TransferenciaDAO();
		MovimientoDAO movimientoDAO = new MovimientoDAO();

		// 1. Obtener los parametros
		int movementID = Integer.parseInt(req.getParameter("movementID"));
		String newDescription = req.getParameter("movementDescription");
		double newValue = Double.parseDouble(req.getParameter("movementValue"));
		LocalDate newDate = LocalDate.parse(req.getParameter("movementDate"));
		LocalTime newHour = LocalTime.parse(req.getParameter("movementHour"));

		LocalDate to = LocalDate
				.parse((req.getParameter("to") == null) ? LocalDate.now().toString() : req.getParameter("to"));
		LocalDate from = LocalDate.parse((req.getParameter("from") == null)
				? LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth().getValue(), 1).toString()
				: req.getParameter("from"));

		int sourceID = (req.getParameter("movementSource") != null)
				? Integer.parseInt(req.getParameter("movementSource"))
				: 0;
		int destinationID = (req.getParameter("movementDestination") != null)
				? Integer.parseInt(req.getParameter("movementDestination"))
				: 0;

		int categoryID = Integer.parseInt(req.getParameter("movementCategory"));

		// 2. Hablar con los modelos
		boolean flag = false;
		String errorMessage = "";
		Movimiento movement = movimientoDAO.getByID(movementID);

		if (movement instanceof Ingreso) {
			Ingreso income = new Ingreso();
			Cuenta destination = cuentaDAO.getByID(destinationID);

			income.setId(movementID);
			income.setDescription(newDescription);
			income.setValue(newValue);
			income.setDate(newDate);
			income.setHour(newHour);
			income.setDestination(destination);
			income.setCategory(categoriaIngresoDAO.getByID(categoryID));

			destination.setTotal(destination.getTotal() + income.getValue() - movement.getValue());

			flag = ingresoDAO.updateIncome(income) && cuentaDAO.update(destination);

		} else if (movement instanceof Egreso) {
			Egreso expense = new Egreso();
			Cuenta source = cuentaDAO.getByID(sourceID);
			
			expense.setId(movementID);
			expense.setDescription(newDescription);
			expense.setDate(newDate);
			expense.setHour(newHour);
			expense.setValue(newValue);
			expense.setSource(source);
			expense.setCategory(categoriaEgresoDAO.getByID(categoryID));

			source.setTotal(source.getTotal() + movement.getValue() - expense.getValue());

			if (source.getTotal() >= 0) {
				
				flag = egresoDAO.updateExpense(expense) && cuentaDAO.update(source);
			}else {
				errorMessage = "El VALOR EXCEDE el dinero que hay en la cuenta " + source.getName();
			}

		} else if (movement instanceof Transferencia) {
			Transferencia transfer = new Transferencia();
			Cuenta source = cuentaDAO.getByID(sourceID);
			Cuenta destination = cuentaDAO.getByID(destinationID);

			transfer.setId(movementID);
			transfer.setDescription(newDescription);
			transfer.setValue(newValue);
			transfer.setDate(newDate);
			transfer.setHour(newHour);
			transfer.setSource(source);
			transfer.setDestination(destination);
			transfer.setCategory(categoriaTransferenciaDAO.getByID(categoryID));

			if (transfer.getSource() != transfer.getDestination()) {
				source.setTotal(source.getTotal() - transfer.getValue() + movement.getValue());
				if (source.getTotal() >= 0) {
					destination.setTotal(destination.getTotal() + transfer.getValue() - movement.getValue());
					flag = transferenciaDAO.updateTransfer(transfer) && cuentaDAO.update(source)
							&& cuentaDAO.update(destination);
				}else {
					errorMessage = "El VALOR EXCEDE el dinero que hay en la cuenta " + source.getName();
				}
			}else {
				errorMessage = "No se permiten Transferencias a la misma cuenta " + source.getName();
			}
		}

		// 3. Llamar a la vista
		if (flag) {
			resp.sendRedirect(
					"ContabilidadController?ruta=showMovements&from=" + from.toString() + "&to" + to.toString());
		} else {
			errorMessage = "Se produjo un ERROR al MODIFICAR el Movimiento " + errorMessage;
			req.setAttribute("from", from);
			req.setAttribute("to", to);
			req.setAttribute("errorMessage", errorMessage);

			req.getRequestDispatcher("jsp/error.jsp").forward(req, resp);
		}

	}

	private void deleteMovement(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 0. Instanciar los DAOs
		IngresoDAO ingresoDAO = new IngresoDAO();
		EgresoDAO egresoDAO = new EgresoDAO();
		TransferenciaDAO transferenciaDAO = new TransferenciaDAO();
		MovimientoDAO movimientoDAO = new MovimientoDAO();
		CuentaDAO cuentaDAO = new CuentaDAO();

		// 1. Obtener los parametros
		int movementID = Integer.parseInt(req.getParameter("movementID"));

		LocalDate to = LocalDate
				.parse((req.getParameter("to") == null) ? LocalDate.now().toString() : req.getParameter("to"));
		LocalDate from = LocalDate.parse((req.getParameter("from") == null)
				? LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth().getValue(), 1).toString()
				: req.getParameter("from"));

		// 2. Hablar con los modelos
		boolean flag = false;
		String errorMessage = "";

		Movimiento movement = movimientoDAO.getByID(movementID);
		Cuenta source;
		Cuenta destination;

		if (movement instanceof Ingreso) {
			destination = ((Ingreso) movement).getDestination();
			destination.setTotal(destination.getTotal() - movement.getValue());
			if (destination.getTotal() >= 0) {
				flag = ingresoDAO.deleteIncome(movementID) && cuentaDAO.update(destination);
			}else {
				errorMessage = "El VALOR EXCEDE el dinero que hay en la cuenta ";
			}

		} else if (movement instanceof Egreso) {
			source = ((Egreso) movement).getSource();
			source.setTotal(source.getTotal() + movement.getValue());

			flag = egresoDAO.deleteExpense(movementID) && cuentaDAO.update(source);

		} else if (movement instanceof Transferencia) {
			source = ((Transferencia) movement).getSource();
			destination = ((Transferencia) movement).getDestination();

			destination.setTotal(destination.getTotal() - movement.getValue());
			source.setTotal(source.getTotal() + movement.getValue());

			if (destination.getTotal() >= 0) {
				flag = transferenciaDAO.deleteTransfer(movementID) && cuentaDAO.update(source)
						&& cuentaDAO.update(destination);
			}else {
				errorMessage = "El VALOR EXCEDE el dinero que hay en la cuenta ";
			}
		}

		// 3. Llamar a la vista
		if (flag) {
			resp.sendRedirect(
					"ContabilidadController?ruta=showMovements&from=" + from.toString() + "&to" + to.toString());
		} else {
			errorMessage = "El VALOR EXCEDE el dinero que hay en la cuenta " + errorMessage;
			req.setAttribute("from", from);
			req.setAttribute("to", to);
			req.setAttribute("errorMessage", errorMessage);

			req.getRequestDispatcher("jsp/error.jsp").forward(req, resp);
		}
	}

	private void showCategories(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 0. Instanciar los DAOs
		CategoriaDAO categoriaDAO = new CategoriaDAO();
		IngresoDAO ingresoDAO = new IngresoDAO();
		EgresoDAO egresoDAO = new EgresoDAO();
		TransferenciaDAO transferenciaDAO = new TransferenciaDAO();
		MovimientoDAO movimientoDAO = new MovimientoDAO();

		// 1. Obtener los parametros
		int categoryID = Integer.parseInt(req.getParameter("categoryID"));

		LocalDate to = LocalDate
				.parse((req.getParameter("to") == null) ? LocalDate.now().toString() : req.getParameter("to"));
		LocalDate from = LocalDate.parse((req.getParameter("from") == null)
				? LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth().getValue(), 1).toString()
				: req.getParameter("from"));

		// 2. Hablar con los modelos
		CategoriaTotalDTO category = categoriaDAO.getSummarizedByID(categoryID, from, to);

		List<Movimiento> movements = new ArrayList<>();
		List<Ingreso> incomeMovements = ingresoDAO.getAllByCategory(categoryID);
		List<Egreso> expenseMovements = egresoDAO.getAllByCategory(categoryID);
		List<Transferencia> transferMovements = transferenciaDAO.getAllByCategory(categoryID);

		movements.addAll(incomeMovements);
		movements.addAll(expenseMovements);
		movements.addAll(transferMovements);

		movements = movimientoDAO.filterMovementByDate(movements, from, to);
		movements = movimientoDAO.sortMovementsByDate(movements);
		// 3. Llamar a la vista
		req.setAttribute("category", category);
		req.setAttribute("movements", movements);
		req.setAttribute("from", from);
		req.setAttribute("to", to);

		req.getRequestDispatcher("jsp/verCategoria.jsp").forward(req, resp);

	}

	private void showIncomeForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 0. Instanciar los DAOs
		CuentaDAO cuentaDAO = new CuentaDAO();
		CategoriaIngresoDAO categoriaIngresoDAO = new CategoriaIngresoDAO();

		// 1. Obtener los parametros
		int accountID = Integer.parseInt(req.getParameter("accountID"));

		LocalDate to = LocalDate
				.parse((req.getParameter("to") == null) ? LocalDate.now().toString() : req.getParameter("to"));
		LocalDate from = LocalDate.parse((req.getParameter("from") == null)
				? LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth().getValue(), 1).toString()
				: req.getParameter("from"));
		
		// 2. Hablar con los modelos
		Cuenta account = cuentaDAO.getByID(accountID);
		List<Cuenta> accounts = cuentaDAO.getAll();
		List<CategoriaIngreso> categories = categoriaIngresoDAO.getAll();

		// 3. Llamar a la vista
		req.setAttribute("account", account);
		req.setAttribute("accounts", accounts);
		req.setAttribute("categories", categories);
		req.setAttribute("from", from);
		req.setAttribute("to", to);

		req.getRequestDispatcher("jsp/verIngreso.jsp").forward(req, resp);
	}

	private void newIncome(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 0. Instanciar los DAOs
		CuentaDAO cuentaDAO = new CuentaDAO();
		CategoriaIngresoDAO categoriaIngresoDAO = new CategoriaIngresoDAO();
		IngresoDAO ingresoDAO = new IngresoDAO();

		// 1. Obtener los parametros
		String description = req.getParameter("movementDescription");
		double value = Double.parseDouble(req.getParameter("movementValue"));
		LocalDate date = LocalDate.parse(req.getParameter("movementDate"));
		LocalTime hour = LocalTime.parse(req.getParameter("movementHour"));
		int destinationID = Integer.parseInt(req.getParameter("movementDestination"));
		int categoryID = Integer.parseInt(req.getParameter("movementCategory"));

		LocalDate to = LocalDate
				.parse((req.getParameter("to") == null) ? LocalDate.now().toString() : req.getParameter("to"));
		LocalDate from = LocalDate.parse((req.getParameter("from") == null)
				? LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth().getValue(), 1).toString()
				: req.getParameter("from"));
		// 2. Hablar con los modelos
		boolean flag = false;
		Cuenta destination = cuentaDAO.getByID(destinationID);
		CategoriaIngreso category = categoriaIngresoDAO.getByID(categoryID);

		Ingreso newIncome = new Ingreso();
		newIncome.setDescription(description);
		newIncome.setValue(value);
		newIncome.setDate(date);
		newIncome.setHour(hour);
		newIncome.setDestination(destination);
		newIncome.setCategory(category);

		destination.setTotal(destination.getTotal() + newIncome.getValue());

		flag = ingresoDAO.newIncome(newIncome) && cuentaDAO.update(destination);
		// 3. Llamar a la vista
		if (flag) {
			resp.sendRedirect(
					"ContabilidadController?ruta=showMovements&from=" + from.toString() + "&to" + to.toString());
		} else {
			req.setAttribute("from", from);
			req.setAttribute("to", to);
			req.setAttribute("errorMessage", "Se produjo un ERROR al realizar el INGRESO");

			req.getRequestDispatcher("jsp/error.jsp").forward(req, resp);
		}

	}

	private void showExpenseForm(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// 0. Instanciar los DAOs
		CuentaDAO cuentaDAO = new CuentaDAO();
		CategoriaEgresoDAO categoriaEgresoDAO = new CategoriaEgresoDAO();

		// 1. Obtener los parametros
		int accountID = Integer.parseInt(req.getParameter("accountID"));

		LocalDate to = LocalDate
				.parse((req.getParameter("to") == null) ? LocalDate.now().toString() : req.getParameter("to"));
		LocalDate from = LocalDate.parse((req.getParameter("from") == null)
				? LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth().getValue(), 1).toString()
				: req.getParameter("from"));
		// 2. Hablar con los modelos
		Cuenta account = cuentaDAO.getByID(accountID);
		List<Cuenta> accounts = cuentaDAO.getAll();
		List<CategoriaEgreso> categories = categoriaEgresoDAO.getAll();

		// 3. Llamar a la vista
		req.setAttribute("account", account);
		req.setAttribute("accounts", accounts);
		req.setAttribute("categories", categories);
		req.setAttribute("from", from);
		req.setAttribute("to", to);

		req.getRequestDispatcher("jsp/verEgreso.jsp").forward(req, resp);

	}

	private void newExpense(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 0. Instanciar los DAOs
		CuentaDAO cuentaDAO = new CuentaDAO();
		CategoriaEgresoDAO categoriaEgresoDAO = new CategoriaEgresoDAO();
		EgresoDAO egresoDAO = new EgresoDAO();

		// 1. Obtener los parametros
		String description = req.getParameter("movementDescription");
		double value = Double.parseDouble(req.getParameter("movementValue"));
		LocalDate date = LocalDate.parse(req.getParameter("movementDate"));
		LocalTime hour = LocalTime.parse(req.getParameter("movementHour"));
		int sourceID = Integer.parseInt(req.getParameter("movementSource"));
		int categoryID = Integer.parseInt(req.getParameter("movementCategory"));

		LocalDate to = LocalDate
				.parse((req.getParameter("to") == null) ? LocalDate.now().toString() : req.getParameter("to"));
		LocalDate from = LocalDate.parse((req.getParameter("from") == null)
				? LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth().getValue(), 1).toString()
				: req.getParameter("from"));
		// 2. Hablar con los modelos
		boolean flag = false;
		String errorMessage = "";
		
		Cuenta source = cuentaDAO.getByID(sourceID);
		CategoriaEgreso category = categoriaEgresoDAO.getByID(categoryID);

		Egreso newExpense = new Egreso();
		newExpense.setDescription(description);
		newExpense.setValue(value);
		newExpense.setDate(date);
		newExpense.setHour(hour);
		newExpense.setSource(source);
		newExpense.setCategory(category);

		source.setTotal(source.getTotal() - newExpense.getValue());

		if (source.getTotal() >= 0) {
			flag = egresoDAO.newExpense(newExpense) && cuentaDAO.update(source);
		} else {
			errorMessage = "El VALOR EXCEDE el dinero que hay en la cuenta " + source.getName();
		}

		// 3. Llamar a la vista
		if (flag) {
			resp.sendRedirect(
					"ContabilidadController?ruta=showMovements&from=" + from.toString() + "&to" + to.toString());
		} else {
			errorMessage = "Se produjo un ERROR al realizar el EGRESO " + errorMessage;
			req.setAttribute("from", from);
			req.setAttribute("to", to);
			req.setAttribute("errorMessage", errorMessage);

			req.getRequestDispatcher("jsp/error.jsp").forward(req, resp);
		}

	}

	private void showTransferForm(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// 0. Instanciar los DAOs
		CuentaDAO cuentaDAO = new CuentaDAO();
		CategoriaTransferenciaDAO categoriaTransferenciaDAO = new CategoriaTransferenciaDAO();

		// 1. Obtener los parametros
		int accountID = Integer.parseInt(req.getParameter("accountID"));

		LocalDate to = LocalDate
				.parse((req.getParameter("to") == null) ? LocalDate.now().toString() : req.getParameter("to"));
		LocalDate from = LocalDate.parse((req.getParameter("from") == null)
				? LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth().getValue(), 1).toString()
				: req.getParameter("from"));
		// 2. Hablar con los modelos
		Cuenta account = cuentaDAO.getByID(accountID);
		List<Cuenta> accounts = cuentaDAO.getAll();
		List<CategoriaTransferencia> categories = categoriaTransferenciaDAO.getAll();

		// 3. Llamar a la vista
		req.setAttribute("account", account);
		req.setAttribute("accounts", accounts);
		req.setAttribute("categories", categories);
		req.setAttribute("from", from);
		req.setAttribute("to", to);

		req.getRequestDispatcher("jsp/verTransferencia.jsp").forward(req, resp);
	}

	private void newTransfer(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 0. Instanciar los DAOs
		CuentaDAO cuentaDAO = new CuentaDAO();
		CategoriaTransferenciaDAO categoriaTransferenciaDAO = new CategoriaTransferenciaDAO();
		TransferenciaDAO transferenciaDAO = new TransferenciaDAO();

		// 1. Obtener los parametros
		String description = req.getParameter("movementDescription");
		double value = Double.parseDouble(req.getParameter("movementValue"));
		LocalDate date = LocalDate.parse(req.getParameter("movementDate"));
		LocalTime hour = LocalTime.parse(req.getParameter("movementHour"));
		int sourceID = Integer.parseInt(req.getParameter("movementSource"));
		int destinationID = Integer.parseInt(req.getParameter("movementDestination"));
		int categoryID = Integer.parseInt(req.getParameter("movementCategory"));

		LocalDate to = LocalDate
				.parse((req.getParameter("to") == null) ? LocalDate.now().toString() : req.getParameter("to"));
		LocalDate from = LocalDate.parse((req.getParameter("from") == null)
				? LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth().getValue(), 1).toString()
				: req.getParameter("from"));
		// 2. Hablar con los modelos
		boolean flag = false;
		String errorMessage = "";
		Cuenta source = cuentaDAO.getByID(sourceID);
		Cuenta destination = cuentaDAO.getByID(destinationID);
		CategoriaTransferencia category = categoriaTransferenciaDAO.getByID(categoryID);

		Transferencia newTransfer = new Transferencia();
		newTransfer.setDescription(description);
		newTransfer.setValue(value);
		newTransfer.setDate(date);
		newTransfer.setHour(hour);
		newTransfer.setSource(source);
		newTransfer.setDestination(destination);
		newTransfer.setCategory(category);

		if (newTransfer.getSource() != newTransfer.getDestination()) {

			destination.setTotal(destination.getTotal() + newTransfer.getValue());
			source.setTotal(source.getTotal() - newTransfer.getValue());

			if (source.getTotal() >= 0) {
				flag = transferenciaDAO.newTransfer(newTransfer) &&	cuentaDAO.update(source);
			} else {
				errorMessage = "El VALOR EXCEDE el dinero que hay en la cuenta " + source.getName();
			}
		} else {
			errorMessage = "No se permiten Transferencias a la misma cuenta " + source.getName() ;
		}

		// 3. Llamar a la vista
		if (flag) {
			resp.sendRedirect("ContabilidadController?ruta=showMovements&from=" + from.toString() + "&to" + to.toString());
		} else {
			errorMessage = "Se produjo un ERROR al realizar la TRANSFERENCIA " + errorMessage;
			req.setAttribute("from", from);
			req.setAttribute("to", to);
			req.setAttribute("errorMessage", errorMessage);

			req.getRequestDispatcher("jsp/error.jsp").forward(req, resp);
		}
	}

}
