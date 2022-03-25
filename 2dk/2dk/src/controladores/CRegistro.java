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
import utils.Comprobaciones;
import utils.Email;
import utils.Encriptador;
import utils.GeneradorStringAleatorio;


@WebServlet("/registro")
public class CRegistro extends HttpServlet {
	private static final long serialVersionUID = 1L;
	HttpSession sesion;
	MCliente cliente;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		sesion = request.getSession();
		
		if(sesion.getAttribute("logged") != null && (boolean)sesion.getAttribute("logged")) {
			
			response.sendRedirect("perfil");
			  
		}else {
			response.sendRedirect("registro.jsp");
		}
		
		
		
		

	}
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		sesion = request.getSession();
		Comprobaciones comprobacion;
		boolean error;
		error = false;
		Connection con;
		Encriptador encriptador;
		String asunto;
		String cuerpo;
		Email email;
		
		String nombre, documento, correo, direccion , codigoPostal, ciudad, localidad, telefono, contrasena, contrasena2; 
		
		comprobacion = new Comprobaciones();
		
		nombre= request.getParameter("nombre");
		documento = request.getParameter("documento");
		correo = request.getParameter("email");
		direccion = request.getParameter("direccion");
		codigoPostal = request.getParameter("cp");
		ciudad = request.getParameter("ciudad");
		localidad = request.getParameter("localidad");
		telefono = request.getParameter("telefono");
		contrasena = request.getParameter("contrasena");
		contrasena2 = request.getParameter("contrasena2");
		
		if(!contrasena.contentEquals(contrasena2) || contrasena.contentEquals("") || !comprobacion.checkStringBetween(contrasena, 6, 30)) {
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
		if(documento.contentEquals("") || !comprobacion.checkDNI(documento) || !comprobacion.checkStringBetween(documento, 1, 29)) {
			sesion.setAttribute("documentoError", true);
			error=true;
		}else {
			sesion.setAttribute("documentoError", false);

		}
		if(correo.contentEquals("") || !comprobacion.checkEmail(correo) || !comprobacion.checkStringBetween(correo, 1, 50)) {
			sesion.setAttribute("correoError", true);
			error=true;
		}else {
			sesion.setAttribute("correoError", false);

		}
		
		
		if(!comprobacion.checkStringBetween(direccion, 1, 40)) {
			sesion.setAttribute("direccionError", true);
			error=true;

		}else {
			sesion.setAttribute("direccionError", false);

		}
		
		if(!comprobacion.checkStringBetween(codigoPostal, 1, 6)) {
			sesion.setAttribute("cpError", true);
			error=true;

		}else {
			sesion.setAttribute("cpError", false);

		}
		
		if(!comprobacion.checkStringBetween(localidad, 1, 25)) {
			sesion.setAttribute("localidadError", true);
			error=true;

		}else {
			sesion.setAttribute("localidadError", false);

		}
		
		if(!comprobacion.checkStringBetween(ciudad, 1, 25)) {
			sesion.setAttribute("ciudadError", true);
			error=true;

		}else {
			sesion.setAttribute("ciudadError", false);

		}
		
		if(!comprobacion.checkStringBetween(telefono, 1, 15)) {
			sesion.setAttribute("telefonoError", true);
			error=true;

		}else {
			sesion.setAttribute("telefonoError", false);

		}
		
			
		
		if(error) {
		    request.setAttribute("nombre", nombre);
		    request.setAttribute("documento", documento);
		    request.setAttribute("email", correo);
		    request.setAttribute("direccion", direccion);
		    request.setAttribute("cp", codigoPostal);
		    request.setAttribute("ciudad", ciudad);
		    request.setAttribute("localidad", localidad);
		    request.setAttribute("telefono", telefono);
			request.getRequestDispatcher("registro.jsp").forward(request, response);
		}else {
			encriptador = new Encriptador();
			con = (Connection) sesion.getAttribute("conexion");
			
			cliente = new MCliente(con);
			cliente.setNombre(nombre);
			cliente.setDocumento(documento);
			cliente.setCorreo(correo);
			cliente.setDireccion(direccion);
			cliente.setCodigoPostal(codigoPostal);
			cliente.setCiudad(ciudad);
			cliente.setLocalidad(localidad);
			cliente.setTelefono(telefono);
			cliente.setContrasena(encriptador.convertirSHA256(contrasena));
			cliente.setId(cliente.UltimoId() + 1);
			cliente.setRol(MCliente.ROL_CLIENTE);
						
			cliente.setHash(GeneradorStringAleatorio.generateRandomString(64));
			cliente.InsertarCliente();

			asunto = "Bienvenido a 2dk, Este es su correo de verificacion";
			cuerpo = "Para verificar su cuenta, haga click en el siguiente enlace: http://localhost:8085/2dk/verificar?correo=" + correo + "&hash=" + cliente.getHash();
			
			email = new Email(correo, asunto, cuerpo);
			
			email.enviarEmail();
			
			
			response.sendRedirect("login.jsp");
		}
		
		
		
		
		
		
		
	}

}
