package controladores;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import modelos2dk.MCliente;
import modelos2dk.MFamilia;
import utils.Comprobaciones;


@WebServlet("/anadirfamilia")
public class CAnadirFamilia extends HttpServlet {
	private static final long serialVersionUID = 1L;
    HttpSession sesion;
	MFamilia familia;


	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		sesion = request.getSession();
		
		 if((sesion.getAttribute("rol") != null && (int)sesion.getAttribute("rol") != MCliente.ROL_ADMINISTRATIVO) || sesion.getAttribute("rol") == null) {
			 
		    	response.sendRedirect("perfil");
		    	
		    }else {
		    	
				response.sendRedirect("anadirfamilia.jsp");

		    }
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection con;
		sesion = request.getSession();
		con = (Connection)sesion.getAttribute("conexion");
		familia = new MFamilia(con);
		String nombre;
		Comprobaciones comprobacion;
		comprobacion = new Comprobaciones();
		
		if((sesion.getAttribute("rol") != null && (int)sesion.getAttribute("rol") != MCliente.ROL_ADMINISTRATIVO) || sesion.getAttribute("rol") == null) {
			
	    	response.sendRedirect("perfil");
	    	
	    }else {
	    	
	    	nombre = request.getParameter("nombre");
	    	
	    	if(nombre.contentEquals("") || !comprobacion.checkStringBetween(nombre, 1, 25)) {
	    		
	    		response.sendRedirect("familias");
	    		
	    	}else {

				familia.setId(familia.UltimoId() + 1);
				familia.setNombre(nombre);
				
				if(familia.InsertarCategoria() != -1) {
					
					request.setAttribute("insertado", true);
					
				}else {
					
					request.setAttribute("insertado", false);

				}
				
				request.getRequestDispatcher("anadirfamilia.jsp").forward(request, response);
	    	}
	    	
			
			
		}
	    }
		
		

}
