package modelos2dk;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MCliente {
	
	public static final int ROL_ADMINISTRATIVO = 1;
	public static final int ROL_CLIENTE=2;
	Long id;
	String documento;
	String nombre;
	String direccion;
	String direccion1;
	String codigoPostal;
	String localidad;
	String ciudad;
	String correo;
	String telefono;
	String contrasena;
	byte[] imagen;
	boolean verificado;
	int rol;
	String hash;
	Connection con;
	ResultSet rs;
	ConsultasBD consultas;
	
	
	public MCliente(Connection con) {
		this.con = con;
	}
	
	public MCliente() {
		
	}
	
	public MCliente[] mostrarTodosClientes( String order , int limit, int offset) {
		MCliente[] clientes;
		clientes = new MCliente[limit];
		
		
		try {
			consultas = new ConsultasBD();
			rs = consultas.consultar(con, "cliente", "*" , "1=1", order ,limit , offset);
			int indice = 0;

			while(rs.next()) {
				MCliente cliente;
				cliente = new MCliente();
				
				cliente.setId(rs.getLong(1));
				cliente.setDocumento(rs.getString(2));
				cliente.setNombre(rs.getString(3));
				cliente.setDireccion(rs.getString(4));
				cliente.setDireccion1(rs.getString(5));
				cliente.setCodigoPostal(rs.getString(6));
				cliente.setLocalidad(rs.getString(7));
				cliente.setCiudad(rs.getString(8));
				cliente.setCorreo(rs.getString(9));
				cliente.setTelefono(rs.getString(10));
				cliente.setContrasena(rs.getString(11));
				cliente.setImagen(rs.getBytes(12));
				cliente.setRol(rs.getInt(13));
				cliente.setVerificado(rs.getBoolean(14));
				cliente.setHash(rs.getString(15));
				
				clientes[indice] = cliente;
				indice++;
				
			}
			
		}catch(SQLException e) {
			System.out.println("Error al recoger los datos de cliente");
			e.printStackTrace();
		}
			
		return clientes;
	}	
	
	
	
	public String getNumeroRegistros() {
		String numeroRegistros;
		numeroRegistros = "0";
		consultas = new ConsultasBD();
		rs = consultas.consultaNumRegistros(con, "cliente", "1=1");
		
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
	
	
	
	public MCliente LoginCliente() {
		consultas = new ConsultasBD();
		MCliente cliente;
		cliente = new MCliente();

		try {

			rs = consultas.consultar(con, "cliente", "id, verificado, rol" , "correo = '" + correo + "' AND contrasena = '" + contrasena + "'", "id" , 1 , 0);
			if(rs.next()) {
				
				cliente.setId(rs.getLong(1));
				cliente.setVerificado(rs.getBoolean(2));
				cliente.setRol(rs.getInt(3));
				
				
				
			}
		} catch (SQLException e) {
			System.out.print("Error en el login del cliente");
			e.printStackTrace();
		}
		
		
		
		return cliente;
	}	
	
	public MCliente ConsultarClientePorId() {
		consultas = new ConsultasBD();
		MCliente cliente;
		cliente = new MCliente();
		
		try {

			rs = consultas.consultar(con, "cliente", "documento, nombre, direccion, direccion1, codigopostar, localidad, ciudad, correo, teléfono, imagen " , "id = " + id, "id" , 1 , 0);
			if(rs.next()) {
				
				cliente.setDocumento(rs.getString(1));
				cliente.setNombre(rs.getString(2));
				cliente.setDireccion(rs.getString(3));
				cliente.setDireccion1(rs.getString(4));
				cliente.setCodigoPostal(rs.getString(5));
				cliente.setLocalidad(rs.getString(6));
				cliente.setCiudad(rs.getString(7));
				cliente.setCorreo(rs.getString(8));
				cliente.setTelefono(rs.getString(9));
				cliente.setImagen(rs.getBytes(10));
				
				
				
			}
		} catch (SQLException e) {
			System.out.print("Error en el login del cliente");
			e.printStackTrace();
		}
		
		
		return cliente;
	}	
	
	public int ActualizarImagen(FileInputStream fis , File file) {
		int filasActualizadas;
		filasActualizadas = -1;
		consultas = new ConsultasBD();
		
		filasActualizadas = consultas.ActualizarImagen(con, "cliente", "imagen", "id",id, fis, file);
		
		return filasActualizadas;
	}
	
	public int ModificarCliente() {
		int filasActualizadas;
		filasActualizadas = -1;
		consultas = new ConsultasBD();
		
		filasActualizadas = consultas.Actualizar(con, "cliente", 
		"documento = '" + documento 
		+ "' , direccion =  '" + direccion 
		+ "' , direccion1 = '" + direccion1
		+ "' , codigopostar =  '" + codigoPostal
		+ "' , localidad = '" + localidad
		+ "' , ciudad = '" + ciudad
		+ "' , teléfono = '" + telefono 
		+ "' , nombre = '" + nombre + "' ",
				"id = " + id);
		
		return filasActualizadas;
		
		
	}

	
	public int Verificar() {
		int filasActualizadas;
		filasActualizadas = -1;
		consultas = new ConsultasBD();
		
		filasActualizadas = consultas.Actualizar(con, "cliente", "verificado = true", "correo = '" + correo + "' AND hash = '" + hash + "'" );
		return filasActualizadas;
		
	}
	

	
	
	
	public int InsertarCliente() {
		int filasInsertadas;
		consultas = new ConsultasBD();

		String values;
		
		
		values = String.format(" %o ,  '%s' ,  '%s' , '%s', '%s', '%s' , '%s', '%s', '%s', '%s', %o , '%s' ", 
			id, documento, nombre, direccion, codigoPostal, localidad, ciudad, correo, telefono, contrasena, rol, hash);

		filasInsertadas = consultas.insertar(con, "cliente", "id, documento, nombre, direccion, "
				+ " codigopostar, localidad, ciudad, correo, teléfono, contrasena, rol, hash"
				 , values);
		
		return filasInsertadas;
		
	}
	
	public Long UltimoId() {
		Long ultimoId;
		ultimoId = (long)0;
		consultas = new ConsultasBD();
		
		
		rs = consultas.consultarUltimoId(con, "cliente", "id");
		
		try {
			if(rs.next()) {
				ultimoId = rs.getLong(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return ultimoId;
	}
	
	
	public int EliminarArticulo() {
		int filasEliminadas;
		consultas = new ConsultasBD();
		
		filasEliminadas = consultas.eliminar(con, "cliente", "id = '"+ id + "'" );
		
		return filasEliminadas;
		
	}
	
	
	
	
	public byte[] getImagen() {
		return imagen;
	}

	public void setImagen(byte[] imagen) {
		this.imagen = imagen;
	}

	public boolean isVerificado() {
		return verificado;
	}

	public void setVerificado(boolean verificado) {
		this.verificado = verificado;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContrasena() {
		return contrasena;
	}


	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}


	public int getRol() {
		return rol;
	}


	public void setRol(int rol) {
		this.rol = rol;
	}


	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getDireccion1() {
		return direccion1;
	}

	public void setDireccion1(String direccion1) {
		this.direccion1 = direccion1;
	}

	public String getCodigoPostal() {
		return codigoPostal;
	}

	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}


	public String getHash() {
		return hash;
	}


	public void setHash(String hash) {
		this.hash = hash;
	}

	

	
	
	
}
