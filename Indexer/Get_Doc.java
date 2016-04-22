/* Note: Fetching xml files locally for now */
import java.util.concurrent.*;
import java.io.*;
import org.apache.commons.io.FileUtils;


public class Get_Doc implements Runnable
{
	boolean exists_next = true;
	BlockingQueue<String> docs;
	Get_Doc(BlockingQueue<String> documents)
	{
		docs = documents;
	}
	public void run()
	{
		File folder = new File("../Crawler/documents/");
		File[] listOfFiles = folder.listFiles();


		for (int i = 0; i < listOfFiles.length; i++) 
		{
			File file = listOfFiles[i];
			if (file.isFile() && file.getName().endsWith(".txt"))
			{
				String content = null;
				try
				{
					content = FileUtils.readFileToString(file);
				}
				catch(IOException e)
				{
					e.printStackTrace();
				}
				/* do somthing with content */
				docs.add(content);
				// System.out.println(content);
				// System.out.println("-------------");
			} 
		}
		// while(exists_next)
		// {
		// 	//read xml from file, add string to doc
		// 	//check if next file exists

		// }
		// TODO Auto-generated method stub
		
	}

}
