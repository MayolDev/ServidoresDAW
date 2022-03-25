package controladores;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import modelos.MCliente;
import utils.Comprobaciones;
import utils.Email;
import utils.Encriptador;
import utils.GeneradorStringAleatorio;
import utils.Parseamiento;


/**
 * Servlet implementation class CRegistro
 */
@WebServlet("/registro")
public class CRegistro extends HttpServlet {
	private static final long serialVersionUID = 1L;
	HttpSession sesion;
	MCliente cliente;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		sesion = request.getSession();
		
		if(sesion.getAttribute("logged") != null && (boolean)sesion.getAttribute("logged")) {
			
			response.sendRedirect("articulos");
			  
		}else {
			response.sendRedirect("registro.jsp");
		}
		
		
		
		
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		sesion = request.getSession();
		Comprobaciones comprobacion;
		boolean error;
		error = false;
		Connection con;
		Parseamiento parse;
		Encriptador encriptador;
		String asunto;
		String cuerpo;
		Email Uemail;
		
		parse = new Parseamiento();
		
		String dni, nombre, apellidos, direccion , categoria, email, fechanac, password , password2; 
		
		comprobacion = new Comprobaciones();
		
		dni= request.getParameter("documento");
		nombre = request.getParameter("nombre");
		apellidos = request.getParameter("apellidos");
		direccion = request.getParameter("direccion");
		categoria = request.getParameter("categoria");
		email = request.getParameter("email");
		fechanac = request.getParameter("fechanac");
		password = request.getParameter("contrasena");
		password2 = request.getParameter("contrasena2");
		
		if(!password.contentEquals(password2) || password.contentEquals("") || !comprobacion.checkStringBetween(password, 6, 30)) {
			sesion.setAttribute("passwdError", true);
			error= true;

		}else {
			sesion.setAttribute("passwdError", false);
		}
		
		if(nombre.contentEquals("") || !comprobacion.checkStringBetween(nombre, 1, 35)) {
			sesion.setAttribute("nombreError", true);
			error=true;
		}else {
			sesion.setAttribute("nombreError", false);

		}
	
		if(email.contentEquals("") || !comprobacion.checkEmail(email) || !comprobacion.checkStringBetween(email, 1, 50)) {
			sesion.setAttribute("correoError", true);
			error=true;
		}else {
			sesion.setAttribute("correoError", false);

		}
		
		if(email.contentEquals("") || !comprobacion.checkInteger(dni)) {
			sesion.setAttribute("dniError", true);
			error=true;
		}else {
			sesion.setAttribute("dniError", false);

		}
		
		
		if(!comprobacion.checkStringBetween(direccion, 1, 40)) {
			sesion.setAttribute("direccionError", true);
			error=true;

		}else {
			sesion.setAttribute("direccionError", false);

		}
		
		if(!comprobacion.checkStringBetween(apellidos, 1, 200)) {
			sesion.setAttribute("apellidosError", true);
			error=true;

		}else {
			sesion.setAttribute("apellidosError", false);

		}
		
		
		if(!comprobacion.checkDate(fechanac)) {
			sesion.setAttribute("fechaError", true);
			error=true;

		}else {
			sesion.setAttribute("fechaError", false);

		}
		
			
		
		if(error) {
		    request.setAttribute("nombre", nombre);
		    request.setAttribute("apellidos", apellidos);
		    request.setAttribute("dni", dni);
		    request.setAttribute("email", email);
		    request.setAttribute("direccion", direccion);
		    request.setAttribute("categoria", categoria);
			request.getRequestDispatcher("registro.jsp").forward(request, response);
		}else {
			encriptador = new Encriptador();
			con = (Connection) sesion.getAttribute("conexion");
			
			cliente = new MCliente(con);
			cliente.setDni(parse.getInteger(dni));
			cliente.setNombre(nombre);
			cliente.setApellidos(apellidos);
			cliente.setDireccion(direccion);
			cliente.setCategoria(parse.getInteger(categoria));
			cliente.setEmail(email);
			cliente.setFechaNac(Date.valueOf(fechanac));
			cliente.setPassword(encriptador.convertirSHA256(password));
			System.out.println(cliente.getPassword());
			
						
			cliente.setHash(GeneradorStringAleatorio.generateRandomString(64));
			cliente.InsertarCliente();

			asunto = "Bienvenido a tienda informatica, Este es su correo de verificacion";
			cuerpo = "Para verificar su cuenta, haga click en el siguiente enlace: http://ns3034756.ip-91-121-81.eu:8080/mbi_ORD/verificar?correo=" + email + "&hash=" + cliente.getHash();
			
			Uemail = new Email(email, asunto, cuerpo);
			
			Uemail.enviarEmail();
			
			
			response.sendRedirect("login.jsp");
		}
		
		
		
		
		
		
		
	}

}
