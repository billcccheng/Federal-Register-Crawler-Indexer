import java.util.concurrent.*;

public class Generate_Queries implements Runnable
{
	String date;
	int numOfDates = 1825;
	BlockingQueue<String> queue;
	boolean keepRunning = true;
	//this query might be too long
	String baseQuery = "https://www.federalregister.gov/api/v1/articles.json?fields%5B%5D=abstract&fields%5B%5D=full_text_xml_url&per_page=1000&order=relevance&conditions%5Bpublication_date%5D%5Bis%5D=";
				
	Generate_Queries(BlockingQueue<String> queue, String date)
	{
		this.date = date;
		this.queue = queue;
	}

	public void run()
	{
		Date_Tracker dateTracking = new Date_Tracker();
		int loopCounter = 0;
		while(keepRunning)
		{	
			try 
			{
				loopCounter++;
				date = dateTracking.get_next_date(date);
				String query = baseQuery + date;
				queue.put(query);
				if(loopCounter == numOfDates)
					keepRunning = false;
			} 
			catch(InterruptedException e)
			{
				break;
			}
		}
	}
}
