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
import utils.Parseamiento;

/**
 * Servlet implementation class CArticulos
 */
@WebServlet("/articulos")
public class CArticulos extends HttpServlet {
	private static final long serialVersionUID = 1L;
    HttpSession sesion;
	MOrdenador ordenador;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		doPost(request, response);

	
	}
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		sesion = request.getSession();
		Connection con;
		MOrdenador[] ordenadores;
		Parseamiento parse;
		String categoria;
		int numCategoria;
		String orden;
		int numOrden;
		String busqueda;
		int numeroRegistros;
		orden = "nombre";
		numCategoria = MOrdenador.CONDICION_CATEGORIA_TODAS;
		
		
		
		
		parse = new Parseamiento();
		int pagina;
		int offset;
		offset=0;
		pagina =1;
		
		
		
		con = (Connection) sesion.getAttribute("conexion");

		
    	ordenador = new MOrdenador(con);

    	
    	if(request.getParameter("page") != null) {
			try {
				pagina = parse.getInteger(request.getParameter("page")) ;
				if(pagina <1) {
					pagina = 1;
				}
				if(pagina == 1) {
					offset = 0;
				}else {
					offset = (5*pagina) -5;
				}
				
				request.setAttribute("page", pagina);
				
			}catch(NumberFormatException e) {
				
				pagina = 1;
				offset = 0;
				request.setAttribute("page", pagina);
				e.printStackTrace();

			}

			
		}
    	
    	if(request.getParameter("orden") != null && (request.getParameter("orden").contentEquals("nombre") || request.getParameter("orden").contentEquals("pvp") ) ) {
			
			orden = request.getParameter("orden");
			
			request.setAttribute("orden", orden);
			
		}
		
		
		switch(orden) {
		
		case "nombre":
			numOrden = MOrdenador.ORDEN_NOMBRE;
		break;
		
		case "pvp":
			numOrden = MOrdenador.ORDEN_PRECIO;
			break;
		default:
			numOrden = MOrdenador.ORDEN_NOMBRE;

		
		}
		
		
		
		
		categoria = request.getParameter("categoria");
		
		if(categoria != null && !categoria.contentEquals("")) {
			try {
				numCategoria = 1;
				request.setAttribute("categoria", numCategoria);


			}catch(NumberFormatException e){
				
				numCategoria = MOrdenador.CONDICION_CATEGORIA_TODAS;
				
				e.printStackTrace();
			}

		}
		
		busqueda = request.getParameter("busqueda");
		
		if(busqueda != null && !busqueda.contentEquals("")) {
			
			
			request.setAttribute("busqueda", busqueda);
			
			
		}
		
		


		ordenadores = ordenador.mostrarArticulos(numCategoria , numOrden , 5 , offset, busqueda , categoria);
		

		
		numeroRegistros = parse.getInteger(ordenador.getNumeroRegistros());
		
		request.setAttribute("numeroRegistros", numeroRegistros);
		request.setAttribute("rs", ordenadores);
		
		request.getRequestDispatcher("articulos.jsp").forward(request, response);
    	
    	
    	
    	
		
		
		
	}

}
