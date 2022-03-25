<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  <%@ page import="javax.servlet.http.HttpSession"%>
  <%@ page import="modelos2dk.MArticulo" %>
  <%@ taglib prefix= "ex" uri = "WEB-INF/custom.tld"%>
  




<%

HttpSession sesion;
sesion = request.getSession();
MArticulo[] articulos;
articulos = null;
boolean error;
error = false;

if((sesion.getAttribute("rol") != null && (int)sesion.getAttribute("rol") != 1) || sesion.getAttribute("rol") == null) {
	response.sendRedirect("perfil");
}

if (request.getAttribute("rs") != null){
articulos = (MArticulo[])request.getAttribute("rs");	
}

int pagina;
pagina =1;
if(request.getAttribute("page") != null ){
	pagina = (int) request.getAttribute("page"); 
}

if(request.getAttribute("DeleteError") != null) {
	error = (boolean) request.getAttribute("DeleteError");
}
int numeroRegistros;
numeroRegistros= 0;
if(request.getAttribute("numeroRegistros") != null){
	numeroRegistros = (int) request.getAttribute("numeroRegistros");
}
%>


<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css"
	href="/2dk/css/style.css" />
<meta charset="UTF-8">
<title>Articulos</title>
</head>
<body>


<ex:error errorMessage= "Error al eliminar elemento" error="<%=error %>" >
</ex:error>

<div class="container">
<h1>Todas los articulos</h1>

<ex:articulo articulos="<%=articulos %>" tipo="tabla"/>


<ex:paginacion numeroRegistros="<%=numeroRegistros %>" href="articulos" pagina="<%=pagina %>"/>


</div>

</body>
</html>