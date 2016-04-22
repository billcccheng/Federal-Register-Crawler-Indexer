/* 
Notice:
Solr core is name test_core

1. SolrClient vs SolrServer? Not sure which one to use but I chosed SolrServer.
2. Fetching files from documents/ for now. We should integrate this with Crawler.
3. Multi-threading not implemented yet.
4. Deleting index in core: http://localhost:8983/solr/test_core/update?stream.body=%3Cdelete%3E%3Cquery%3E*:*%3C/query%3E%3C/delete%3E&commit=true
5. Run Make to compile and run program.
6. Might have to index url too?
*/

import java.util.concurrent.*;
import java.util.ArrayList;
import java.io.IOException;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;


public class Indexer {

	public static void main(String[] args) 
	{
		int parserThreads = 4;
		// SolrClient client = new SolrClient(/*initializer here*/);
		HttpSolrServer server = new HttpSolrServer("http://localhost:8983/solr/test_core");


		BlockingQueue<String> xmlDocuments = new ArrayBlockingQueue<String>(140000);
		BlockingQueue<SolrInputDocument> solrDocs = new ArrayBlockingQueue<SolrInputDocument>(140000);

		ArrayList<SolrInputDocument> solrBuffer = new ArrayList<SolrInputDocument>();
		
		Get_Doc get = new Get_Doc(xmlDocuments);
		Put_Doc put_doc = new Put_Doc(solrDocs, server);
		Thread get_thread = new Thread(get);
		Thread put_doc_thread = new Thread(put_doc);

		Parse_Document parsingXML = new Parse_Document(xmlDocuments, solrDocs);
		Thread parsingXMLThread = new Thread(parsingXML);
		
		get_thread.start();
		parsingXMLThread.start();
		put_doc_thread.start();

		try
		{
			get_thread.join();
			xmlDocuments.add("STOP");
		}
		catch(InterruptedException e)
		{
			e.printStackTrace();
		}
		
		
		// System.out.println(xmlDocuments.size());
		//ArrayList of Parse_Document threads
		
		//join all the threads
		
		try
		{
			parsingXMLThread.join();
			put_doc_thread.join();
		}
		catch(InterruptedException e)
		{
			e.printStackTrace();
		}

		//TODO:
		/*SolrDoc.add(new SolrInputDocument())*/
		
		// solrDocs.drainTo(solrBuffer);

		try
		{
			// server.add(solrBuffer);
			server.commit(); 
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}



		//NOTE!! drainTo NOT THREAD SAFE!! Only main thread may be running!
		//ALL OTHER THREADS MUST BE DEAD HERE! USE JOIN!
		// solrDoc.drainTo(solrBuffer);
		
		//adds documents to the client
		// client.add(solrBuffer);
		//commits documents to client
		// client.commit();

	}
}
