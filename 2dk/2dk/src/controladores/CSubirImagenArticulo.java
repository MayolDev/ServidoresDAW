package controladores;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import modelos2dk.MArticulo;


@WebServlet("/subirimagenarticulo")
public class CSubirImagenArticulo extends HttpServlet {
	private static final long serialVersionUID = 1L;
	HttpSession sesion;



	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		sesion = request.getSession();
		
		if(sesion.getAttribute("logged") != null && (boolean)sesion.getAttribute("logged")) {
			
			response.sendRedirect("subirimagen.jsp");
			  
		}else {
			response.sendRedirect("login");

		}
		
		
		
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		File file;
		int maxFileSize;
		int maxMemSize;
		String filePath;
		String contentType;
		FileItem fi;
		List <FileItem> fileItems;
		DiskFileItemFactory factory;
		String fileName;
	    FileInputStream fis;
		MArticulo articulo;
	    Connection con;
	    
	    sesion = request.getSession();
	    con = (Connection)sesion.getAttribute("conexion");
		file=null;
		maxFileSize = 5000 * 1024;
		maxMemSize= 5000 * 1024;
		fileName=null;
		filePath = "data";
		
		String codigo;
		codigo = "";
		
		contentType = request.getContentType();
		
		
		
		   if ((contentType.indexOf("multipart/form-data") >= 0)) {

		      factory = new DiskFileItemFactory();
		      // maximum size that will be stored in memory
		      factory.setSizeThreshold(maxMemSize);
		      // Location to save data that is larger than maxMemSize.
		      factory.setRepository(new File("."));

		      // Create a new file upload handler
		      ServletFileUpload upload = new ServletFileUpload(factory);
		      // maximum file size to be uploaded.
		      upload.setSizeMax( maxFileSize );
		      
		      try{ 
		         // Parse the request to get file items.
		         fileItems = upload.parseRequest(request);

		         // Process the uploaded file items
		         Iterator<FileItem> i = fileItems.iterator();


		         while ( i.hasNext () ) 
		         {
		            fi = (FileItem)i.next();
		            if ( !fi.isFormField () )	
		            {



		            fileName = fi.getName();

		            
		            if( fileName.lastIndexOf("/") >= 0 ){
			            file = new File( filePath + 
			            fileName.substring( fileName.lastIndexOf("/"))) ;
		            }
		            else{
			            file = new File( filePath + "/" +
			            fileName.substring(fileName.lastIndexOf("/")+1)) ;
		            }
		            fi.write( file ) ;
		            }else {
		            	codigo = fi.getString();
		            }
		         }

		      }catch(Exception ex) {
		         System.out.println(ex);
		      }
		   }
 
		//SUBIR A LA BBDD
		   
		   


		 codigo = codigo.replaceAll("(\n|\r)", "");
	    articulo = new MArticulo(con);
	    
	    articulo.setCodigo(codigo);	
	    
	    
	    fis = new FileInputStream(file);
	   
	    articulo.ActualizarImagen(fis, file);
	    file.delete();
	    response.sendRedirect("articulos");
		 
		 
	}

}
