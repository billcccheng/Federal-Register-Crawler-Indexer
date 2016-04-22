import java.util.concurrent.*;
import java.util.ArrayList;

public class Crawler 
{

	public static void main(String[] args) 
	{
		int numGenerate = 1;
		int numJSON = 100;
		int numXML = 100;
		String startDate = "2007-01-01";

		BlockingQueue<String> queriesQueue = new ArrayBlockingQueue<String>(200000);
		BlockingQueue<String> urls = new ArrayBlockingQueue<String>(200000);
		Generate_Queries queries = new Generate_Queries(queriesQueue, startDate);
		Get_List_Docs getListDocs = new Get_List_Docs(queriesQueue, urls);
		Get_Doc getDoc = new Get_Doc(urls);

		/* Spawn threads */
		ArrayList<Thread> generateQueriesThread = new ArrayList<Thread>();
		ArrayList<Thread> getListDocThread = new ArrayList<Thread>();
		ArrayList<Thread> getDocThread = new ArrayList<Thread>();

		for(int i = 0; i < numGenerate; i++)
			generateQueriesThread.add(new Thread(queries));
		
		for(int i = 0; i <numJSON; i++)
			getListDocThread.add(new Thread(getListDocs));

		for(int i = 0; i < numXML; i++)
			getDocThread.add(new Thread(getDoc));


		for(int i = 0; i < numGenerate; i++)
			generateQueriesThread.get(i).start();
		
		try
		{
			for(int i = 0; i < numGenerate; i++)
				generateQueriesThread.get(i).join();

			for(int i = 0; i < numJSON; i++)
				queriesQueue.add("STOP");	
		} 
		catch(InterruptedException e)
		{
			e.printStackTrace();
		}
		finally
		{
			System.out.println("generateQueriesThread Dead");
		}


		for(int i = 0; i < numJSON; i++)
			getListDocThread.get(i).start();
	
		try
		{
			for(int i = 0; i < numJSON; i++){
				getListDocThread.get(i).join();
			}

			for(int i = 0; i < numXML; i++)
				urls.add("STOP");	
		} 
		catch(InterruptedException e)
		{
			e.printStackTrace();//
		}
		finally
		{
			System.out.println("getListDocThread Dead");
		}


		for(int i = 0; i < numXML; i++)
			getDocThread.get(i).start();

		try
		{
			for(int i = 0; i < numXML; i++)
				getDocThread.get(i).join();
		} 
		catch(InterruptedException e)
		{
			e.printStackTrace();
		}
		finally
		{
			System.out.println("getDocThread Dead");
		}
		

	}
}
