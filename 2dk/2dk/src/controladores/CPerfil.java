package controladores;

import java.io.IOException;
import java.sql.Connection;
import java.util.Base64;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import modelos2dk.MCliente;


@WebServlet("/perfil")
public class CPerfil extends HttpServlet {
	private static final long serialVersionUID = 1L;
	HttpSession sesion;
	MCliente cliente;
       


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection con;
		sesion = request.getSession();
		byte [] foto;
		String bfoto;
		
		if(sesion.getAttribute("logged") == null) {
			
			response.sendRedirect("login");
			
		}else if(!(boolean)sesion.getAttribute("logged") || sesion.getAttribute("idCliente") == null) {
			
			response.sendRedirect("login");
			
		}else {
			
				con = (Connection)sesion.getAttribute("conexion");
				cliente = new MCliente(con);
				cliente.setId((Long) sesion.getAttribute("idCliente"));
				
				cliente = cliente.ConsultarClientePorId();
				request.setAttribute("documento", cliente.getDocumento() );
				request.setAttribute("nombre", cliente.getNombre());
				request.setAttribute("direccion", cliente.getDireccion());
				request.setAttribute("direccion1", cliente.getDireccion1());
				request.setAttribute("codigopostal", cliente.getCodigoPostal());
				request.setAttribute("localidad", cliente.getLocalidad());
				request.setAttribute("ciudad", cliente.getCiudad());
				request.setAttribute("correo", cliente.getCorreo());
				request.setAttribute("telefono", cliente.getTelefono());
				foto = cliente.getImagen();
				if(foto != null) {
				bfoto = Base64.getEncoder().encodeToString(foto);
				request.setAttribute("bfoto", bfoto);
				}
				
				
				request.getRequestDispatcher("perfil.jsp").forward(request, response);

	
		}
		
		
		
	}

	

}
