package Tags;


import javax.servlet.jsp.tagext.*;

import modelos.MOrdenador;

import javax.servlet.jsp.*;
import java.io.*;
import java.util.Base64;

public class ProductoTag extends SimpleTagSupport{

	private MOrdenador ordenador;
	private String atributo;


	public void setArticulo(MOrdenador ordenador) {
		this.ordenador = ordenador;
	}
	
	public void setAtributo(String atributo) {
		this.atributo = atributo;
	}
	

	public void doTag() throws JspException, IOException {
	      JspWriter out = getJspContext().getOut();
	      
	  	if(ordenador != null){
	  		
	  		switch(atributo) {
	  		
	  		case "titulo":
	  			out.print(ordenador.getModelo());
	  			break;
	  		case "Marca":
	  			out.print(ordenador.getMarca());
	  			break;

	  		case "pvp":
	  			out.print(ordenador.getPrecio());

	  			break;
	  		
	  		case "foto":
	  			byte[] foto = ordenador.getImagen();
	  			String bfoto;
	  			bfoto = "./image/imagen.webp";
				if(foto != null){
					bfoto = "data:image/png;base64," + Base64.getEncoder().encodeToString(foto);

				}
				out.print("<img style='border-radius: 5px' border = '0' src = '"+bfoto+"' width = '300' height = '320' >");

	  		
	  			break;
	  		default:
	  			out.print("No hay resultados para mostrar");
			


		}

	      
	      
	}
	
	
	
	
	
	
	}
	}
