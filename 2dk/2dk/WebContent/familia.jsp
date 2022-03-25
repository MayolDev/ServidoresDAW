<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  <%@ page import="javax.servlet.http.HttpSession"%>
  <%@ page import="modelos2dk.MFamilia" %>
    <%@ taglib prefix= "ex" uri = "WEB-INF/custom.tld"%>
  
<%

HttpSession sesion;
sesion = request.getSession();
MFamilia[] familias;
boolean error;
error = false;

if((sesion.getAttribute("rol") != null && (int)sesion.getAttribute("rol") != 1) || sesion.getAttribute("rol") == null) {
	response.sendRedirect("perfil");
}


int pagina;
pagina =1;
if(request.getAttribute("page") != null ){
	pagina = (int) request.getAttribute("page"); 
}

if(request.getAttribute("DeleteError") != null) {
	error =(boolean) request.getAttribute("DeleteError");
}

familias = null;
if (request.getAttribute("rs")!=null){
	
	familias = (MFamilia[])request.getAttribute("rs");
	
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
<title>Ver familias</title>
</head>
<body>

<ex:error error="<%=error %>" errorMessage="Error al eliminar elemento">
</ex:error>

<div class="container">
<h1>Todas las familias</h1>


<ex:familia familias="<%=familias%>" tipo="tabla"/>

<ex:paginacion numeroRegistros="<%=numeroRegistros %>" href="familias" pagina="<%=pagina %>"/>

</div>

</body>
</html>