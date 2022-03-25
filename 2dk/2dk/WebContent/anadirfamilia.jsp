<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
      <%@ page import="javax.servlet.http.HttpSession"%>
    <%
    
    HttpSession sesion;
    sesion = request.getSession();


    if((sesion.getAttribute("rol") != null && (int)sesion.getAttribute("rol") != 1) || sesion.getAttribute("rol") == null) {
    	response.sendRedirect("perfil");
    }
    
    %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css"
	href="/2dk/css/style.css" />
<title>Añadir familia</title>
</head>
<body>
<form action="anadirfamilia" method="post">
<h1>Añadir familias</h1>
<label>Introduce nombre</label>
<input name="nombre" type="text" value=""/>
<button>Enviar</button>
</form>

</body>
</html>