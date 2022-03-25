package utils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

public class Comprobaciones {
	public boolean checkDNI(String DNI) {

		 Pattern patron;
		 patron = Pattern.compile("[0-9]{7,8}[A-Z a-z]");

		 java.util.regex.Matcher mat;
		 mat = patron.matcher(DNI);

		 if(mat.matches()){

			 return true;

		 }

	 		return false;

	 }

	public boolean checkByte(String valor) {

		try {

			Byte.parseByte(valor);

			return true;


		}catch(NumberFormatException e) {

			return false;

		}

	}

	public boolean checkInteger(String valor) {

		try {

			Integer.parseInt(valor);

			return true;


		}catch(NumberFormatException e) {

			return false;

		}

	}

	public boolean checkBigDecimal(String valor) {

		BigDecimal parse;

		try {

			parse=new BigDecimal(valor);

			return true;


		}catch(NumberFormatException e) {

			return false;

		}

	}

	public boolean checkHour(String valor) {

		SimpleDateFormat formato;

		try {

			formato = new SimpleDateFormat("HH:mm");

			formato.parse(valor);

			return true;


		}catch(NumberFormatException | ParseException e) {

			return false;

		}

	}

	public boolean checkDate(String valor) {

		SimpleDateFormat formato;

		try {

			formato = new SimpleDateFormat("dd-MM-yyyy");

			formato.parse(valor);

			return true;

		}catch(NumberFormatException | ParseException e) {

			return false;

		}

	}

	public boolean checkDateHour(String valor) {

       SimpleDateFormat formato;

       try {

       	formato = new SimpleDateFormat("dd-MM-yyyy HH:mm");

       	formato.parse(valor);

           return true;

       } catch (ParseException e) {

           return false;

       }

   }
	
	
	public boolean checkEmail(String valor) {

		boolean valido=false;

		Pattern patron;
		patron = Pattern.compile("^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$");

		java.util.regex.Matcher mat;
		mat = patron.matcher(valor);

		if(mat.matches()){

			valido= true;

		}

		return valido;

	}
	
	

	public boolean checkStringBetween(String valor, int minimo, int maximo) {

		if(valor.length()>=minimo && valor.length()<=maximo) {

			return true;

		}

		return false;

	}

	public boolean checkIntegerBetween(String valor, int minimo, int maximo) {

		if (Integer.parseInt(valor)>=minimo && Integer.parseInt(valor)<=maximo) {

			return true;

		}

		return false;

	}

	public boolean checkBigDecimalBetween(String valor, BigDecimal minimo, BigDecimal maximo) {

		BigDecimal comparar;

		comparar=new BigDecimal(valor);

		if (comparar.compareTo(minimo)>=0 && comparar.compareTo(maximo)<=0) {

			return true;

		}

		return false;

	}
}
