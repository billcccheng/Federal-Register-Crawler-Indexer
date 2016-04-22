import java.util.concurrent.BlockingQueue;
import java.io.*;

public class Get_Doc implements Runnable
{
	BlockingQueue<String> urls;
	boolean keepRunning = true;
	String page = null;
	String url = null;
	static int countDocument = 1;

	Get_Doc(BlockingQueue<String> urlin)
	{
		this.urls = urlin;
	}
	
	public void run()
	{
		while(keepRunning)
		{
			try 
			{
				url = urls.take();
				if(url.equals("STOP"))
					keepRunning = false;
			} 
			catch(InterruptedException e) 
			{
				break;
			}


			try
			{
				page = Network.fetch(url);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
					
			saveFile(page, countDocument++);
		}
		
	}

	private static void saveFile(String page, int countDocument)
	{
		System.out.println(countDocument);
		PrintStream out = null;
		try
		{
			out = new PrintStream(new FileOutputStream("documents/"+Integer.toString(countDocument)+".txt"));
			out.println(page);
		}
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
		} 
		finally
		{
			out.close();
		}
	}
}
