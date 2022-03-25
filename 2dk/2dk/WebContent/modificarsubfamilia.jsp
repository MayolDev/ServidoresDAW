<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
      <%@ page import="javax.servlet.http.HttpSession"%>
         <%@ page import="modelos2dk.MFamilia" %>
      
   <%

HttpSession sesion;
sesion = request.getSession();
MFamilia[] familias;

if((sesion.getAttribute("rol") != null && (int)sesion.getAttribute("rol") != 1) || sesion.getAttribute("rol") == null) {
	response.sendRedirect("perfil");
}



if(request.getParameter("id") == null){
	
	response.sendRedirect("articulos");
}

String nombre;
int id, familia;



id = (int)request.getAttribute("id");
nombre = (String)request.getAttribute("nombre");
familia = (int)request.getAttribute("familia");



%> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css"
	href="/2dk/css/style.css" />
<title>Modificar subfamilia</title>
</head>
<body>

<form action="modificarsubfamilia" method="post">
<input type='hidden' value="<%=id %>" name='id' />
<h1>Añadir SUBfamilias</h1>
<label>Introduce nombre</label>
<input name="nombre" type="text" value="<%=nombre %>" />
<label>Familia</label>
<%


if (request.getAttribute("rs")!=null){
	
	familias = (MFamilia[])request.getAttribute("rs");

	out.print("<select name='familia'>");
	out.print("<option value='"+familia+"' selected>"+familia+"</option>");
	int indice = 0;
	while(indice < familias.length){
		
		if(familias[indice] != null)
		out.print("<option value='"+familias[indice].getId()+"'> "+familias[indice].getNombre() +"</option>");
	
		indice++;
	}
	out.print("</select><button>Enviar</button>");

}else{
	out.print("<small style='color:red'> NO SE PUEDE CREAR UNA SUBFAMILIA SI NO EXISTEN FAMILIAS</small>");
}

%>


</form>
</body>
</html>