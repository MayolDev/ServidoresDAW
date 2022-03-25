<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
HttpSession sesion; 
sesion = request.getSession();

if(sesion.getAttribute("logged") == null || !(boolean)sesion.getAttribute("logged")) {
	
	response.sendRedirect("login");
	  
}
if(request.getAttribute("correo") == null){
	response.sendRedirect("editarperfil");
}


boolean error;
error = false;
String nombre,documento, direccion, direccion1, codigoPostal, ciudad, localidad, telefono;

nombre = "";
documento = "";
direccion="";
direccion1="";
codigoPostal="";
ciudad="";
localidad="";
telefono="";

if(request.getAttribute("nombre") != null && !((String)request.getAttribute("nombre")).contentEquals("null") ){
	nombre = (String)request.getAttribute("nombre");
}
if(request.getAttribute("documento") != null && !((String)request.getAttribute("documento")).contentEquals("null") ){
	documento = (String)request.getAttribute("documento");
}
if(request.getAttribute("direccion") != null && !((String)request.getAttribute("direccion")).contentEquals("null") ){
	direccion = (String)request.getAttribute("direccion");
}
if(request.getAttribute("direccion1") != null && !((String)request.getAttribute("direccion1")).contentEquals("null") ){
	direccion1 = (String)request.getAttribute("direccion1");
}
if(request.getAttribute("cp") != null && !((String)request.getAttribute("cp")).contentEquals("null") ){
	codigoPostal = (String)request.getAttribute("cp");
}
if(request.getAttribute("ciudad") != null && !((String)request.getAttribute("ciudad")).contentEquals("null") ){
	ciudad = (String)request.getAttribute("ciudad");
}
if(request.getAttribute("localidad") != null && !((String)request.getAttribute("localidad")).contentEquals("null") ){
	localidad = (String)request.getAttribute("localidad");
}
if(request.getAttribute("telefono") != null && !((String)request.getAttribute("telefono")).contentEquals("null") ){
	telefono = (String)request.getAttribute("telefono");
}



%>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Editar perfil</title>
</head>
<body>

<h1>Editar perfil</h1>

<form action="editarperfil" method="post">

<div>
<label>Nombre:</label>
<input type="text" name="nombre" value="<%=nombre %>" />
</div>

<div>
<label>Documento:</label>
<input type="text" name="documento" value="<%=documento %>" />
</div>
<div>
<label>Direccion:</label>
<input type="text" name="direccion" value="<%=direccion %>" />
</div>
<div>
<label>Direccion adicional:</label>
<input type="text" name="direccion1" value="<%=direccion1 %>" />
</div>
<div>
<label>Codigo postal:</label>
<input type="number" name="cp" value="<%=codigoPostal %>" />
</div>
<div>
<label>Localidad:</label>
<input type="text" name="localidad" value="<%=localidad %>" />
</div>
<div>
<label>Ciudad:</label>
<input type="text" name="ciudad" value="<%=ciudad %>" />
</div>
<div>
<label>Telefono:</label>
<input type="text" name="telefono" value="<%=telefono %>" />
</div>

<button>Enviar</button>
</form>



</body>
</html>