import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import java.util.concurrent.*;

public class Put_Doc implements Runnable
{
    BlockingQueue<SolrInputDocument> solrDoc;
    HttpSolrServer server;

    Put_Doc(BlockingQueue<SolrInputDocument> solrDoc, HttpSolrServer server)
    {
        this.solrDoc = solrDoc;
        this.server = server;
    }

    public void run()
    {
        while(true){
            SolrInputDocument doc = null;
            try{
                doc = solrDoc.take();
            } catch(Exception e){
                e.printStackTrace();
            }

            try{
                server.add(doc);
            } catch(Exception e){
                e.printStackTrace();
            }

            // if(solrDoc.peek == null)
            // {
            //     break;
            // }
        }
    }
}