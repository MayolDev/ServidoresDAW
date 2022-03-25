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
import utils.Comprobaciones;
import utils.Parseamiento;


@WebServlet("/anadirsubfamilia")
public class CAnadirSubFamilia extends HttpServlet {
	private static final long serialVersionUID = 1L;
    HttpSession sesion;
    MSubfamilia subfamilia;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    MFamilia familia;
	    MFamilia[] familias;
	    String numRegistros;
	    Parseamiento parse;
	    parse = new Parseamiento();
	    Connection con;
	    sesion = request.getSession();
	    
	    if((sesion.getAttribute("rol") != null && (int)sesion.getAttribute("rol") != MCliente.ROL_ADMINISTRATIVO) || sesion.getAttribute("rol") == null) {
	    	
	    	response.sendRedirect("perfil");
	    	
	    }else {
	    	
		    con = (Connection) sesion.getAttribute("conexion");
		    familia = new MFamilia(con);
		    
		    numRegistros= familia.getNumeroRegistros();
		    
		    familias = familia.mostrarCategorias("id", parse.getInteger(numRegistros) , 0);
		    request.setAttribute("rs", familias);
		    
		    request.getRequestDispatcher("anadirsubfamilia.jsp").forward(request, response);
	    	
	    }
	    
	    

	    
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Connection con;
		sesion = request.getSession();
		con = (Connection)sesion.getAttribute("conexion");
		subfamilia = new MSubfamilia(con);
		Parseamiento parse;
		parse = new Parseamiento();
		String nombre; 
		int idFamilia;
		Comprobaciones comprobacion;
		comprobacion = new Comprobaciones();
		
		if((sesion.getAttribute("rol") != null && (int)sesion.getAttribute("rol") != MCliente.ROL_ADMINISTRATIVO) || sesion.getAttribute("rol") == null) {
			
	    	response.sendRedirect("perfil");
	    	
	    }else {
	    	
			nombre = request.getParameter("nombre");
			
			if(comprobacion.checkInteger(request.getParameter("familia"))) {
				
				idFamilia = parse.getInteger((request.getParameter("familia")));

			}else {
				
				idFamilia = 0;
			}
			
			if(nombre.contentEquals("") || !comprobacion.checkStringBetween(nombre, 1, 25)) {
				
				request.setAttribute("insertado", true);
				request.getRequestDispatcher("anadirsubfamilia.jsp").forward(request, response);

	    	}else {
	    		
	    		subfamilia.setId(subfamilia.UltimoId() + 1);
				subfamilia.setNombre(nombre);
				subfamilia.setIdFamilia(idFamilia);
				
				if(subfamilia.InsertarCategoria() != -1) {
					
					request.setAttribute("insertado", true);
					
				}else {
					
					request.setAttribute("insertado", false);

				}
				

				response.sendRedirect("anadirsubfamilia");

	    		
	    	}
			
			
	    }
		

		
		
	}

}
