import java.util.*;
import java.text.*;
import java.io.*;

public class Date_Tracker {

	String get_next_date(String dateString)
	{
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Calendar currentDate = null;
		Date startDate = null;
		Date date = null;

		try{

			startDate = formatter.parse(dateString);
		} 
		catch (Exception e){
			e.printStackTrace();
		}
		
	   	currentDate = Calendar.getInstance();
		currentDate.setTime(startDate);
		currentDate.add(Calendar.DATE, 1);
		date = currentDate.getTime();		
		dateString = formatter.format(date).toString();
		log(dateString);
		return dateString;
	}

	private static void log(String dateString)
	{
		try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("log.txt", true)))) 
		{
		    out.println(dateString);
		}
		catch (IOException e)
		{
		    e.printStackTrace();
		}
	}
}
