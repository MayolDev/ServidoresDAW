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
 * Servlet implementation class CEliminarArticulo
 */
@WebServlet("/eliminararticulo")
public class CEliminarArticulo extends HttpServlet {
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
	    	
			response.sendRedirect("articulos");

	    }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection con;
		String codigo;
		int id;
		Parseamiento parse;
		parse = new Parseamiento();
		sesion = request.getSession();
		
		if((sesion.getAttribute("rol") == null )) {
			
	    	response.sendRedirect("loginempleados");
	    	
	    }else {
	    	
	    	con = (Connection) sesion.getAttribute("conexion");
			
			ordenador = new MOrdenador(con);
			
			codigo = request.getParameter("idArticulo");
			id = parse.getInteger(codigo);
			ordenador.setIdOrdenador(id);
			int filas = ordenador.EliminarArticulo();

			if(filas != -1) {
				response.sendRedirect("articulosempleados");

			}else {
				request.setAttribute("DeleteError", true);
				request.getRequestDispatcher("articulosempleados").forward(request, response);
			}
			
			
		}
	}

}
