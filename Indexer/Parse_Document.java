import java.util.concurrent.BlockingQueue;
import org.apache.solr.common.SolrInputDocument;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.xml.sax.InputSource;
import org.w3c.dom.NodeList;
import org.w3c.dom.Document;
import java.io.StringReader;
import org.w3c.dom.Node;
import org.w3c.dom.Element;



public class Parse_Document implements Runnable
{
	BlockingQueue<String> xmlDoc;
	BlockingQueue<SolrInputDocument> solrDoc;
	Parse_Document(BlockingQueue<String> xmlDoc, BlockingQueue<SolrInputDocument> solrDoc)
	{
		this.xmlDoc = xmlDoc;
		this.solrDoc = solrDoc;
	}

	public void run() 
	{
		String xml = null;

		while(true)
		{
			try
			{
				xml = xmlDoc.take();
				if(xml.equals("STOP"))
				{
					xmlDoc.add("STOP");
					break;
				}
			}
			catch(InterruptedException e)
			{
				e.printStackTrace();
			}

			try
			{
				parseXML(xml);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		// //parse the document here
		// 
		
		//document.addField("Title", "the title itself");
		//
		//
		// solrDoc.put(document);
		// TODO Auto-generated method stub
		
	}

	private void parseXML(String xml) throws Exception
	{
		SolrInputDocument document = new SolrInputDocument();
		Document doc = null;
		DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
	    InputSource source = new InputSource();
	    source.setCharacterStream(new StringReader(xml));
	    try{
	    	doc = db.parse(source);
	    }
	    catch(Exception e)
	    {
	    	return;
	    }
	    NodeList nList = doc.getElementsByTagName("*");

	   // System.out.println("TYPE :" 
         //   + nList.item(0).getNodeName());

	    document.addField("TYPE", nList.item(0).getNodeName());

	    if(!doc.getDocumentElement().getNodeName().equals("PRESDOCU"))
	    {
	    	NodeList n = doc.getElementsByTagName("FRDOC");
	        String str = new String(doc.getElementsByTagName("FRDOC").item(0).getTextContent());
	       // System.out.println("SUBTYPE :" + str);
	        document.addField("DOC_ID",  str.split(" ")[2]);

	    	nList = doc.getElementsByTagName(nList.item(1).getNodeName());
		    for (int temp = 0; temp < nList.getLength(); temp++) 
		    {
	            Node nNode = nList.item(temp);
	            document.addField("SUBTYPE", nNode.getNodeName());
	       // System.out.println("SUBTYPE :" + nNode.getNodeName());
	            if (nNode.getNodeType() == Node.ELEMENT_NODE) 
	            {
	               	Element eElement = (Element) nNode;
	               	if(eElement.getElementsByTagName("AGENCY").item(0) != null)
	               	{
	               		document.addField("AGENCY TYPE", eElement.getElementsByTagName("AGENCY").item(0).getTextContent());
		             //  	System.out.println("AGENCY TYPE: " 
						//	+ eElement.getElementsByTagName("AGENCY").item(0).getTextContent());
	               	}

	               	if(eElement.getElementsByTagName("SUBAGY").item(0) != null)
	               	{
	               		document.addField("SUBAGY", eElement.getElementsByTagName("SUBAGY").item(0).getTextContent());
		             //  	System.out.println("SUBAGY: " 
		                //    + eElement.getElementsByTagName("SUBAGY").item(0).getTextContent());
	               	}

	           		if(eElement.getElementsByTagName("SUBJECT").item(0) != null)
	           		{
	           			document.addField("SUBJECT",  eElement.getElementsByTagName("SUBJECT").item(0).getTextContent());
           			 //	System.out.println("SUBJECT: " 
                    	//	+ eElement.getElementsByTagName("SUBJECT").item(0).getTextContent());
	           		}
	           	}
	          // 	System.out.println();
	        }

        }
        else
        {
        	// NodeList nList = doc.getElementsByTagName("*");
        	nList = doc.getElementsByTagName(nList.item(1).getNodeName());
		    for (int temp = 0; temp < nList.getLength(); temp++) 
		    {
	            Node nNode = nList.item(temp);
	            document.addField("SUBTYPE", nNode.getNodeName());

	            if (nNode.getNodeType() == Node.ELEMENT_NODE) 
	            {
	               	Element eElement = (Element) nNode;
	               	if(eElement.getElementsByTagName("HD").item(0) != null)
	               	{
		               	document.addField("HD", eElement.getElementsByTagName("HD").item(0).getTextContent());
		        //       	System.out.println("HD: " + eElement.getElementsByTagName("HD").item(0).getTextContent());
		            }
	               	
	               	if(eElement.getElementsByTagName("FP").item(0) != null)
	               	{
	               		document.addField("FP", eElement.getElementsByTagName("FP").item(0).getTextContent());
	               	//	System.out.println("FP: " + eElement.getElementsByTagName("FP").item(0).getTextContent());
	               	}
	              	if(eElement.getElementsByTagName("FRDOC").item(0) != null)
	           		{
	           			String str = new String(eElement.getElementsByTagName("FRDOC").item(0).getTextContent());
	           			document.addField("DOC_ID",  str.split(" ")[2]);
	           		}
	           	}
	        //   	System.out.println();
	     	}
        }
	
        solrDoc.put(document);
	}
}
