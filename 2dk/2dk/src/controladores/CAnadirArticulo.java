package controladores;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import modelos2dk.MArticulo;
import modelos2dk.MCliente;
import modelos2dk.MSubfamilia;
import utils.Comprobaciones;
import utils.Parseamiento;


@WebServlet("anadirarticulo")
public class CAnadirArticulo extends HttpServlet {
	private static final long serialVersionUID = 1L;
       HttpSession sesion;
       MArticulo articulo;
 


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    MSubfamilia subfamilia;
	    Parseamiento parse;
	    MSubfamilia[] subfamilias;
	    String numRegistros;
	    Connection con;
	    sesion = request.getSession();
	    parse = new Parseamiento();
	    
	    //Si no es administrativo, redirige a perfil
	    if((sesion.getAttribute("rol") != null && (int)sesion.getAttribute("rol") != MCliente.ROL_ADMINISTRATIVO) || sesion.getAttribute("rol") == null) {
	    	
	    	response.sendRedirect("perfil");
	    
	    }else {
	    	//Sacamos el resulset y lo pasamos a la vista
		    con = (Connection) sesion.getAttribute("conexion");
		    subfamilia = new MSubfamilia(con);
		    numRegistros = subfamilia.getNumeroRegistros();
		    
		    
		    
		    subfamilias = subfamilia.mostrarCategorias("id", parse.getInteger(numRegistros), 0);
		    request.setAttribute("rs", subfamilias);
		    
		    request.getRequestDispatcher("anadirarticulo.jsp").forward(request, response);
	    }
	    

	    
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection con;
		
		Parseamiento parse;
		parse = new Parseamiento();
		String codigo, ean, nombre, descripcion;
		int tipoiva, stock, disponible, minimimo;
		BigDecimal pcd, pvp;
		int idSubFamilia;
		Comprobaciones comprobacion;
		
		comprobacion = new Comprobaciones();
		
		sesion = request.getSession();
		
		con = (Connection)sesion.getAttribute("conexion");
		articulo = new MArticulo(con);
		
		if((sesion.getAttribute("rol") != null && (int)sesion.getAttribute("rol") != MCliente.ROL_ADMINISTRATIVO) || sesion.getAttribute("rol") == null) {
			//Si no es administrativo, va a perfil
	    	response.sendRedirect("perfil");
	    	
	    }else {
	    	//Recupero datos
	    	codigo = request.getParameter("codigo");
			ean = request.getParameter("ean");
			nombre = request.getParameter("nombre");
			descripcion = request.getParameter("descripcion");
			
			//Validaciones
			if(comprobacion.checkInteger(request.getParameter("tipoiva"))) {
				tipoiva = parse.getInteger((request.getParameter("tipoiva")));

			}else {
				tipoiva = 0;
			}
			if(comprobacion.checkInteger(request.getParameter("stock"))) {
				stock = parse.getInteger((request.getParameter("stock")));

			}else {
				stock = 0;
			}
			
			if(comprobacion.checkInteger(request.getParameter("disponible"))) {
				disponible = parse.getInteger((request.getParameter("disponible")));

			}else {
				disponible = 0;
			}
			
			if(comprobacion.checkInteger(request.getParameter("minimimo"))) {
				minimimo = parse.getInteger((request.getParameter("minimimo")));

			}else {
				minimimo = 0;
			}
			
			if(comprobacion.checkInteger(request.getParameter("subfamilia"))) {
				idSubFamilia = parse.getInteger((request.getParameter("subfamilia")));

			}else {
				idSubFamilia = 0;
			}
			
			if(comprobacion.checkBigDecimal(request.getParameter("pcd"))) {
				pcd = parse.getBigDecimal(request.getParameter("pcd"));

			}else {
				pcd = null;
			}
			
			if(comprobacion.checkBigDecimal(request.getParameter("pvp"))) {
				pvp = parse.getBigDecimal(request.getParameter("pvp"));

			}else {
				pvp = null;
			}
			
			
			if(codigo.contentEquals("") || !comprobacion.checkStringBetween(codigo, 1, 8) || 
					!comprobacion.checkStringBetween(ean, 1, 13) ||
					!comprobacion.checkStringBetween(nombre, 1, 25) ||
					!comprobacion.checkStringBetween(descripcion, 1, 250)){ 
				
				request.setAttribute("insertado", false);
				 MSubfamilia subfamilia;
				 MSubfamilia[] subfamilias;
				 int numRegistros;
				 
			    subfamilia = new MSubfamilia(con);
			    numRegistros = parse.getInteger(subfamilia.getNumeroRegistros());
			    
			    
			    
			    subfamilias = subfamilia.mostrarCategorias("id", numRegistros, 0);
			    request.setAttribute("rs", subfamilias);
				
				request.getRequestDispatcher("anadirarticulo.jsp").forward(request, response);
				response.sendRedirect("anadirarticulo");
			}else {
				
				//Si todo correcto, añadimos los datos al modelo y ejecutamos la consulta
				articulo.setCodigo(codigo);
				articulo.setEan(ean);
				articulo.setNombre(nombre);
				articulo.setDescripcion(descripcion);
				articulo.setTipoiva(tipoiva);
				articulo.setStock(stock);
				articulo.setDisponible(disponible);
				articulo.setMinimimo(minimimo);
				articulo.setSubfamilia(idSubFamilia);
				articulo.setPcd(pcd);
				articulo.setPvp(pvp);
				
				if( articulo.InsertarArticulo() != -1) {
					
					request.setAttribute("insertado", true);
					
				}else {
					
					request.setAttribute("insertado", false);

				}
				
				response.sendRedirect("anadirarticulo");
				
			}
			
			

	    	
	    }
		
		
	
	}

}
