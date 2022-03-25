<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ page import="javax.servlet.http.HttpSession"%>
 <%@ page import="modelos2dk.MFamilia" %>
 <%@ taglib prefix= "ex" uri = "WEB-INF/custom.tld"%>
          
<%      
HttpSession sesion;
sesion = request.getSession();
MFamilia[] familias;
familias=null;

if((sesion.getAttribute("rol") != null && (int)sesion.getAttribute("rol") != 1) || sesion.getAttribute("rol") == null) {
	response.sendRedirect("perfil");
}

if (request.getAttribute("rs")!=null){
	
	familias = (MFamilia[])request.getAttribute("rs");

}


%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css"
	href="/2dk/css/style.css" />
<title>Añadir subfamilia</title>
</head>
<body>

<form action="anadirsubfamilia" method="post">
<h1>Añadir SUBfamilias</h1>
<label>Introduce nombre</label>
<input name="nombre" type="text" />
<label>Familia</label>

<ex:familia familias="<%=familias%>" tipo="formulario"/>
<button>Enviar</button>

</form>


</body>
</html>