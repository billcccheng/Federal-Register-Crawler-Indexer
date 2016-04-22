import java.util.concurrent.BlockingQueue;
import org.json.*;


public class Get_List_Docs implements Runnable
{
	BlockingQueue<String> requests;
	BlockingQueue<String> urls;
	boolean keepRunning = true;
	String req;
	Get_List_Docs(BlockingQueue<String> requests, BlockingQueue<String> urls)
	{
		this.requests = requests;
		this.urls = urls;
	}
	
	public void run()
	{
		while(keepRunning)
		{
			try 
			{
				req = requests.take();
				if(req.equals("STOP"))
					keepRunning = false;
			}
			catch(InterruptedException e) 
			{
				break;
			} 


			String encodedJSON = null;
			try
			{
				encodedJSON = Network.fetch(req);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}

			JSONObject decodedJSON = new JSONObject(encodedJSON);
			JSONObject decodedJSONResults = null;
			if(decodedJSON.getInt("count") != 0)
			{				
				for(int i = 0; i < decodedJSON.getJSONArray("results").length(); i++)
				{
					decodedJSONResults = decodedJSON.getJSONArray("results").getJSONObject(i);
					//System.out.println(decodedJSONResults.getString("full_text_xml_url") instanceof String);
					try
					{
						if(decodedJSONResults.isNull("full_text_xml_url") == false){
							// System.out.println(decodedJSONResults.getString("full_text_xml_url"));
							urls.put(decodedJSONResults.getString("full_text_xml_url"));
						}
					}
					catch (InterruptedException e)
					{
						e.printStackTrace();
					}
				}
			}
		}
	}
}
