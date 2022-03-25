<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="java.util.Base64" %>
  <%@ page import="modelos2dk.MArticulo" %>
  <%@ taglib prefix= "ex" uri = "WEB-INF/custom.tld"%>

<!DOCTYPE html>
<html>
<%
MArticulo[] articulos;
MArticulo articulo;
articulo = null;

if (request.getAttribute("rs")!=null){

	articulos = (MArticulo[]) request.getAttribute("rs");
	articulo = articulos[0];
	
}else{
	response.sendRedirect("articulos2dk");
}




%>
<head>
<meta charset="UTF-8">
<title><ex:producto atributo="titulo" articulo="<%=articulo %>"/></title>
</head>
<body>
<h1 style="text-align: center;"><ex:producto atributo="titulo" articulo="<%=articulo %>"/></h1>
<div style="width: 100vw ; margin: 0 auto; display:flex; flex-direction: column; justify-content: center; align-items: center ">
<ex:producto atributo="foto" articulo="<%=articulo %>"/>
<p> <ex:producto atributo="descripcion" articulo="<%=articulo %>"/> </p>
<small>Precio : <strong><ex:producto atributo="pvp" articulo="<%=articulo %>"/></strong></small>
<small>Precio con descuento : <strong><ex:producto atributo="pcd" articulo="<%=articulo %>"/></strong></small>
<small>Stock : <strong><ex:producto atributo="stock" articulo="<%=articulo %>"/></strong></small>

</div>
<a href="articulos2dk">Ir a ver productos</a>

</body>
</html>