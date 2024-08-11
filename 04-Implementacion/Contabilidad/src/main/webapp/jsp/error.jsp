<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Error</title>
	<script src="https://kit.fontawesome.com/ecc032754e.js"
		crossorigin="anonymous"></script>
	<link rel="stylesheet" href="./css/style.css">
</head>
<body>
	<header class="bg-blue">
		<i class="fa-solid fa-coins fa-2x"></i>
		<h1>Mi Chaucherita</h1>
	</header>
	<main>
		<div class="error-details">
			<h1>Error</h1>
			<p>
				<c:out value="${errorMessage}" />
			</p>
			<a class="error-button" href="ContabilidadController?ruta=showDashboard&from=${from.toString()}&to=${to.toString()}">
				Volver a la página principal 
			</a>
		</div>
	</main>
</body>
</html>