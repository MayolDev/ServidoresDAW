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


@WebServlet("/producto")
public class CArticuloClientes extends HttpServlet {
	private static final long serialVersionUID = 1L;
      HttpSession sesion;
      MArticulo articulo;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Connection con;
		String codigo;
		MArticulo[] articulos;
		
		codigo = request.getParameter("codigo");
		
		if(codigo == null) {
			response.sendRedirect("articulos2dk");
		}else {
			sesion = request.getSession();
			con = (Connection)sesion.getAttribute("conexion");
			articulo = new MArticulo(con);
			
			articulos = new MArticulo[5];
			articulos = articulo.mostrarArticulos("codigo = '" + codigo + "'", "codigo", 1, 0);
			request.setAttribute("rs", articulos);
			
			request.getRequestDispatcher("producto.jsp").forward(request, response);
			
			
			
		}
		
		
		
		
		
	}


}
