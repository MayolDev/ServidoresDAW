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


@WebServlet("/eliminarfamilia")
public class CEliminarFamilia extends HttpServlet {
	private static final long serialVersionUID = 1L;
    HttpSession sesion;
    MFamilia familia;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		sesion = request.getSession();
		
		if((sesion.getAttribute("rol") != null && (int)sesion.getAttribute("rol") != MCliente.ROL_ADMINISTRATIVO) || sesion.getAttribute("rol") == null) {
	    	response.sendRedirect("perfil");
	    }else {
			response.sendRedirect("familias");
	    }
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection con;
		int id;
		Parseamiento parse;
		parse = new Parseamiento();

		sesion = request.getSession();
		
		if((sesion.getAttribute("rol") != null && (int)sesion.getAttribute("rol") != MCliente.ROL_ADMINISTRATIVO) || sesion.getAttribute("rol") == null) {
			
	    	response.sendRedirect("perfil");
	    	
	    }else {
	    	
			con = (Connection) sesion.getAttribute("conexion");
			
			familia = new MFamilia(con);
			
			id = parse.getInteger(request.getParameter("idFamilia"));
			
			familia.setId(id);
			int filas = familia.EliminarFamilia();
			if(filas != -1) {
				response.sendRedirect("familias");

			}else {
				
				request.setAttribute("DeleteError", true);
				request.getRequestDispatcher("familias").forward(request, response);
			}
	    	
	    }
		

		
	}

}
