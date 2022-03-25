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


@WebServlet("/editarperfil")
public class CEditarPerfil extends HttpServlet {
	private static final long serialVersionUID = 1L;
	HttpSession sesion;
	MCliente cliente;
 


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Connection con;
		sesion = request.getSession();
		
		if(sesion.getAttribute("logged") != null && (boolean)sesion.getAttribute("logged")) {
			
				
					con = (Connection)sesion.getAttribute("conexion");
					cliente = new MCliente(con);
					cliente.setId((Long) sesion.getAttribute("idCliente"));
					
					cliente = cliente.ConsultarClientePorId();
					
					request.setAttribute("documento", cliente.getDocumento());
					request.setAttribute("nombre", cliente.getNombre());
					request.setAttribute("direccion", cliente.getDireccion());
					request.setAttribute("direccion1", cliente.getDireccion1());
					request.setAttribute("cp", cliente.getCodigoPostal());
					request.setAttribute("localidad", cliente.getLocalidad());
					request.setAttribute("ciudad", cliente.getCiudad());
					request.setAttribute("correo", cliente.getCorreo());
					request.setAttribute("telefono", cliente.getTelefono());
					
					request.getRequestDispatcher("editarperfil.jsp").forward(request, response);
							  
		}else {
			response.sendRedirect("login");
		}
		
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Connection con;
		sesion = request.getSession();
		con = (Connection) sesion.getAttribute("conexion");
		cliente = new MCliente(con);
		Comprobaciones comprobacion;
		comprobacion = new Comprobaciones();
		
		String nombre, documento, direccion, direccion1, codigoPostal, localidad, ciudad, telefono;
		
		nombre  =request.getParameter("nombre");
		documento = request.getParameter("documento");
		direccion = request.getParameter("direccion");
		direccion1 = request.getParameter("direccion1");
		codigoPostal = request.getParameter("cp");
		localidad = request.getParameter("localidad");
		ciudad = request.getParameter("ciudad");
		telefono = request.getParameter("telefono");
		
		
		if(nombre.contentEquals("") || !comprobacion.checkStringBetween(nombre, 1, 35) || documento.contentEquals("") || !comprobacion.checkDNI(documento) || !comprobacion.checkStringBetween(documento, 1, 29)
				|| !comprobacion.checkStringBetween(direccion, 1, 40) || !comprobacion.checkStringBetween(direccion1, 1, 40) || !comprobacion.checkStringBetween(codigoPostal, 1, 6)
				|| !comprobacion.checkStringBetween(localidad, 1, 25) || !comprobacion.checkStringBetween(ciudad, 1, 25) || !comprobacion.checkStringBetween(telefono, 1, 15)) {
			
			response.sendRedirect("perfil");

			
		}else {
			cliente.setNombre(nombre);
			cliente.setDocumento(documento);
			cliente.setDireccion(direccion);
			cliente.setDireccion1(direccion1);
			cliente.setCodigoPostal(codigoPostal);
			cliente.setLocalidad(localidad);
			cliente.setCiudad(ciudad);
			cliente.setTelefono(telefono);
			cliente.setId((Long)sesion.getAttribute("idCliente"));
			
			cliente.ModificarCliente();
			
			
			response.sendRedirect("perfil");
			
		}
		
		

		
		
		
	}

}
