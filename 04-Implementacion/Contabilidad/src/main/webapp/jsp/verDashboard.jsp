<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Dashboard</title>
<link rel="stylesheet" href="./css/style.css">

<script src="https://kit.fontawesome.com/ecc032754e.js"
	crossorigin="anonymous"></script>
</head>

<body>
	<header class="bg-blue">
		<i class="fa-solid fa-coins fa-2x"></i>
		<h1><a href="ContabilidadController?ruta=showDashboard&from=${from.toString()}&to=${to.toString()}">Mi Chaucherita</a></h1>
		<!-- Formulario para seleccionar fechas -->
		<div class="date-selection">
			<form action="ContabilidadController" method="get">
				<input type="hidden" name="ruta" value="showDashboard" />
				<label for="from">Desde:</label> 
				<input type="date" id="from" name="from" value="${from.toString()}" required> 
				<label for="to">Hasta:</label> 
				<input type="date" id="to" name="to" value="${to.toString()}" required>

				<button type="submit">Filtrar</button>
			</form>
		</div>
	</header>
	<main>
		<div class="dashboard">
			<div class="accounts">
				<h2>Cuentas</h2>
				<div class="card-container">
					<c:forEach items="${accounts}" var="account">
						<div class="card">
							<div class="card-details">
								<h3>${account.name}</h3>
								<h4>$${account.total}</h4>
							</div>
							<form
								action="ContabilidadController?ruta=showAccount&from=${from}&to=${to}"
								method="post">
								<input type="hidden" name="accountID" value="${account.id}" />
								<button type="submit" class="card-button">
									<h5>Ver Detalles</h5>
								</button>
							</form>
							<form action="ContabilidadController?ruta=showIncomeForm&from=${from}&to=${to}"
								method="post">
								<input type="hidden" name="accountID" value="${account.id}" />
								<button type="submit" class="card-button">
									<h5>Nuevo Ingreso</h5>
								</button>
							</form>
							<form action="ContabilidadController?ruta=showExpenseForm&from=${from}&to=${to}"
								method="post">
								<input type="hidden" name="accountID" value="${account.id}" />
								<button type="submit" class="card-button">
									<h5>Nuevo Egreso</h5>
								</button>
							</form>
							<form action="ContabilidadController?ruta=showTransferForm&from=${from}&to=${to}"
								method="post">
								<input type="hidden" name="accountID" value="${account.id}" />
								<button type="submit" class="card-button">
									<h5>Nueva Transferencia</h5>
								</button>
							</form>
						</div>
					</c:forEach>
				</div>
			</div>
			<div class="cat-mov">
				<div class="categories">
					<h2>Categorias</h2>
					<br>
					<h3>Categorias de Ingreso</h3>
					<div class="card-container">

						<c:forEach items="${incomeCategories}" var="incomeCategory">
							<div class="card">
								<div class="card-details">
									<h3>${incomeCategory.name}</h3>
									<h4>$${incomeCategory.total}</h4>
								</div>
								<form action="ContabilidadController?ruta=showCategories&from=${from}&to=${to}"
									method="post">
									<input type="hidden" name="categoryID"
										value="${incomeCategory.id}" />
									<button type="submit" class="card-button">
										<h5>Ver Detalles</h5>
									</button>
								</form>
							</div>
						</c:forEach>
					</div>
					<h3>Categorias de Egreso</h3>
					<div class="card-container">

						<c:forEach items="${expenseCategories}" var="expenseCategory">
							<div class="card">
								<div class="card-details">
									<h3>${expenseCategory.name}</h3>
									<h4>$${expenseCategory.total}</h4>
								</div>
								<form action="ContabilidadController?ruta=showCategories&from=${from}&to=${to}"
									method="post">
									<input type="hidden" name="categoryID"
										value="${expenseCategory.id}" />
									<button type="submit" class="card-button">
										<h5>Ver Detalles</h5>
									</button>
								</form>

							</div>
						</c:forEach>
					</div>
				</div>
				<div class="movements">
					<h2>Movimientos</h2>
					<br>
					<form action="ContabilidadController?ruta=showMovements&from=${from}&to=${to}"
						method="post">
						<input type="hidden" name="expenseCategoryID"
							value="${account.id}" />
						<button type="submit" class="card-button">
							<h5>Ver todo</h5>
						</button>
					</form>
					<table>
						<thead>
							<tr>
								<th>ID</th>
								<th>Concepto</th>
								<th>Monto</th>
								<th>Cuenta de Origen</th>
								<th>Cuenta de Destino</th>
								<th>Fecha (aaaa/mm/dd)</th>
								<th>Hora</th>
								<th>Categoria</th>
								<th>Acciones</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${movements}" var="movement">
								<tr>
									<td>${movement.id}</td>
									<td>${movement.description}</td>
									<td>${movement.value}</td>
									<td>${movement.getSource() != null? movement.source.getName() : 'N/A'}</td>
									<td>${movement.getDestination() != null ? movement.destination.getName() : 'N/A'}</td>
									<td>${movement.date}</td>
									<td>${movement.hour}</td>
									<td>${movement.category.getName()}</td>
									<td>
										<a href="ContabilidadController?ruta=showUpdateForm&movementID=${movement.id}&from=${from}&to=${to}">Actualizar</a>
										<br> 
										<a href="ContabilidadController?ruta=deleteMovement&movementID=${movement.id}&from=${from}&to=${to}">Eliminar</a>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</main>
</body>

</html>