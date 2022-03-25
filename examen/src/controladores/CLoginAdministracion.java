package controladores;

import java.io.IOException;
import java.sql.Connection;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import modelos.MEmpleado;
import utils.Encriptador;


/**
 * Servlet implementation class MLogin
 */
@WebServlet("/loginempleados")
public class CLoginAdministracion extends HttpServlet {
	private static final long serialVersionUID = 1L;
	MEmpleado cliente;
	HttpSession sesion;


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		sesion = request.getSession();
		
		if(sesion.getAttribute("logged") != null && (boolean)sesion.getAttribute("logged")) {
			
			response.sendRedirect("articulos");
			  
		}else {
			
			response.sendRedirect("loginempleado.jsp");

		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Connection con;
		Encriptador encriptador;
		encriptador = new Encriptador();
		sesion = request.getSession();
		con = (Connection)sesion.getAttribute("conexion");
		Date fechaActual;
		Date fechaSalida;
		Calendar calendar;
		Calendar calendar2;
		int intentos;
		String email;
		String contrasena;
		
		

		fechaActual = new Date();
		fechaSalida = new Date();
		calendar = Calendar.getInstance();
		calendar2 = Calendar.getInstance();
		calendar.setTime(fechaActual);
		intentos = 0;
		

		if(sesion.getAttribute("intentos") != null) {
			
			intentos = (int)sesion.getAttribute("intentos");
			
		}
		if(sesion.getAttribute("fechalogin") != null) {
			
			calendar2.setTime((Date)sesion.getAttribute("fechalogin") );
		}
		
		
		email = request.getParameter("email");
		contrasena = request.getParameter("password");
		
		contrasena = encriptador.convertirSHA256(contrasena);
		
		cliente = new MEmpleado(con);
		
		cliente.setEmail(email);
		cliente.setPassword(contrasena);
		
		cliente = cliente.LoginCliente();
		
		
		
		try {

			if(cliente.getDni() < 0) {
				
				sesion.setAttribute("loginError", true);
				request.setAttribute("loginEmail", email);  
				sesion.setAttribute("intentos", (intentos + 1));
				calendar.add(Calendar.MINUTE, intentos *2 );
				fechaSalida = calendar.getTime();
				sesion.setAttribute("fechalogin", fechaSalida);

				
				
				request.getRequestDispatcher("loginempleado.jsp").forward(request, response);

			}else {
				
					
					if(sesion.getAttribute("fechalogin") != null) {
						
						calendar2.setTime((Date)sesion.getAttribute("fechalogin"));
						
						if(calendar.after(calendar2)){
							
							sesion.setAttribute("logged", true);
							sesion.setAttribute("idCliente", cliente.getDni() );
							response.sendRedirect("articulos");
							
						}else {
							
							sesion.setAttribute("loginError", true);
							request.setAttribute("loginEmail", email);  
							sesion.setAttribute("intentos", (intentos + 1));
							calendar.add(Calendar.MINUTE, intentos *2 );
							fechaSalida = calendar.getTime();
							sesion.setAttribute("fechalogin", fechaSalida);
							request.getRequestDispatcher("loginempleado.jsp").forward(request, response);
							
						}
						
						
					}else {
						
						sesion.setAttribute("logged", true);
						sesion.setAttribute("idCliente", cliente.getDni() );
						sesion.setAttribute("rol", true );

						response.sendRedirect("articulos");
						
					}
					

				}

			
		} catch (ServletException e) {
			System.out.println("Error en el servidor");
			e.printStackTrace();
		} 
		
		
		
		
	}

}
