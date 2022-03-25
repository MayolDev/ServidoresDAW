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


@WebServlet("/eliminarsubfamilia")
public class CEliminarSubFamilia extends HttpServlet {
	private static final long serialVersionUID = 1L;
       HttpSession sesion;
       MSubfamilia subfamilia;
       


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		sesion = request.getSession();
		
		if((sesion.getAttribute("rol") != null && (int)sesion.getAttribute("rol") != MCliente.ROL_ADMINISTRATIVO) || sesion.getAttribute("rol") == null) {
			
	    	response.sendRedirect("perfil");
	    	
	    }else {
	    	
			response.sendRedirect("subfamilias");
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
			
			subfamilia = new MSubfamilia(con);
			
			id = parse.getInteger(request.getParameter("idSubfamilia"));
			
			subfamilia.setId(id);
			int filas = subfamilia.EliminarSubfamilia();
			if(filas != -1) {
				response.sendRedirect("subfamilias");

			}else {
				
				request.setAttribute("DeleteError", true);
				request.getRequestDispatcher("subfamilias").forward(request, response);
			}
	    	
	    }

		
		
	}

}
