import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

public class Network
{
    static String fetch(String url) throws Exception
    {
    	final String USER_AGENT = "Mozilla/5.0";
    	URL obj = new URL(url);
    	HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
    	con.setRequestMethod("GET");
    	con.setRequestProperty("User-Agent",USER_AGENT);
    	int responseCode = con.getResponseCode();
    	//TODO
    	//handle 302 or 404 response codes
    	BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
  		String input;
  		StringBuffer response = new StringBuffer();
  		
  	while(true)
		{	
			input = in.readLine();
			if(input == null)
				break;
			response.append(input);
		}
		in.close();


        //fetch webpage here and return string
        return response.toString();
    }
}