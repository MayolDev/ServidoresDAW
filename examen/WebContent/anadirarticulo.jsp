<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
      <%@ page import="javax.servlet.http.HttpSession"%>
        <%@ taglib prefix= "ex" uri = "WEB-INF/custom.tld"%>
      
    <%
    
    HttpSession sesion;
    sesion = request.getSession();
	boolean insertado;
	insertado = true;

    if((sesion.getAttribute("rol") == null )) {
    	response.sendRedirect("loginempleados");
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

<label>Introduce id</label>
<input name="idOrdenador" type="text" />
<label>Introduce tipo</label>
<input name="tipo" type="number" />
<label>Introduce Marca</label>
<input name="marca" type="text" />
<label>Introduce modelo</label>
<input name="modelo" type="text" />

<label>Introduce precio</label>
<input name='precio' type="number" step="0.01"/>

<button>Enviar</button>
</form>


</body>
</html>