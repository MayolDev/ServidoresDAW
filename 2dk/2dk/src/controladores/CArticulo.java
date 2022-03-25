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
import utils.Parseamiento;


@WebServlet("/articulos")
public class CArticulo extends HttpServlet {
	private static final long serialVersionUID = 1L;
    HttpSession sesion;
    MArticulo articulo;



	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		sesion = request.getSession();
		
		if((sesion.getAttribute("rol") != null && (int)sesion.getAttribute("rol") != MCliente.ROL_ADMINISTRATIVO) || sesion.getAttribute("rol") == null) {
			
	    	response.sendRedirect("perfil");
	    	
	    }else {
	    	
			doPost(request, response);

	    }
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		sesion = request.getSession();
		Connection con;
		MArticulo[] articulos;
		Parseamiento parse;
		int numeroRegistros;
		parse = new Parseamiento();
		int pagina;
		int offset;
		offset=0;
		pagina =1;
		
		con = (Connection) sesion.getAttribute("conexion");

		if((sesion.getAttribute("rol") != null && (int)sesion.getAttribute("rol") != MCliente.ROL_ADMINISTRATIVO) || sesion.getAttribute("rol") == null) {
			
	    	response.sendRedirect("perfil");
	    	
	    }else {
	    	
	    	articulo = new MArticulo(con);
			
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
			
			
			articulos = new MArticulo[5];
			articulos = articulo.mostrarTodosArticulos("codigo", 5, offset);
			request.setAttribute("rs", articulos);
			
			numeroRegistros = parse.getInteger(articulo.getNumeroRegistros()); 
			
			request.setAttribute("numeroRegistros", numeroRegistros);
			
			if(request.getAttribute("DeleteError") != null && (boolean) request.getAttribute("DeleteError")) {
				
				request.setAttribute("DeleteError", true);
			}
			
			request.getRequestDispatcher("articulo.jsp").forward(request, response);
			
		}
	    }
		
		

}
