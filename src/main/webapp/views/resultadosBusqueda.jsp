<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Resultados de búsqueda</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background: #f1f3f5;
            margin: 0;
            color: #243b55;
        }
        header {
            background: #243b55;
            color: white;
            padding: 25px;
            text-align: center;
        }
        main {
            max-width: 1000px;
            margin: 35px auto;
            padding: 25px;
            background: white;
            border-radius: 10px;
            box-shadow: 0 3px 12px #0002;
            overflow-x: auto;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        th, td {
            padding: 12px;
            border: 1px solid #ddd;
            text-align: left;
        }
        th {
            background: #243b55;
            color: white;
        }
        tr:nth-child(even) {
            background: #f1f3f5;
        }
        .volver {
            display: inline-block;
            margin-top: 20px;
            margin-right: 10px;
            padding: 11px 16px;
            border-radius: 5px;
            background: #243b55;
            color: white;
            text-decoration: none;
        }
    </style>
</head>
<body>

<header>
    <h1>Resultados de búsqueda</h1>
</header>

<main>

    <c:if test="${not empty mensaje}">
        <p><c:out value="${mensaje}"/></p>
    </c:if>

    <c:choose>
        <c:when test="${not empty empleados}">
            <table>
                <thead>
                    <tr>
                        <th>Nombre</th>
                        <th>DNI</th>
                        <th>Sexo</th>
                        <th>Categoría</th>
                        <th>Años trabajados</th>
                    </tr>
                </thead>

                <tbody>
                    <c:forEach var="empleado" items="${empleados}">
                        <tr>
                            <td><c:out value="${empleado.nombre}"/></td>
                            <td><c:out value="${empleado.dni}"/></td>
                            <td><c:out value="${empleado.sexo}"/></td>
                            <td><c:out value="${empleado.categoria}"/></td>
                            <td><c:out value="${empleado.anyosTrabajados}"/></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:when>

        <c:otherwise>
            <p>No se han encontrado empleados.</p>
        </c:otherwise>
    </c:choose>

    <a class="volver"
       href="${pageContext.request.contextPath}/controlador?accion=buscar">
        Volver a buscar
    </a>

    <a class="volver"
       href="${pageContext.request.contextPath}/index.jsp">
        Volver al menú
    </a>

</main>
</body>
</html>
