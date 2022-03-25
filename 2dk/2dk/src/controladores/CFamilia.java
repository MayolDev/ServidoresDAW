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
import utils.Parseamiento;


/**
 * Servlet implementation class CFamilia
 */
@WebServlet("/familias")
public class CFamilia extends HttpServlet {
	private static final long serialVersionUID = 1L;
    HttpSession sesion;
    MFamilia familia;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		MFamilia[] familias;
		sesion = request.getSession();
		Connection con;
		int numeroRegistros;

		Parseamiento parse;
		parse = new Parseamiento();
		int pagina;
		int offset;
		offset=0;
		pagina =1;
		
		if((sesion.getAttribute("rol") != null && (int)sesion.getAttribute("rol") != MCliente.ROL_ADMINISTRATIVO) || sesion.getAttribute("rol") == null) {
			
	    	response.sendRedirect("perfil");
	    	
	    }else {
	    	
			con = (Connection) sesion.getAttribute("conexion");

			familia = new MFamilia(con);
			
			if(request.getParameter("page") != null) {
				
				try {
					
					pagina = parse.getInteger(request.getParameter("page")) ;
					
					if(pagina <1) {
						pagina = 1;
					}
					if(pagina == 1) {
						offset = 0;
					}else {
						offset = (5*pagina) -5;
					}
					
					request.setAttribute("page", pagina);
					
				}catch(NumberFormatException e) {
					pagina = 1;
					offset = 0;
					request.setAttribute("page", pagina);
					e.printStackTrace();

				}

				
			}
			
			familias = familia.mostrarCategorias("id", 5, offset);
			request.setAttribute("rs", familias);
			
			numeroRegistros = parse.getInteger(familia.getNumeroRegistros()); 
			
			request.setAttribute("numeroRegistros", numeroRegistros);
			
			request.getRequestDispatcher("familia.jsp").forward(request, response);	
	    	
	    }
		

		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		MFamilia[] familias;
		sesion = request.getSession();
		Connection con;
		int numeroRegistros;

		Parseamiento parse;
		parse = new Parseamiento();
		int pagina;
		int offset;
		offset=0;
		pagina =1;
		
		if((sesion.getAttribute("rol") != null && (int)sesion.getAttribute("rol") != MCliente.ROL_ADMINISTRATIVO) || sesion.getAttribute("rol") == null) {
			
	    	response.sendRedirect("perfil");
	    	
	    }else {
	    	
	    	con = (Connection) sesion.getAttribute("conexion");

			familia = new MFamilia(con);
			
			if(request.getParameter("page") != null) {
				
				try {
					pagina = parse.getInteger(request.getParameter("page")) ;
					if(pagina <1) {
						pagina = 1;
					}
					if(pagina == 1) {
						offset = 0;
					}else {
						offset = (5*pagina) -5;
					}
					
					request.setAttribute("page", pagina);
					
				}catch(NumberFormatException e) {
					pagina = 1;
					offset = 0;
					request.setAttribute("page", pagina);
					e.printStackTrace();

				}

				
			}
			
			familias = familia.mostrarCategorias("id", 5, offset);
			
			request.setAttribute("rs", familias);
			numeroRegistros = parse.getInteger(familia.getNumeroRegistros()); 
			
			request.setAttribute("numeroRegistros", numeroRegistros);
			
			if(request.getAttribute("DeleteError") != null && (boolean) request.getAttribute("DeleteError")) {
				
				request.setAttribute("DeleteError", true);
			}
			
			request.getRequestDispatcher("familia.jsp").forward(request, response);
			
	    	
	    }
		
		
		
		
	}

}
