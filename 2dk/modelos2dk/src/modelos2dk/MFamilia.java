package modelos2dk;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MFamilia {

	int id;
	String nombre;
	Connection con;
	ResultSet rs;
	ConsultasBD consultas;
	
	
	
	public MFamilia(Connection con) {
		this.con = con;
	}
	
	public MFamilia() {
		
	}

	public MFamilia[] mostrarCategorias( String order , int limit, int offset) {
		MFamilia[] familias;
		
		familias = new MFamilia[limit];
		consultas = new ConsultasBD();
		
		try {
			rs = consultas.consultar(con, "familia", "*" , "1=1", order ,limit , offset);
			int indice = 0;
			while(rs.next()) {
				MFamilia familia;
				familia = new MFamilia();
				familia.setId(rs.getInt(1));
				familia.setNombre(rs.getString(2));
				
				familias[indice] = familia;
				
				indice++;
				
				
			}
		} catch (SQLException e) {
			System.out.println("Error al traer los resultados de la base de datos");
			e.printStackTrace();
		}
		
		
		
		
		return familias;
	}	
	
	public int InsertarCategoria() {
		int filasInsertadas;
		consultas = new ConsultasBD();

		String values;
		
		
		values = String.format(" %o ,  '%s' ", 
			id, nombre);

		filasInsertadas = consultas.insertar(con, "familia", "id, nombre" , values);
		
		return filasInsertadas;
		
	}
	
	
	public int EliminarFamilia() {
		int filasEliminadas;
		consultas = new ConsultasBD();
		
		filasEliminadas = consultas.eliminar(con, "familia", "id = '"+ id + "'" );
		
		return filasEliminadas;
		
	}
	
	
	public int UltimoId() {
		int ultimoId;
		ultimoId = 0;
		consultas = new ConsultasBD();
		
		
		rs = consultas.consultarUltimoId(con, "familia", "id");
		
		try {
			if(rs.next()) {
				ultimoId = rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return ultimoId;
	}
	
	
	public int ActualizarCategoria( ) {
		int filasActualizadas;
		filasActualizadas = -1;
		consultas = new ConsultasBD();
		
		filasActualizadas = consultas.Actualizar(con, "familia", "nombre = '" + nombre + "'", "id = " + id + "");
		
		return filasActualizadas;
		
	}
	
	
	public String getNumeroRegistros() {
		String numeroRegistros;
		numeroRegistros = "0";
		consultas = new ConsultasBD();
		rs = consultas.consultaNumRegistros(con, "familia", "1=1");
		
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
	
	
	
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	
	
	
	
	
}
