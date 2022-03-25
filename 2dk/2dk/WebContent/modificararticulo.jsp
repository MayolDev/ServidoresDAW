<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.math.BigDecimal" %>
  <%@ page import="javax.servlet.http.HttpSession"%>
   <%@ page import="modelos2dk.MSubfamilia" %>
 
 
 <%

HttpSession sesion;
sesion = request.getSession();
MSubfamilia[] subfamilias;


if((sesion.getAttribute("rol") != null && (int)sesion.getAttribute("rol") != 1) || sesion.getAttribute("rol") == null) {
	response.sendRedirect("perfil");
}


if(request.getParameter("codigo") == null){
	
	response.sendRedirect("articulos");
}

String codigo, ean, nombre, descripcion;
int tipoiva,stock, disponible, minimimo, subfamilia;
BigDecimal pvp, pcd;


codigo = (String)request.getAttribute("codigo");
ean = (String)request.getAttribute("ean");
nombre = (String)request.getAttribute("nombre");
descripcion = (String)request.getAttribute("descripcion");

tipoiva = (int) request.getAttribute("tipoiva");
stock = (int) request.getAttribute("stock");
disponible = (int) request.getAttribute("disponible");
minimimo = (int) request.getAttribute("minimimo");
subfamilia = (int) request.getAttribute("subfamilia");
pvp = (BigDecimal) request.getAttribute("pvp");
pcd = (BigDecimal) request.getAttribute("pcd");
request.setAttribute("codigo",codigo);


%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css"
	href="/2dk/css/style.css" />
<title>Modificar Articulo</title>
</head>
<body>


<form action="modificararticulo" method="post">
<input type='hidden' value="<%=codigo %>" name='codigo' />
<label>Introduce ean</label>
<input name="ean" type="text" value="<%=ean %>" />
<label>Introduce nombre</label>
<input name="nombre" type="text" value="<%=nombre %>"/>
<label>Introduce descripcion</label>
<input name="descripcion" type="text" value="<%=descripcion %>"/>
<label>Introduce tipoIva</label>
<select name="tipoiva">
<option value = "<%=tipoiva %>" selected> <%=tipoiva %> </option> 
<option value="1">Reducido</option>
<option value="2">Normal</option>
</select>
<label>Introduce PCD</label>
<input name="pcd" type="number"  value="<%=pcd %>"/>
<label>Introduce pvp</label>
<input name=pvp type="number" value="<%=pvp %>" />
<label>Introduce stock</label>
<input name="stock" type="number" value="<%=stock %>" />
<label>Introduce disponible</label>
<select name="disponible" >
<option value="<%=disponible %>" selected> <%=disponible %></option>
<option value="1"> disponible </option>
<option value="0"> No disponible </option>
</select>
<label>Introduce Minimimo</label>
<input name="minimimo" type="number" value="<%=minimimo %>" />
<label>Introduce subfamilia</label>
<%



if (request.getAttribute("rs")!=null){
	
	subfamilias = (MSubfamilia[])request.getAttribute("rs");

	out.print("<select name='subfamilia'>");
	out.print("<option value='"+subfamilia+"' selected>"+subfamilia+"</option>");
	int indice = 0;
	while(indice < subfamilias.length){
		if(subfamilias[indice] != null)
		out.print("<option value='"+subfamilias[indice].getId()+"'> "+subfamilias[indice].getNombre() +"</option>");
	
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