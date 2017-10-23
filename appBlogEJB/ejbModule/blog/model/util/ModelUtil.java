package blog.model.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ModelUtil {
	public static boolean isEmpty(String cadena) {
		if (cadena == null || cadena.length() == 0)
			return true;
		return false;
	}

	public static int getAnioActual() {
		Date fechaActual = new Date();
		SimpleDateFormat formato = new SimpleDateFormat("yyyy");
		int anioActual = Integer.parseInt(formato.format(fechaActual));
		return anioActual;
	}

	public static int getMesActual() {
		Date fechaActual = new Date();
		SimpleDateFormat formato = new SimpleDateFormat("MM");
		int mesActual = Integer.parseInt(formato.format(fechaActual));
		return mesActual;
	}

	public static Date getSumarDias(Date fecha, int dias)  {
		Calendar calendar = Calendar.getInstance();  
		calendar.setTime(fecha); // Configuramos la fecha que se recibe  
		calendar.add(Calendar.DAY_OF_YEAR, dias);  // numero de dias a sumar, o restar en caso de dias<0 
		return calendar.getTime(); // Devuelve el objeto Date con los nuevos dias anadidos  }  
	}
	public static String fecha(Date fecha) {
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy hh:mm");
		return formato.format(fecha);
	}
}

