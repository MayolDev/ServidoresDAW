<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  <%@ page import="javax.servlet.http.HttpSession"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Registro</title>
</head>
<body style='background-color: #1f1f1f; color: white; font-weight: bold'>

<% 
HttpSession sesion; 
sesion = request.getSession();


if(sesion.getAttribute("logged") != null && (boolean)sesion.getAttribute("logged")) {
	
	response.sendRedirect("perfil");
	  
}



boolean error;
error = false;
String nombre,dni, email, direccion, apellidos, categoria, fechanac;

nombre = "";
apellidos = "";
dni = "";
email="";
direccion="";
categoria = "";
fechanac = "";


if(sesion.getAttribute("passwdError") != null || sesion.getAttribute("nombreError") != null || sesion.getAttribute("documentoError") != null || sesion.getAttribute("correoError") != null){
	if((boolean)sesion.getAttribute("passwdError") || (boolean)sesion.getAttribute("nombreError") || (boolean)sesion.getAttribute("documentoError") || (boolean)sesion.getAttribute("correoError")    ){
		error = true;
	}
	
	
}

if(error){
	if(request.getAttribute("nombre") != null){
		nombre = (String)request.getAttribute("nombre");
	}
	if(request.getAttribute("dni") != null){
		dni = (String)request.getAttribute("dni");
	}
	if(request.getAttribute("email") != null){
		email = (String)request.getAttribute("email");
	}
	if(request.getAttribute("direccion") != null){
		direccion = (String)request.getAttribute("direccion");
	}
	if(request.getAttribute("apellidos") != null){
		apellidos = (String)request.getAttribute("apellidos");
	}
	if(request.getAttribute("categoria") != null){
		categoria = (String)request.getAttribute("categoria");
	}
	if(request.getAttribute("fechanac") != null){
		fechanac = (String)request.getAttribute("fechanac");
	}

}



%>

<h1 style='text-align: center'>Formulario de registro</h1>
<form style='display:flex; flex-direction:column; justify-content: center; align-items: center; padding: 10px' action= 'registro' method='post'>
<%
if(sesion.getAttribute("nombreError") != null){
	if((boolean)sesion.getAttribute("nombreError")){
		out.print("<small Style='color:red'>** Nombre incorrecto o nulo ** </small>");
	}
}

%>
<label>Nombre </label>
<input type='text' name='nombre' value= '<%=nombre %>'  />

<%
if(sesion.getAttribute("cpError") != null){
	if((boolean)sesion.getAttribute("cpError")){
		out.print("<small Style='color:red'>** CP incorrecto ** </small>");
	}
}

%>
<label>Apellidos</label>
<input type='text' name='apellidos' value= '<%=apellidos%>'/>


<%
if(sesion.getAttribute("documentoError") != null){
	if((boolean)sesion.getAttribute("documentoError")){
		out.print("<small Style='color:red'>** Documento incorrecto o nulo ** </small>");
	}
}

%>
<label>Documento de identificacion</label>
<input type='text' name='documento' value= '<%=dni%>'/>
<%
if(sesion.getAttribute("correoError") != null){
	if((boolean)sesion.getAttribute("correoError")){
		out.print("<small Style='color:red'>** Email incorrecto o nulo ** </small>");
	}
}

%>
<label>Correo electrónico</label>
<input type='email' name='email' value= '<%=email%> '/>
<%
if(sesion.getAttribute("direccionError") != null){
	if((boolean)sesion.getAttribute("direccionError")){
		out.print("<small Style='color:red'>** direccion incorrecta ** </small>");
	}
}

%>
<label>Direccion</label>
<input type='text' name='direccion' value= '<%=direccion%>'  />

<%
if(sesion.getAttribute("ciudadError") != null){
	if((boolean)sesion.getAttribute("ciudadError")){
		out.print("<small Style='color:red'>** Ciudad incorrecta  ** </small>");
	}
}

%>
<label>Categoria</label>
<input type='number' name='categoria' value= '<%=categoria%>'/>
<%
if(sesion.getAttribute("localidadError") != null){
	if((boolean)sesion.getAttribute("localidadError")){
		out.print("<small Style='color:red'>** Localidad incorrecta ** </small>");
	}
}

%>
<label>fecha</label>
<input type='date' name='fechanac' value= '<%=fechanac%>'/>

<%
if(sesion.getAttribute("telefonoError") != null){
	if((boolean)sesion.getAttribute("correoError")){
		out.print("<small Style='color:red'>** Telefono incorrecto  ** </small>");
	}
}

%>






<%
if(sesion.getAttribute("passwdError") != null){
	if((boolean)sesion.getAttribute("passwdError")){
		out.print("<small Style='color:red'>** Contraseñas no coinciden, vuelva a escribirlas ** </small>");
	}
}

%>



<label>Contraseña</label>
<input type='password' name='contrasena' />
<label>Vuelva a introducir la contraseña</label>
<input type='password' name='contrasena2' />
<button>Enviar</button>
</form>
</body>
</html>