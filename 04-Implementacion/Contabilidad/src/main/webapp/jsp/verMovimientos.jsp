<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Movimientos</title>
<link rel="stylesheet" href="./css/style.css">
<script src="https://kit.fontawesome.com/ecc032754e.js"
	crossorigin="anonymous"></script>
</head>
<body>
	<header class="bg-blue">
		<i class="fa-solid fa-coins fa-2x"></i>
		<h1><a href="ContabilidadController?ruta=showDashboard&from=${from.toString()}&to=${to.toString()}">Mi Chaucherita</a></h1>
		<div class="date-selection">
			<form action="ContabilidadController" method="get">
				<input type="hidden" name="ruta" value="showMovements" />
				<label for="from">Desde:</label> 
				<input type="date" id="from" name="from" value="${from.toString()}" required> 
				<label for="to">Hasta:</label> 
				<input type="date" id="to" name="to" value="${to.toString()}" required>

				<button type="submit">Filtrar</button>
			</form>
		</div>
	</header>
	<main>
		<div class="movements">
			<h2>Todos los movimientos</h2>
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
							<td><a href="ContabilidadController?ruta=showUpdateForm&movementID=${movement.id}&from=${from.toString()}&to=${to.toString()}">Actualizar</a>
								<br> 
								<a href="ContabilidadController?ruta=deleteMovement&movementID=${movement.id}&from=${from.toString()}&to=${to.toString()}">Eliminar</a>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</main>
</body>
</html>