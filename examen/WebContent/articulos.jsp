<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.Base64" %>
  <%@ page import="modelos.MOrdenador" %>
  <%@ taglib prefix= "ex" uri = "WEB-INF/custom.tld"%>
<%
int categoria;
byte [] foto;
String bfoto;
MOrdenador[] ordenadores;

ordenadores = null;
if (request.getAttribute("rs")!=null){
	
ordenadores = (MOrdenador[])request.getAttribute("rs");

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
<title>Articulos de nuestra tienda Informatica</title>
</head>
<body>




<h1 style="text-align: center"> - Todos los articulos! -  </h1>
<label>Buscar: </label>
<form action="articulos" method="post">
<input type="text" name="busqueda" value="" />
<label>Ordenar por:</label>
<select name="orden">
<option value="nombre" selected> Nombre </option>
<option value="pvp"> Precio </option>
</select>
<label>Marca</label>
<select name="categoria">
<option value="HP" selected> HP </option>
<option value="DELL"> DELL </option>
</select>

<button> Buscar </button>

</form>

<ex:articulo articulos="<%=ordenadores %>" tipo="catalogo"/>
<div style="text-align: center;">


<ex:paginacion numeroRegistros="<%=numeroRegistros %>" href="articulos" pagina="<%=pagina %>" orden="<%=orden %>" categoria="<%=categoria %>" busqueda="<%=busqueda %>"/>
</div>
</body>
</html>