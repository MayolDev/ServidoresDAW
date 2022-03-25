package utils;


import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Parseamiento {


		public String getSHA256(String valor){

			String resultado;

			resultado=null;

			try {

			    MessageDigest digerir;
			    digerir = MessageDigest.getInstance("SHA-256");

			    digerir.reset();
			    digerir.update(valor.getBytes("utf8"));

			    resultado = String.format("%064x", new BigInteger(1, digerir.digest()));

			} catch (Exception e) {

			    e.printStackTrace();

			}

			return resultado;

		}


		public Byte getByte(String valor) {

			return Byte.parseByte(valor);

		}

		public int getInteger(String valor) {

			return Integer.parseInt(valor);

		}

		public BigDecimal getBigDecimal(String valor) {

			return new BigDecimal(valor);

		}

		public Date getHour(String valor) {

			DateFormat formato;
			Date fecha;

			fecha=null;

			try {

				formato = new SimpleDateFormat("HH:mm");

				fecha = formato.parse(valor);

			} catch (ParseException e) {

				e.printStackTrace();

			}

			return fecha;

		}

		public Date getDate(String valor) {

			SimpleDateFormat formato;
			Date fecha;

			fecha=null;

			try {

				formato = new SimpleDateFormat("dd-MM-yyyy");

				fecha=formato.parse(valor);

			} catch (ParseException e) {

				e.printStackTrace();

			}

			return fecha;

		}

		public Date getDateHour(String valor) {

	        SimpleDateFormat formato;
	        Date fecha;

			fecha=null;

	        try {

	        	formato = new SimpleDateFormat("dd-MM-yyyy HH:mm");

	            fecha=formato.parse(valor);


	        } catch (ParseException e) {

	        	e.printStackTrace();

	        }

	        return fecha;

	    }

	
}
	
