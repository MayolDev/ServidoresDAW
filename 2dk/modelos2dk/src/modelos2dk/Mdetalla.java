package modelos2dk;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Mdetalla {
	String idArticulo;
	int nFactura;
	int cantidad;
	Connection con;
	ResultSet rs;
	ConsultasBD consultas;
	
	public Mdetalla(Connection con) {
		this.con = con;
	}
	
	
	public int mostrarCantidad( int limit, int offset) {
		consultas = new ConsultasBD();
		cantidad = -1;
		try {
			rs = consultas.consultar(con, "detalla", "cantidad" , "articulo= "+idArticulo+" AND factura= '"+ nFactura +"' ", "cantidad" ,limit , offset);
			if(rs.next()) {
				cantidad = rs.getInt(1);
			}
		} catch (SQLException e) {
			System.out.println("Error al traer los resultados de la base de datos");
			e.printStackTrace();
		}
		return cantidad;
	}	
	
	public int InsertarCantidad() {
		int filasInsertadas;
		consultas = new ConsultasBD();

		String values;
		
		
		values = String.format(" '%s' ,  %o ,  %o", 
			idArticulo, nFactura , cantidad);

		filasInsertadas = consultas.insertar(con, "detalla", "articulo, factura, cantidad" , values);
		
		return filasInsertadas;
		
	}
	
	
	public int EliminarCantidad() {
		int filasEliminadas;
		consultas = new ConsultasBD();
		
		filasEliminadas = consultas.eliminar(con, "detalla", "articulo = "+ idArticulo + " AND factura = '"+ nFactura +"' " );
		
		return filasEliminadas;
		
	}
	
	public String getNumeroRegistros() {
		String numeroRegistros;
		numeroRegistros = "0";
		consultas = new ConsultasBD();
		rs = consultas.consultaNumRegistros(con, "articulo", "articulo= "+idArticulo+" AND factura= '"+ nFactura +"' ");

		
		try {
			if(rs.next()) {
				numeroRegistros = rs.getString("CANTIDAD");
			}
		} catch (SQLException e) {
			System.out.println("Error al recibir la cantidad de registros");
			e.printStackTrace();
		}
		
		
		return numeroRegistros;
	}
	
	
	
	public String getIdArticulo() {
		return idArticulo;
	}
	public void setIdArticulo(String idArticulo) {
		this.idArticulo = idArticulo;
	}
	public int getnFactura() {
		return nFactura;
	}
	public void setnFactura(int nFactura) {
		this.nFactura = nFactura;
	}
	public int getCantidad() {
		return cantidad;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	
	
	
	
	
}
