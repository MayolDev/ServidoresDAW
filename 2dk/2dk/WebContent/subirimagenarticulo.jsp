<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%
String codigo;
codigo = "";
if(request.getParameter("codigo") != null || !request.getParameter("codigo").contains("")){
	codigo = request.getParameter("codigo");
}else {
	response.sendRedirect("articulos");
}


%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Subir imagen de articulo</title>
</head>
<body>

<h1>Subir imagen de articulo <%=codigo %></h1>
<form action="subirimagenarticulo" method="post" enctype="multipart/form-data">
 <input type=file size=60 name="file" value="Examinar"><br><br>
 <input  type="hidden" value="<% out.println(codigo); %>" name="codigo"  />
 <input type=submit value="subir"><br>
</form>
</body>
</html>