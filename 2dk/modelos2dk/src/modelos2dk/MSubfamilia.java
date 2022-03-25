package modelos2dk;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MSubfamilia {

	int id;
	String nombre;
	int idFamilia;
	Connection con;
	ResultSet rs;
	ConsultasBD consultas;
	
	
	public MSubfamilia(Connection con) {
		this.con = con;
	}
	
	public MSubfamilia() {
		
	}
	
	
	
	public MSubfamilia[] mostrarCategorias( String order , int limit, int offset) {
		MSubfamilia[] subfamilias;
		
		subfamilias = new MSubfamilia[limit];
		
		consultas = new ConsultasBD();
		
		
		try {
			rs = consultas.consultar(con, "subfamilia", "*" , "1=1", order ,limit , offset);
			int indice = 0;
			while(rs.next()) {
				MSubfamilia subFamilia;
				subFamilia = new MSubfamilia();

				subFamilia.setId(rs.getInt(1));
				subFamilia.setNombre(rs.getString(2));
				subFamilia.setIdFamilia(rs.getInt(3));
				
				
				subfamilias[indice] = subFamilia;
				
				indice++;
				
				
			}
		} catch (SQLException e) {
			System.out.println("Error al traer los resultados de la base de datos");
			e.printStackTrace();
		}
		
		
		
		
		return subfamilias;
	}	
	
	
	public String getNumeroRegistros() {
		String numeroRegistros;
		numeroRegistros = "0";
		consultas = new ConsultasBD();
		rs = consultas.consultaNumRegistros(con, "subfamilia", "1=1");
		
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
	
	
	
	
	
	public int InsertarCategoria() {
		int filasInsertadas;
		consultas = new ConsultasBD();

		String values;
		
		
		values = String.format(" %o ,  '%s' , %o ", 
			id, nombre, idFamilia);

		filasInsertadas = consultas.insertar(con, "subfamilia", "id, nombre, familia" , values);
		
		return filasInsertadas;
		
	}
	
	
	public int EliminarSubfamilia() {
		int filasEliminadas;
		consultas = new ConsultasBD();
		
		filasEliminadas = consultas.eliminar(con, "subfamilia", "id = '"+ id + "'" );
		
		return filasEliminadas;
		
	}
	
	public int UltimoId() {
		int ultimoId;
		ultimoId = 0;
		consultas = new ConsultasBD();
		
		
		rs = consultas.consultarUltimoId(con, "subfamilia", "id");
		
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
		
		filasActualizadas = consultas.Actualizar(con, "subfamilia", "nombre = '" + nombre + "' , familia = " + idFamilia, "id = " + id + "");
		
		return filasActualizadas;
		
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
	public int getIdFamilia() {
		return idFamilia;
	}
	public void setIdFamilia(int idFamilia) {
		this.idFamilia = idFamilia;
	}
	
	
	
	
	
}
