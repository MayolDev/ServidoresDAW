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


@WebServlet("/modificararticulo")
public class CModificarArticulo extends HttpServlet {
	private static final long serialVersionUID = 1L;
    HttpSession sesion;
    MArticulo articulo;
    MSubfamilia subfamilia;
 

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Parseamiento parse;
		parse = new Parseamiento();
		MSubfamilia[] subfamilias;
		int numRegistros;
		String codigo, ean, nombre, descripcion;
		int tipoiva, stock, disponible, minimimo;
		BigDecimal pcd, pvp;
		int idSubFamilia;
		sesion = request.getSession();
		
		if((sesion.getAttribute("rol") != null && (int)sesion.getAttribute("rol") != MCliente.ROL_ADMINISTRATIVO) || sesion.getAttribute("rol") == null) {
			
	    	response.sendRedirect("perfil");
	    	
	    }else {
	    	
	    	if(request.getParameter("codigo") != null) {
	    		
				codigo = request.getParameter("codigo");
				ean = request.getParameter("ean");
				nombre = request.getParameter("nombre");
				descripcion = request.getParameter("descripcion");
				tipoiva = parse.getInteger((request.getParameter("tipoiva")));
				stock = parse.getInteger((request.getParameter("stock")));
				disponible = parse.getInteger((request.getParameter("disponible")));
				minimimo = parse.getInteger((request.getParameter("minimimo")));
				idSubFamilia = parse.getInteger(request.getParameter("subfamilia"));
				pcd = parse.getBigDecimal(request.getParameter("pcd"));
				pvp = parse.getBigDecimal(request.getParameter("pvp"));

				request.setAttribute("codigo", codigo);
				request.setAttribute("ean", ean);
				request.setAttribute("nombre", nombre);
				request.setAttribute("descripcion", descripcion);
				request.setAttribute("tipoiva", tipoiva);
				request.setAttribute("stock", stock);
				request.setAttribute("disponible", disponible);
				request.setAttribute("minimimo", minimimo);
				request.setAttribute("subfamilia", idSubFamilia);
				request.setAttribute("pcd", pcd);
				request.setAttribute("pvp", pvp);
				
				    Connection con;
				    sesion = request.getSession();
				    con = (Connection) sesion.getAttribute("conexion");
				    subfamilia = new MSubfamilia(con);
				    numRegistros = parse.getInteger(subfamilia.getNumeroRegistros());
				    subfamilias = subfamilia.mostrarCategorias("id", numRegistros, 0);
				    request.setAttribute("rs", subfamilias);
				    			
				
				request.getRequestDispatcher("modificararticulo.jsp").forward(request, response);

			}else {
				response.sendRedirect("articulos");
			}

	    }
		
		
		

		
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection con;
		sesion = request.getSession();
		con =  (Connection)sesion.getAttribute("conexion");
		Parseamiento parse;
		parse = new Parseamiento();
		String codigo, ean, nombre, descripcion;
		int tipoiva, stock, disponible, minimimo;
		BigDecimal pcd, pvp;
		int idSubFamilia;
		Comprobaciones comprobacion;
		comprobacion = new Comprobaciones();
		
		if((sesion.getAttribute("rol") != null && (int)sesion.getAttribute("rol") != MCliente.ROL_ADMINISTRATIVO) || sesion.getAttribute("rol") == null) {
			
	    	response.sendRedirect("perfil");
	    	
	    }else {
	    	
	    	codigo = request.getParameter("codigo");
			ean = request.getParameter("ean");
			nombre = request.getParameter("nombre");
			descripcion = request.getParameter("descripcion");
			
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
				
				
				response.sendRedirect("articulos");
			}else {
				
				articulo = new MArticulo(con);
				
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
				
				articulo.ActualizarArticulo();
				response.sendRedirect("articulos");
				
			}
			
			
			
	    }
		
		

		
		
		
	}

}
