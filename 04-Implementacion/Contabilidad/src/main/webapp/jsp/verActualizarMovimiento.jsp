<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Actualizar</title>
<link rel="stylesheet" href="./css/style.css">

<script src="https://kit.fontawesome.com/ecc032754e.js"
	crossorigin="anonymous"></script>
</head>
<body>
	<header class="bg-blue">
		<i class="fa-solid fa-coins fa-2x"></i>
		<h1>Mi Chaucherita</h1>
	</header>
	<main class="main-form">
		<form action="ContabilidadController?ruta=updateMovement&from=${from.toString()}&to=${to.toString()}"
			method="post">
			<h2>Actualizar ${movement.getClass().getSimpleName()}</h2>
			<br>
			<div>
				<label for="movementID">ID:</label> <input type="text"
					name="movementID" id="movementID" value="${movement.id}" readonly>
			</div>
			<br>
			<div>
				<label for="movementDescription">Concepto:</label> <input
					type="text" name="movementDescription" id="movementDescription"
					value="${movement.description}" required>
			</div>
			<br>
			<div>
				<label for="movementValue">Monto:</label> <input type="number"
					name="movementValue" id="movementValue" value="${movement.value}" min="0" step="any" required>
			</div>
			<br> <br>
			<div>
				<label for="movementDate">Fecha:</label> <input type="date"
					name="movementDate" id="movementDate" value="${movement.date}"required>
			</div>
			<br>
			<div>
				<label for="movementHour">Hora:</label> <input type="time"
					name="movementHour" id="movementHour" value="${movement.hour}"required>
			</div>
			<div>
				<c:if test="${not empty movement.source}">
					<label for="movementSource">Ct. de Origen:</label>
					<select name="movementSource" id="movementSource" required>
						<c:forEach items="${accounts}" var="account">
							<option value="${account.id}"
								<c:if test="${account.name eq movement.source.name}">selected</c:if>>
								${account.name}</option>
						</c:forEach>
					</select>
					<br>
				</c:if>
			</div>

			<div>
				<c:if test="${not empty movement.destination}">
					<label for="movementDestination">Cuenta de Destino:</label>
					<select name="movementDestination" id="movementDestination" required>
						<c:forEach items="${accounts}" var="account">
							<option value="${account.id}"
								<c:if test="${account.name eq movement.destination.name}">selected</c:if>>
								${account.name}</option>
						</c:forEach>
					</select>
					<br>
				</c:if>
			</div>

			<div>
				<label for="movementCategory">Categoría:</label> <select
					name="movementCategory" id="movementCategory" required>
					<c:forEach items="${categories}" var="category">
						<option value="${category.id}"
							<c:if test="${category.id == movement.category.id}">selected</c:if>>
							${category.name}</option>
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