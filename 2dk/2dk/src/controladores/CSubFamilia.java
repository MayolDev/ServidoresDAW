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
import modelos2dk.MSubfamilia;
import utils.Parseamiento;


@WebServlet("/subfamilias")
public class CSubFamilia extends HttpServlet {
	private static final long serialVersionUID = 1L;
    HttpSession sesion;
    MSubfamilia subfamilia;



	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		sesion = request.getSession();
		
		if((sesion.getAttribute("rol") != null && (int)sesion.getAttribute("rol") != MCliente.ROL_ADMINISTRATIVO) || sesion.getAttribute("rol") == null) {
			
	    	response.sendRedirect("perfil");
	    	
	    }else {
	    	
			doPost(request,response);

	    }
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		sesion = request.getSession();
		MSubfamilia[] subfamilias;
		int numeroRegistros;
		Connection con;
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

			subfamilia = new MSubfamilia(con);
			
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
			
			subfamilias = subfamilia.mostrarCategorias("id", 5, offset);
			request.setAttribute("rs", subfamilias);
			
			numeroRegistros = parse.getInteger(subfamilia.getNumeroRegistros()); 
			
			request.setAttribute("numeroRegistros", numeroRegistros);
			
			if(request.getAttribute("DeleteError") != null && (boolean) request.getAttribute("DeleteError")) {
				
				request.setAttribute("DeleteError", true);
			}
			
			request.getRequestDispatcher("subfamilia.jsp").forward(request, response);
	    	
	    }
		
		
		
	}

}
