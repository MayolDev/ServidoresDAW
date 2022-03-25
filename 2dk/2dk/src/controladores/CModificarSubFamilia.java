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
import modelos2dk.MSubfamilia;
import utils.Parseamiento;

/**
 * Servlet implementation class CModificarSubFamilia
 */
@WebServlet("/modificarsubfamilia")
public class CModificarSubFamilia extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    HttpSession sesion;
    MSubfamilia subfamilia;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    MFamilia familia;
	    MFamilia[] familias;
	    int numRegistros;
	    Connection con;
		Parseamiento parse;
		parse = new Parseamiento();
		
		int id, idFamilia;
		String nombre;
		
		sesion = request.getSession();
		if((sesion.getAttribute("rol") != null && (int)sesion.getAttribute("rol") != MCliente.ROL_ADMINISTRATIVO) || sesion.getAttribute("rol") == null) {
			
	    	response.sendRedirect("perfil");
	    	
	    }else {
	    	
	    	if(request.getParameter("id") != null) {
				id = parse.getInteger(request.getParameter("id")) ;
				nombre = request.getParameter("nombre");
				idFamilia = parse.getInteger(request.getParameter("familia"));
				
				request.setAttribute("id", id);
				request.setAttribute("familia", idFamilia);
				request.setAttribute("nombre", nombre);
				    
				con = (Connection) sesion.getAttribute("conexion");
				familia = new MFamilia(con);
				    
				numRegistros = parse.getInteger(familia.getNumeroRegistros());
				familias = familia.mostrarCategorias("id", numRegistros, 0);
				request.setAttribute("rs", familias);
				    			
				
				request.getRequestDispatcher("modificarsubfamilia.jsp").forward(request, response);

			}else {
				response.sendRedirect("subfamilias");
			}
			
	    	
	    }
		
		

		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection con;
		sesion = request.getSession();
		con =  (Connection)sesion.getAttribute("conexion");
		Parseamiento parse;
		parse = new Parseamiento();
		String nombre;
		int id, idFamilia;
		
		if((sesion.getAttribute("rol") != null && (int)sesion.getAttribute("rol") != MCliente.ROL_ADMINISTRATIVO) || sesion.getAttribute("rol") == null) {
			
	    	response.sendRedirect("perfil");
	    	
	    }else {
	    
	    	
	    	id = parse.getInteger(request.getParameter("id")) ;
			nombre = request.getParameter("nombre");
			idFamilia = parse.getInteger(request.getParameter("familia"));
			
			subfamilia = new MSubfamilia(con);
			
			subfamilia.setId(id);
			subfamilia.setNombre(nombre);
			subfamilia.setIdFamilia(idFamilia);
			
			subfamilia.ActualizarCategoria();
			response.sendRedirect("subfamilias");
	    }
		
		

	}

}
