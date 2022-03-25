<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.Base64" %>
  <%@ page import="modelos2dk.MArticulo" %>
  <%@ page import="modelos2dk.MSubfamilia" %>
  <%@ taglib prefix= "ex" uri = "WEB-INF/custom.tld"%>
<%
int categoria;
byte [] foto;
MSubfamilia[] subfamilias;
String bfoto;
subfamilias = null;

MArticulo[] articulos;

articulos = null;
if (request.getAttribute("rs")!=null){
	
articulos = (MArticulo[])request.getAttribute("rs");

}

categoria = -1;
if(request.getAttribute("categoria") != null){
		categoria = (int)request.getAttribute("categoria");
}

String busqueda;
busqueda = "";
if(request.getAttribute("busqueda") != null){
	busqueda = (String)request.getAttribute("busqueda");
}

String orden;
orden = "nombre";
if(request.getAttribute("orden") != null){
	orden = (String)request.getAttribute("orden");
}

int numeroRegistros;
numeroRegistros= 0;
if(request.getAttribute("numeroRegistros") != null){
	numeroRegistros = (int) request.getAttribute("numeroRegistros");
}

if (request.getAttribute("rsSubfamilia")!=null){
	
	subfamilias = (MSubfamilia[])request.getAttribute("rsSubfamilia");
	
}


int pagina;
pagina =1;
if(request.getAttribute("page") != null ){
	pagina = (int) request.getAttribute("page"); 
}

%>
    
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css"
	href="/2dk/css/style.css" />
<meta charset="UTF-8">
<title>Articulos de nuestra tienda 2DK</title>
</head>
<body>




<h1 style="text-align: center"> - Todos los articulos! -  </h1>
<label>Buscar: </label>
<form action="articulos2dk" method="post">
<input type="text" name="busqueda" value="" />
<label>Ordenar por:</label>
<select name="orden">
<option value="nombre" selected> Nombre </option>
<option value="pvp"> Precio </option>
</select>
<label>Categoria</label>
<ex:Categoria categorias="<%=subfamilias %>"/>

<button> Buscar </button>

</form>

<ex:articulo articulos="<%=articulos %>" tipo="catalogo"/>
<div style="text-align: center;">


<ex:paginacion numeroRegistros="<%=numeroRegistros %>" href="articulos2dk" pagina="<%=pagina %>" orden="<%=orden %>" categoria="<%=categoria %>" busqueda="<%=busqueda %>"/>
</div>
</body>
</html>