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
import modelos2dk.MSubfamilia;
import utils.Parseamiento;


@WebServlet("/articulos2dk")
public class CArticulosClientes extends HttpServlet {
	private static final long serialVersionUID = 1L;
    HttpSession sesion;
    MArticulo articulo;
    MSubfamilia subfamilia;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		sesion = request.getSession();
		
		if(sesion.getAttribute("logged") == null) {
			
			response.sendRedirect("login");
			
		}else if(!(boolean)sesion.getAttribute("logged") || sesion.getAttribute("idCliente") == null) {
			
			response.sendRedirect("login");
		}else {
		    	
				doPost(request, response);

		    }
			
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		sesion = request.getSession();
		Connection con;
		MArticulo[] articulos;
		MSubfamilia[] subfamilias;
		Parseamiento parse;
		parse = new Parseamiento();
		int pagina;
		int offset;
		offset=0;
		pagina =1;
		
		con = (Connection) sesion.getAttribute("conexion");

		if(sesion.getAttribute("logged") == null) {
			
			response.sendRedirect("login");
			
		}else if(!(boolean)sesion.getAttribute("logged") || sesion.getAttribute("idCliente") == null) {
			
			response.sendRedirect("login");
		}else {
	    	
	    	articulo = new MArticulo(con);
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
			
	
			String categoria;
			int numCategoria;
			String orden;
			String busqueda;
			String condition;
			int numeroRegistros;
			condition = "1 = 1";
			orden = "nombre";
			
			if(request.getParameter("orden") != null && (request.getParameter("orden").contentEquals("nombre") || request.getParameter("orden").contentEquals("pvp") ) ) {
				
				orden = request.getParameter("orden");
				request.setAttribute("orden", orden);
				
			}
			
			categoria = request.getParameter("categoria");
			
			if(categoria != null && !categoria.contentEquals("")) {
				try {
					numCategoria = parse.getInteger(categoria);
					request.setAttribute("categoria", numCategoria);
					if(numCategoria != -1) {
						condition = condition + " AND subfamilia = " + numCategoria;
					}		

				}catch(NumberFormatException e){
					
					//TODO
					e.printStackTrace();
				}

			}
			
			busqueda = request.getParameter("busqueda");
			
			if(busqueda != null && !busqueda.contentEquals("")) {
				
				
				condition = condition + " AND nombre LIKE '%"+busqueda +"%'";
				request.setAttribute("busqueda", busqueda);
				
				
			}
			
			


			articulos = articulo.mostrarArticulos(condition , orden , 5 , offset);
			
			numeroRegistros = parse.getInteger(subfamilia.getNumeroRegistros()); 
			subfamilias = subfamilia.mostrarCategorias("nombre",numeroRegistros, 0);
			
			numeroRegistros = parse.getInteger(articulo.getNumeroRegistros());
			
			request.setAttribute("numeroRegistros", numeroRegistros);
			request.setAttribute("rsSubfamilia", subfamilias);
			request.setAttribute("rs", articulos);
			
			request.getRequestDispatcher("articulos2dk.jsp").forward(request, response);
			
	}

}
	}
