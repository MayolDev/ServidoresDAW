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
 * Servlet implementation class CProducto
 */
@WebServlet("/producto")
public class CProducto extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    HttpSession sesion;
    MOrdenador ordenador;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection con;
		String codigo;
		Parseamiento parse;
		MOrdenador ordenador;
		int idOrdenador;
		
		parse = new Parseamiento();
		codigo = request.getParameter("codigo");
		idOrdenador =  parse.getInteger(codigo);
		
		
		if(codigo == null) {
			response.sendRedirect("articulos");
		}else {
			sesion = request.getSession();
			con = (Connection)sesion.getAttribute("conexion");
			ordenador = new MOrdenador(con);
			
			ordenador.setIdOrdenador(idOrdenador);
			ordenador = ordenador.ConsultarArticuloPorId();
			
			request.setAttribute("rs", ordenador);
			
			request.getRequestDispatcher("producto.jsp").forward(request, response);
			
			
			
		}
	}



}
