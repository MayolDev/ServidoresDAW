<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  <%@ page import="javax.servlet.http.HttpSession"%>
<%

HttpSession sesion;
sesion = request.getSession();


if((sesion.getAttribute("rol") != null && (int)sesion.getAttribute("rol") != 1) || sesion.getAttribute("rol") == null) {
	response.sendRedirect("perfil");
}



if(request.getParameter("id") == null){
	
	response.sendRedirect("familias");
}


int id;
String nombre;

id= (int) request.getAttribute("id");
nombre = (String) request.getAttribute("nombre");


%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Modificar familia</title>
</head>
<body>


<form action="modificarfamilia" method="post">
<h1>Añadir familias</h1>
<input type='hidden' value="<%=id %>" name='id' />

<label>Introduce nombre</label>
<input name="nombre" type="text"  value="<%=nombre  %>"/>
<button>Enviar</button>
</form>

</body>
</html>