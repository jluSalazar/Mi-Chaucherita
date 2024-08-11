<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Nuevo Egreso</title>
<link rel="stylesheet" href="./css/style.css">

<script src="https://kit.fontawesome.com/ecc032754e.js"
	crossorigin="anonymous"></script>
</head>
<body>
	<header class="bg-blue">
		<i class="fa-solid fa-coins fa-2x"></i>
		<h1><a href="ContabilidadController?ruta=showDashboard&from=${from.toString()}&to=${to.toString()}">Mi Chaucherita</a></h1>
	</header>
	<main class="main-form">
		<form
			action="ContabilidadController?ruta=newExpense&from=${from.toString()}&to=${to.toString()}"
			method="post">
			<h2>Nuevo Egreso</h2>
			<br>
			<div>
				<label for="movementDescription">Concepto:</label> <input
					type="text" name="movementDescription" id="movementDescription" required>
			</div>
			<br>
			<div>
				<label for="movementValue">Monto:</label> <input type="number"
					name="movementValue" id="movementValue" min="0" step="any" required>
			</div>
			<br> <br>
			<div>
				<label for="movementDate">Fecha:</label> <input type="date"
					name="movementDate" id="movementDate" required>
			</div>
			<br>
			<div>
				<label for="movementHour">Hora:</label> <input type="time"
					name="movementHour" id="movementHour" required>
			</div>
			<div>
				<label for="movementSource">Cuenta de Origen:</label> <select
					name="movementSource" id="movementSource" required>
					<c:forEach items="${accounts}" var="accountIterator">
						<option value="${accountIterator.id}"
							<c:if test="${accountIterator.id eq account.id}">selected</c:if>>
							${accountIterator.name}</option>
					</c:forEach>
				</select> <br>
			</div>

			<div>
				<label for="movementCategory">Categoría:</label> <select
					name="movementCategory" id="movementCategory" required>
					<c:forEach items="${categories}" var="category">
						<option value="${category.id}">${category.name}</option>
					</c:forEach>
				</select>
			</div>
			<br>
			<div>
				<button type="submit">Guardar</button>
			</div>
		</form>

	</main>
</body>
</html>