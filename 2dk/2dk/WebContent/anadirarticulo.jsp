<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
      <%@ page import="javax.servlet.http.HttpSession"%>
        <%@ page import="modelos2dk.MSubfamilia" %>
        <%@ taglib prefix= "ex" uri = "WEB-INF/custom.tld"%>
      
    <%
    
    HttpSession sesion;
    MSubfamilia[] subfamilias;
    sesion = request.getSession();
	boolean insertado;
    subfamilias = null;
	insertado = true;

    if((sesion.getAttribute("rol") != null && (int)sesion.getAttribute("rol") != 1) || sesion.getAttribute("rol") == null) {
    	response.sendRedirect("perfil");
    }
    
    if (request.getAttribute("rs")!=null){
    	
    	subfamilias = (MSubfamilia[])request.getAttribute("rs");
    }
    
    if(request.getAttribute("insertado") != null){
    	insertado = (boolean) request.getAttribute("insertado");    
    }
    
    %>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css"
	href="/2dk/css/style.css" />
<meta charset="UTF-8">
<title>Añadir articulo</title>
</head>
<body>


<ex:error errorMessage= "Error al insertar el articulo" error="<%=!insertado %>" >
</ex:error>

<h1>Añadir Articulo</h1>
<form action="anadirarticulo" method="post">

<label>Introduce codigo</label>
<input name="codigo" type="text" />
<label>Introduce ean</label>
<input name="ean" type="text" />
<label>Introduce nombre</label>
<input name="nombre" type="text" />
<label>Introduce descripcion</label>
<input name="descripcion" type="text" />
<label>Introduce tipoIva</label>
<select name="tipoiva">
<option value="1">Reducido</option>
<option value="2">Normal</option>
</select>
<label>Introduce PCD</label>
<input name="pcd" type="number" step="0.01"/>
<label>Introduce pvp</label>
<input name=pvp type="number" step="0.01"/>
<label>Introduce stock</label>
<input name="stock" type="number" />
<label>Introduce disponible</label>
<select name="disponible">
<option value="1"> disponible </option>
<option value="0"> No disponible </option>
</select>
<label>Introduce Minimimo</label>
<input name="minimimo" type="number" />
<label>Introduce subfamilia</label>

<ex:Subfamilia subfamilias = "<%=subfamilias %>" tipo="formulario" />
<button>Enviar</button>
</form>


</body>
</html>