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
import utils.Comprobaciones;
import utils.Parseamiento;

/**
 * Servlet implementation class CAnadirArticulo
 */
@WebServlet("/anadirarticulo")
public class CAnadirArticulo extends HttpServlet {
	private static final long serialVersionUID = 1L;
    HttpSession sesion;
    MOrdenador ordenador;
  
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    sesion = request.getSession();
	    
	    //Si no es administrativo, redirige a perfil
	    if((sesion.getAttribute("rol") == null)) {
	    	
	    	response.sendRedirect("loginempleados");
	    
	    }else {


		   
		    
		    request.getRequestDispatcher("anadirarticulo.jsp").forward(request, response);
	    }
	    

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection con;
		
		Parseamiento parse;
		parse = new Parseamiento();
		String idOrdenador, tipo, marca, modelo, precio;
		int id, tipoOrdenador;
		double precioOrdenador;
		Comprobaciones comprobacion;
		
		comprobacion = new Comprobaciones();
		
		sesion = request.getSession();
		
		con = (Connection)sesion.getAttribute("conexion");
		ordenador = new MOrdenador(con);
		
		if((sesion.getAttribute("rol") == null )) {
			//Si no es administrativo, va a perfil
	    	response.sendRedirect("loginempleados");
	    	
	    }else {
	    	//Recupero datos
	    	idOrdenador = request.getParameter("idOrdenador");
			tipo = request.getParameter("tipo");
			marca = request.getParameter("marca");
			modelo = request.getParameter("modelo");
			precio = request.getParameter("precio");
			
			precioOrdenador = Double.parseDouble(precio);
			
			//Validaciones
			if(comprobacion.checkInteger(idOrdenador)) {
				id = parse.getInteger((idOrdenador));

			}else {
				id = 0;
			}
			
			if(comprobacion.checkInteger(tipo)) {
				tipoOrdenador = parse.getInteger((tipo));

			}else {
				tipoOrdenador = 0;
			}
			
	
			
			
			
			if(id < 0){ 
				
				request.setAttribute("insertado", false);


				
				request.getRequestDispatcher("anadirarticulo.jsp").forward(request, response);
				response.sendRedirect("anadirarticulo");
			}else {
				
				//Si todo correcto, añadimos los datos al modelo y ejecutamos la consulta
			
				ordenador.setIdOrdenador(id);
				ordenador.setModelo(modelo);
				ordenador.setMarca(marca);
				ordenador.setTipo(tipoOrdenador);
				ordenador.setPrecio(precioOrdenador);
				
				
				if( ordenador.InsertarArticulo() != -1) {
					
					request.setAttribute("insertado", true);
					
				}else {
					
					request.setAttribute("insertado", false);

				}
				
				response.sendRedirect("anadirarticulo");
				
			}
			
			

	    	
	    }
	}

}
