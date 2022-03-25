<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="javax.servlet.http.HttpSession"%>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.util.Date" %>
 <%@ taglib prefix= "ex" uri = "WEB-INF/custom.tld"%>
<%
HttpSession sesion;
Date fechaSesion;
boolean loginError;
boolean verificationError;
loginError = false;
verificationError = false;
sesion = request.getSession();

if(sesion.getAttribute("logged") != null && (boolean)sesion.getAttribute("logged")) {
	
	response.sendRedirect("perfil");
	  
}

fechaSesion = null;
if(sesion.getAttribute("fechalogin") != null){
	fechaSesion = (Date) sesion.getAttribute("fechalogin");

}
if(sesion.getAttribute("loginError") != null ){
	
	loginError = (boolean)sesion.getAttribute("loginError");
}

if(sesion.getAttribute("verificacionError") != null && (boolean)sesion.getAttribute("verificacionError")){
	verificationError= (boolean)sesion.getAttribute("verificacionError");
}

%>

    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login</title>
</head>
<body style='background-color: #1f1f1f; color: white; font-weight: bold'>



<h1>Formulario de Login</h1>

<ex:fechalogin fechaSesion="<%=fechaSesion %>"/>
<ex:error error="<%=loginError %>" errorMessage="Email o contraseña incorrectos...">

</ex:error>
<ex:error error="<%=verificationError %>" errorMessage="Cuenta no verificada, por favor, revisa tu email...">

</ex:error>

<form action="login" method="post">
<label>Introduce correo electrónico</label>
<input type="email" name="email" />
<label>Introduce contraseña:</label>
<input type="password" name="password" />
<button>Enviar</button>

</form>
<a href="registro.jsp">Registrarse</a>

</body>
</html>