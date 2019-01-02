package util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class FormatoData {
    static String data_br;
    static java.util.Date data;
    static DateFormat df = new SimpleDateFormat("dd/MM/yyyy");    
    
    public static String getDataBR(java.util.Date data) {
        try {
            data_br = df.format(data);
        }
        catch (Exception e) {
            data_br = "";
        }
        return data_br;
    }
    public static java.util.Date getData(String data_br) {
           try {
			data = df.parse(data_br.trim());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        return data;
    }    
}