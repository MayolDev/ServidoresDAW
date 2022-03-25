package modelos2dk;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MFactura {

	int nFactura;
	Date fecha;
	Long idCliente;
	Connection con;
	ResultSet rs;
	ConsultasBD consultas;
	
	public MFactura(Connection con) {
		this.con = con;
	}
	
	public MFactura() {
		
	}
	

	public MFactura[] mostrarTodasFacturas( String order , int limit, int offset) {
		MFactura[] facturas;
		facturas = new MFactura[limit];
		consultas = new ConsultasBD();
		try {
			rs = consultas.consultar(con, "factura", "*" , "1=1", order ,limit , offset);
			int indice = 0;
			while(rs.next()) {
				
				MFactura factura;
				factura = new MFactura();
				factura.setnFactura(rs.getInt(1));
				factura.setFecha(rs.getDate(2));
				factura.setIdCliente(rs.getLong(3));
				
				facturas[indice] = factura;
				indice++;
				
				
				
				
			}
		} catch (SQLException e) {
			System.out.println("Error al traer los resultados de la base de datos");
			e.printStackTrace();
		}
		
		
		
		return facturas;
	}	
	
	public MFactura[] mostrarFacturasCliente( String order , int limit, int offset) {
		MFactura[] facturas;
		facturas = new MFactura[limit];
		consultas = new ConsultasBD();
		
		try {
			rs = consultas.consultar(con, "factura", "*" , "cliente = "+idCliente, order ,limit , offset);
			int indice = 0;
			while(rs.next()) {
				
				MFactura factura;
				factura = new MFactura();
				factura.setnFactura(rs.getInt(1));
				factura.setFecha(rs.getDate(2));
				factura.setIdCliente(rs.getLong(3));
				
				facturas[indice] = factura;
				indice++;
				
				
				
				
			}
		} catch (SQLException e) {
			System.out.println("Error al traer los resultados de la base de datos");
			e.printStackTrace();
		}
		
		
		return facturas;
	}	
	
	
	
	
	
	public int InsertarFactura() {
		int filasInsertadas;
		consultas = new ConsultasBD();

		String values;
		
		
		values = String.format(" %o ,  %t ,  %o  ", 
				nFactura, fecha, idCliente);

		filasInsertadas = consultas.insertar(con, "factura", "factura, fecha, cliente", values);
		
		return filasInsertadas;
		
	}
	
	
	public int EliminarArticulo() {
		int filasEliminadas;
		consultas = new ConsultasBD();
		
		filasEliminadas = consultas.eliminar(con, "factura", "factura = '"+ nFactura + "'" );
		
		return filasEliminadas;
		
	}
	
	public String getNumeroRegistros() {
		String numeroRegistros;
		numeroRegistros = "0";
		consultas = new ConsultasBD();
		rs = consultas.consultaNumRegistros(con, "factura", "1=1");
		
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
	
	
	
	
	public int getnFactura() {
		return nFactura;
	}
	public void setnFactura(int nFactura) {
		this.nFactura = nFactura;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public Long getIdCliente() {
		return idCliente;
	}
	public void setIdCliente(Long idCliente) {
		this.idCliente = idCliente;
	}
	
	
	
	
}
