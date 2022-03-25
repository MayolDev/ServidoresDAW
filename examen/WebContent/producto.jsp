<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="java.util.Base64" %>
  <%@ page import="modelos.MOrdenador" %>
  <%@ taglib prefix= "ex" uri = "WEB-INF/custom.tld"%>

<!DOCTYPE html>
<html>
<%
MOrdenador ordenador;
ordenador = null;

if (request.getAttribute("rs")!=null){

	ordenador = (MOrdenador) request.getAttribute("rs");

	
}else{
	response.sendRedirect("articulos2dk");
}




%>
<head>
<meta charset="UTF-8">
<title><ex:producto atributo="titulo" articulo="<%=ordenador %>"/></title>
</head>
<body>
<h1 style="text-align: center;"><ex:producto atributo="titulo" articulo="<%=ordenador %>"/></h1>
<div style="width: 100vw ; margin: 0 auto; display:flex; flex-direction: column; justify-content: center; align-items: center ">
<ex:producto atributo="foto" articulo="<%=ordenador %>"/>
<p> <ex:producto atributo="descripcion" articulo="<%=ordenador %>"/> </p>
<small>Precio : <strong><ex:producto atributo="pvp" articulo="<%=ordenador %>"/></strong></small>

</div>
<a href="articulos2dk">Ir a ver productos</a>

</body>
</html>