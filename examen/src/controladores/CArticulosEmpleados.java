package controladores;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import modelos.MOrdenador;

import utils.Parseamiento;

/**
 * Servlet implementation class CArticulosEmpleados
 */
@WebServlet("/articulosempleados")
public class CArticulosEmpleados extends HttpServlet {
	private static final long serialVersionUID = 1L;
    HttpSession sesion;
    MOrdenador ordenador;
	
  

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		sesion = request.getSession();
		
		if((sesion.getAttribute("rol") == null )) {
			
	    	response.sendRedirect("loginempleados");
	    	
	    }else {
	    	
			doPost(request, response);

	    }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		sesion = request.getSession();
		Connection con;
		MOrdenador[] ordenadores;
		Parseamiento parse;
		int numeroRegistros;
		parse = new Parseamiento();
		int pagina;
		int offset;
		offset=0;
		pagina =1;
		
		con = (Connection) sesion.getAttribute("conexion");

		if((sesion.getAttribute("rol") == null )) {
			
	    	response.sendRedirect("articulos");
	    	
	    }else {
	    	
	    	ordenador = new MOrdenador(con);
			
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
			
			
			ordenadores = new MOrdenador[5];
			ordenadores = ordenador.mostrarTodosArticulos(MOrdenador.ORDEN_NOMBRE, 5, offset);
			request.setAttribute("rs", ordenadores);
			
			numeroRegistros = parse.getInteger(ordenador.getNumeroRegistros()); 
			
			request.setAttribute("numeroRegistros", numeroRegistros);
			
			if(request.getAttribute("DeleteError") != null && (boolean) request.getAttribute("DeleteError")) {
				
				request.setAttribute("DeleteError", true);
			}
			
			request.getRequestDispatcher("articulo.jsp").forward(request, response);
			
		}
	}

}
