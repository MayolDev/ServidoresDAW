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
import utils.Parseamiento;


@WebServlet("/modificarfamilia")
public class CModificarFamilia extends HttpServlet {
	private static final long serialVersionUID = 1L;
	HttpSession sesion;
	MFamilia familia;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Parseamiento parse;
		parse = new Parseamiento();
		int id;
		String nombre;
		sesion = request.getSession();
		
		if((sesion.getAttribute("rol") != null && (int)sesion.getAttribute("rol") != MCliente.ROL_ADMINISTRATIVO) || sesion.getAttribute("rol") == null) {
			
	    	response.sendRedirect("perfil");
	    }else {
	    	
	    	
	    	if(request.getParameter("id") != null) {
	    		
				id=parse.getInteger(request.getParameter("id")) ;
				nombre= request.getParameter("nombre");
				
				request.setAttribute("id", id);
				request.setAttribute("nombre", nombre);
				
				request.getRequestDispatcher("modificarfamilia.jsp").forward(request, response);

				
				
			}else {
				response.sendRedirect("familias");
			}
			
	    	
	    	
	    	
	    }
		
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Parseamiento parse;
		parse = new Parseamiento();
		int id;
		String nombre;
		Connection con;
		sesion = request.getSession();
		Comprobaciones comprobacion;
		comprobacion = new Comprobaciones();
		
		if((sesion.getAttribute("rol") != null && (int)sesion.getAttribute("rol") != MCliente.ROL_ADMINISTRATIVO) || sesion.getAttribute("rol") == null) {
			
	    	response.sendRedirect("perfil");
	    	
	    }else {
	    	
	    	
	    	
	    	con = (Connection) sesion.getAttribute("conexion");
			familia = new MFamilia(con);
			
			id = parse.getInteger(request.getParameter("id"));
			nombre = request.getParameter("nombre");
			
			if(nombre.contentEquals("") || !comprobacion.checkStringBetween(nombre, 1, 25)) {
				
	    		response.sendRedirect("familias");
	    		
	    	}else {
	    		
				familia.setId(id);
				familia.setNombre(nombre);
				
				familia.ActualizarCategoria();
				
				response.sendRedirect("familias");
	    	}
			
			

	    	
	    }
		
		
		
		
		
		
	}

}
