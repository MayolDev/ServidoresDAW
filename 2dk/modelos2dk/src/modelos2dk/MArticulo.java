package modelos2dk;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;


public class MArticulo {
	
	
	public static final int IVA_REDUCIDO= 1;
	public static final int IVA_NORMAL=2;

	String codigo;
	String ean;
	String nombre;
	String descripcion;
	int tipoiva;
	BigDecimal pcd;
	BigDecimal pvp;
	int stock;
	int disponible;
	int minimimo;
	int subfamilia;
	byte[] imagen;
	Connection con;

	ResultSet rs;
	ConsultasBD consultas;
	
	
	public MArticulo(Connection con) {
		this.con = con;
	}
	
	
	public MArticulo() {
		
	}

	public MArticulo[] mostrarTodosArticulos( String order , int limit, int offset) {
		MArticulo[] articulos;
		MArticulo articulo;

		articulos = new MArticulo[limit];

		try {
			consultas = new ConsultasBD();
			rs = consultas.consultar(con, "articulo", "*" , "1=1", order ,limit , offset);
			int indice = 0;
			while(rs.next()) {
				
				articulo = new MArticulo();
				
				articulo.setCodigo(rs.getString(1));
				articulo.setEan(rs.getString(2));
				articulo.setNombre(rs.getString(3));
				articulo.setDescripcion(rs.getString(4));
				articulo.setTipoiva(rs.getInt(5));
				articulo.setPcd(rs.getBigDecimal(6));
				articulo.setPvp(rs.getBigDecimal(7));
				articulo.setStock(rs.getInt(8));
				articulo.setDisponible(rs.getInt(9));
				articulo.setMinimimo(rs.getInt(10));
				articulo.setSubfamilia(rs.getInt(11));
				articulo.setImagen(rs.getBytes(12));
				
				articulos[indice] = articulo;
				
				indice++;
				
				
			}
		} catch (SQLException e) {
			System.out.println("Error al recibir datos de articulos");
			e.printStackTrace();
		}
		
		
		
		return articulos;
	}	
	
	public MArticulo[] mostrarArticulos( String condition ,String order , int limit, int offset) {
		MArticulo[] articulos;
		MArticulo articulo;
		articulos = new MArticulo[limit];
		
		
		try {
			consultas = new ConsultasBD();
			rs = consultas.consultar(con, "articulo", "*" , condition, order ,limit , offset);
			int indice = 0;
			while(rs.next()) {
				
				articulo = new MArticulo();
				
				articulo.setCodigo(rs.getString(1));
				articulo.setEan(rs.getString(2));
				articulo.setNombre(rs.getString(3));
				articulo.setDescripcion(rs.getString(4));
				articulo.setTipoiva(rs.getInt(5));
				articulo.setPcd(rs.getBigDecimal(6));
				articulo.setPvp(rs.getBigDecimal(7));
				articulo.setStock(rs.getInt(8));
				articulo.setDisponible(rs.getInt(9));
				articulo.setMinimimo(rs.getInt(10));
				articulo.setSubfamilia(rs.getInt(11));
				articulo.setImagen(rs.getBytes(12));
				
				articulos[indice] = articulo;
				
				indice++;
				
				
			}
		} catch (SQLException e) {
			System.out.println("Error al recibir datos de articulos");
			e.printStackTrace();
		}
		
		
		return articulos;
	}
	
	public String getNumeroRegistros() {
		String numeroRegistros;
		numeroRegistros = "0";
		consultas = new ConsultasBD();
		rs = consultas.consultaNumRegistros(con, "articulo", "1=1");
		
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
	
	public int InsertarArticulo() {
		int filasInsertadas;
		consultas = new ConsultasBD();
		String parsepcd;
		String parsepvp;
		String values;
		parsepcd = "0";
		parsepvp = "0";
		if(pcd != null) {
			parsepcd = String.format(  "%f", pcd).replaceAll(",", ".");
		}
		if(pvp != null) {
			parsepvp = String.format(  "%f", pvp).replaceAll(",", ".");

		}
		
		values = String.format(" '%s' ,  '%s' ,  '%s' , '%s', %o, %s, %s , %o, %o, %o, %o ", 
				codigo, ean, nombre, descripcion, tipoiva, parsepcd, parsepvp, stock, disponible, minimimo, subfamilia);

		filasInsertadas = consultas.insertar(con, "articulo", "codigo, ean, nombre, descripcion, tipoiva, pcd,"
				+ " pvp, stock, disponible, minimimo, subfamilia" , values);
		
		return filasInsertadas;
		
	}
	
	
	public int EliminarArticulo() {
		int filasEliminadas;
		consultas = new ConsultasBD();
		
		filasEliminadas = consultas.eliminar(con, "articulo", "codigo = '"+ codigo + "'" );
		
		return filasEliminadas;
		
	}
	
	public int ActualizarImagen(FileInputStream fis , File file) {
		int filasActualizadas;
		filasActualizadas = -1;
		consultas = new ConsultasBD();
		
		filasActualizadas = consultas.ActualizarImagen(con, "articulo", "imagen", "codigo", codigo , fis, file);
		
		return filasActualizadas;
	}
	
	
	
	public int ActualizarArticulo( ) {
		int filasActualizadas;
		filasActualizadas = -1;
		consultas = new ConsultasBD();
		
		filasActualizadas = consultas.Actualizar(con, "articulo", 
		"ean = '" + ean 
		+ "' , nombre =  '" + nombre 
		+ "' , descripcion = '" + descripcion
		+ "' , tipoiva =  " + tipoiva
		+ " , pcd = " + pcd
		+ " , pvp = " + pvp
		+ " , stock = " + stock 
		+ " , disponible = " + disponible
		+ " , minimimo = " + minimimo
		+ " , subfamilia = " + subfamilia + "",
				"codigo = '" + codigo + "'");
		
		return filasActualizadas;
		
	}
	
	
	

	
	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getEan() {
		return ean;
	}

	public void setEan(String ean) {
		this.ean = ean;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getTipoiva() {
		return tipoiva;
	}

	public void setTipoiva(int tipoiva) {
		this.tipoiva = tipoiva;
	}

	public BigDecimal getPcd() {
		return pcd;
	}

	public void setPcd(BigDecimal pcd) {
		this.pcd = pcd;
	}

	public BigDecimal getPvp() {
		return pvp;
	}

	public void setPvp(BigDecimal pvp) {
		this.pvp = pvp;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public int getDisponible() {
		return disponible;
	}

	public void setDisponible(int disponible) {
		this.disponible = disponible;
	}

	public int getMinimimo() {
		return minimimo;
	}

	public void setMinimimo(int minimimo) {
		this.minimimo = minimimo;
	}

	public int getSubfamilia() {
		return subfamilia;
	}

	public void setSubfamilia(int subfamilia) {
		this.subfamilia = subfamilia;
	}

	
	public byte[] getImagen() {
		return imagen;
	}


	public void setImagen(byte[] imagen) {
		this.imagen = imagen;
	}

	
	
	
}
