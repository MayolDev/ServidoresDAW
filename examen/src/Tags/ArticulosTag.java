package Tags;


import javax.servlet.jsp.tagext.*;

import modelos.MOrdenador;

import javax.servlet.jsp.*;
import java.io.*;
import java.util.Base64;

public class ArticulosTag extends SimpleTagSupport{

	private MOrdenador[] ordenadores;
	private String tipo;


	public void setArticulos(MOrdenador[] ordenadores) {
		this.ordenadores = ordenadores;
	}
	
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	

	public void doTag() throws JspException, IOException {
	      JspWriter out = getJspContext().getOut();
	      
	      

	      if(ordenadores != null && tipo != null) {
	    	  
	    	  
	    	  switch(tipo) {
	    	  
	    	  case "tabla":
	    		  
	    		  out.print("		<table class=\"styled-table\">\n"
	    		  		+ "			<thead>\n"
	    		  		+ "				<tr>\n"
	    		  		+ "					<th>id</th>\n"
	    		  		+ "					<th>tipo</th>\n"
	    		  		+ "					<th>marca</th>\n"
	    		  		+ "					<th>modelo</th>\n"
	    		  		+ "					<th>precio</th>\n"
	    		  		
	    		  		+ "					<th>&nbsp;</th>\n"
	    		  		+ "					<th>&nbsp;</th>\n"
	    		  		+ "					<th>&nbsp;</th>\n"
	    		  		+ "				</tr>\n"
	    		  		+ "			</thead>\n"
	    		  		+ "			<tbody>");
	    		  
	    		  for(int i = 0; i < (ordenadores.length -1); i++)
	    		  {
	    		  	
	    		  if(ordenadores[i] != null){
	    		  		

	    		  	out.println("<tr>");
	    		  	out.println("<td>"+ordenadores[i].getIdOrdenador()+"</td>");
	    		  	out.println("<td>"+ordenadores[i].getTipo()+"</td>");
	    		  	out.println("<td>"+ordenadores[i].getMarca()+"</td>");
	    		  	out.println("<td>"+ordenadores[i].getModelo()+"</td>");

	    		  	
	    		  	out.println("<td>"+ordenadores[i].getPrecio()+"</td>");
	    		  	out.println("<td>");
	    		  	out.println("<form method='post' action='eliminararticulo'>");
	    		  	out.println("<input type='submit' value='Borrar'/>");
	    		  	out.println("<input type='hidden' value='" + ordenadores[i].getIdOrdenador() + "' name='idArticulo' />");
	    		  	out.println("</form></td>");
	    		  	
	    		  	out.println("</td>");
	    		  	out.println("<td>");
	    		  	out.println("<form method='get' action='modificararticulo'>");
	    		  	out.println("<input type='submit' value='Modificar'/>");
	    		  	out.println("<input type='hidden' value='" + ordenadores[i].getIdOrdenador()+ "' name='codigo' />");
	    		  	out.println("<input type='hidden' value='" + ordenadores[i].getTipo() + "' name='ean' />");
	    		  	out.println("<input type='hidden' value='" +ordenadores[i].getMarca() + "' name='nombre' />");
	    		  	out.println("<input type='hidden' value='" + ordenadores[i].getModelo() + "' name='descripcion' />");
	    		  	out.println("<input type='hidden' value='" + ordenadores[i].getPrecio() + "' name='tipoiva' />");

	    		  	out.println("</form></td>");
	    		  	out.println("<td>");
	    		  	out.println("<form method='get' action='subirimagenarticulo.jsp'>");
	    		  	out.println("<input type='submit' value='Subir Imagen'/>");
	    		  	out.println("<input type='hidden' value='" + ordenadores[i].getIdOrdenador()+ "' name='codigo' />");
	    		  	out.println("</form></td>");
	    		  	out.println("</tr>");

	    		  	
	    		  	}

	    		  	
	    		  	
	    		  }
	    		  
	    		  break;
	    		  
	    	  case "catalogo":
	    		  byte[] foto;
	    		  String bfoto;
	    		  
	    		  out.print("<div class=\"articulos\" style=\"display:flex; flex-wrap: wrap; justify-content: center; align-items:center; \">\n"
	    		  		+ "");
	    		  for(int i = 0; i < (ordenadores.length); i++)
	    		  {
	    		  	
	    		  	if(ordenadores[i] != null){
	    		  		
	    		  		foto = ordenadores[i].getImagen();
	    		  		if(foto != null){
	    		  		bfoto = "data:image/png;base64," + Base64.getEncoder().encodeToString(foto);
	    		  		}else{
	    		  			bfoto = "./image/imagen.webp";
	    		  		}
	    		  		out.println("<div class='articulo' style='width: 200px; margin: 10px; border-radius: 5px; padding: 10px; background-color:#d1d1d1; '>");
	    		  		out.println("<a style='display:flex; width: 100%; heigth: 100%; justify-content: center; align-items:center; flex-direction: column ' href='producto?codigo="+ordenadores[i].getIdOrdenador()+"'>");
	    		  		if(bfoto != null)
	    		  		out.println("<img style='border-radius: 5px' border = '0' src = '"+ bfoto +"' width = '100' height = '120' >");
	    		  		out.println("<h3>"+ordenadores[i].getModelo()+"</h3>");
	    		  		out.println("<p>Precio : "+ ordenadores[i].getPrecio() + "€ </p>");
	    		  		out.println("<small>Marca:  "+ ordenadores[i].getMarca() +" </small>");
	    		  		out.println("</a>");

	    		  		out.println("</div>");
	    		  	}


	    		  }
	    		  out.print("</div>");
	    		  
	    		  
	    		  break;
	    	  
	    	  default:
	    		  out.print("<small>No hay resultados para mostar</small>");
	    	  	
	    	  }
	    	  
	    	  
	    	  
  		  	out.println("</tbody>"
		  			+ "</table>");
	    	
	      }
	      
	      
	}
	
	
	
	
	
	
}
