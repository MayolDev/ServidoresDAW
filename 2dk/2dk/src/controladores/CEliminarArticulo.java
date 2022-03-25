package controladores;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import modelos2dk.MArticulo;
import modelos2dk.MCliente;


@WebServlet("/eliminararticulo")
public class CEliminarArticulo extends HttpServlet {
	private static final long serialVersionUID = 1L;
	HttpSession sesion;
	MArticulo articulo;
       
 


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		sesion = request.getSession();
		
		if((sesion.getAttribute("rol") != null && (int)sesion.getAttribute("rol") != MCliente.ROL_ADMINISTRATIVO) || sesion.getAttribute("rol") == null) {
			
	    	response.sendRedirect("perfil");
	    	
	    }else {
	    	
			response.sendRedirect("articulos");

	    }
		
		
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Connection con;
		String codigo;
		sesion = request.getSession();
		
		if((sesion.getAttribute("rol") != null && (int)sesion.getAttribute("rol") != MCliente.ROL_ADMINISTRATIVO) || sesion.getAttribute("rol") == null) {
			
	    	response.sendRedirect("perfil");
	    	
	    }else {
	    	
	    	con = (Connection) sesion.getAttribute("conexion");
			
			articulo = new MArticulo(con);
			
			codigo = request.getParameter("idArticulo");
			articulo.setCodigo(codigo);
			int filas = articulo.EliminarArticulo();

			if(filas != -1) {
				response.sendRedirect("articulos");

			}else {
				request.setAttribute("DeleteError", true);
				request.getRequestDispatcher("articulos").forward(request, response);
			}
			
			
		}
	    }
		
	

}
