package modelos2dk;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConsultasBD {
	ResultSet rs;

	
	public ResultSet consultar(Connection con, String table, String columns, String condition, String order , int limit, int offset) {
		
		rs = null;

		try {
			String query;
			PreparedStatement stmt;
			if(limit != -1) {
			query="SELECT "+ columns+" FROM "+ table +" WHERE "+condition+" ORDER BY "+ order + " LIMIT ? OFFSET ?" ; 	
			stmt = con.prepareStatement(query);
			stmt.setInt(1, limit);
			stmt.setInt(2, offset);
			
			rs = stmt.executeQuery();
			
			}else {
				query="SELECT "+ columns+" FROM "+ table +" WHERE "+condition+" ORDER BY "+order+" OFFSET ?" ; 	
				stmt = con.prepareStatement(query);
				stmt.setInt(1, offset);

				rs = stmt.executeQuery();
			}

			


		} catch (SQLException sqle) {
			System.err.println("Se ha producido un error en la peticion SELECT a la base de datos. ");
			sqle.printStackTrace();
		}
		return rs;
		
	}
	
	
	public ResultSet consultaNumRegistros(Connection con, String table, String condition) {
		rs = null;
		
		
		try {

			String query;
			PreparedStatement stmt;
			
			query= "SELECT COUNT(*) AS CANTIDAD FROM "+table+ " WHERE " + condition ;
			stmt = con.prepareStatement(query);
			
			rs = stmt.executeQuery();
			
		} catch (SQLException e) {
			System.err.println("Se ha producido un error en la peticion SELECT a la base de datos. ");
			e.printStackTrace();
		}
		
		
		
		
		return rs;
	}
	
	public ResultSet consultarUltimoId(Connection con, String table, String column) {
		
		rs = null;

		try {
			String query;
			PreparedStatement stmt;
		
			query="SELECT "+ column+" FROM "+ table +" ORDER BY "+ column+ " DESC" ; 	
			stmt = con.prepareStatement(query);
			rs = stmt.executeQuery();		

		} catch (SQLException sqle) {
			System.err.println("Se ha producido un error en la peticion SELECT a la base de datos. ");
			sqle.printStackTrace();
		}
		return rs;
		
		
		
	}
	
	
	public int eliminar(Connection con, String table, String condition) {
		int filasEliminadas;
		filasEliminadas = -1;
		try {

			String query;
			PreparedStatement stmt;
			query= "DELETE FROM "+table+" WHERE "+condition+" ";
			
			stmt = con.prepareStatement(query);
			filasEliminadas = stmt.executeUpdate();			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return filasEliminadas;
	}
	
	
	
	public int actualizar(Connection con, String table, String set, String condition) {
		int filasmodificadas;
		filasmodificadas = -1;
		try {

			String query;
			PreparedStatement stmt;
			query= "UPDATE ? SET ? WHERE ? ";
			
			stmt = con.prepareStatement(query);
			stmt.setString(1, table);
			stmt.setString(2, set);
			stmt.setString(3, condition);
			filasmodificadas = stmt.executeUpdate();			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return filasmodificadas;
	}
	
	
	public int insertar(Connection con, String table, String columns, String values) {
		int filasinsertadas;
		filasinsertadas = -1;
		try {

			String query="INSERT INTO "+ table +" ("+columns+") VALUES ("+values+")"; 
			PreparedStatement stmt = con.prepareStatement(query);
			
			stmt.executeUpdate();
			stmt.close();
			

		} catch (SQLException sqle) {
			System.err.println("Ha dado un error al ejecutar la query");
			sqle.printStackTrace();
		}
		
		return filasinsertadas;
	}
	
	
	public int Actualizar(Connection con, String table, String SET, String condition) {
		int filasActualizadas;
		filasActualizadas = -1;
		try {

			String query="UPDATE "+ table +" SET " + SET  + " WHERE " + condition; 
			PreparedStatement stmt = con.prepareStatement(query);
			
			filasActualizadas = stmt.executeUpdate();
			stmt.close();
			

		} catch (SQLException sqle) {
			System.err.println("Ha dado un error al ejecutar la query");
			sqle.printStackTrace();
		}
		
		return filasActualizadas;
		
		
		
	}
	
	
	public int ActualizarImagen(Connection con, String table, String column, String condition, Object conditionvalue, FileInputStream fis , File file) {
		int filasActualizadas;
		filasActualizadas = -1;
		try {

			String query="UPDATE "+ table +" SET " + column  + " = ? WHERE " + condition + " = ?"; 
			query = query.replaceAll("\n", "");
			PreparedStatement stmt = con.prepareStatement(query);
			stmt.setBinaryStream(1, fis, (int)file.length());
			stmt.setObject(2, conditionvalue);
			filasActualizadas = stmt.executeUpdate();
			stmt.close();
			

		} catch (SQLException sqle) {
			System.err.println("Ha dado un error al ejecutar la query");
			sqle.printStackTrace();
		}
		
		return filasActualizadas;
		
		
		
	}
	
	
	
	
	
	
	
}
