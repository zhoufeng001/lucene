package lucene;

import java.io.File;
import java.io.FileReader;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Index;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;



/**
 * Hello world!
 *
 */
public class HelloLucene 
{

	/**
	 * 创建索引
	 */
	public void createIndex(){
		//创建索引存放目录
		//		Directory directory = new RAMDirectory() ;
		
		IndexWriter idxWriter = null ;
		try {

			File idxDir = new File("D:/lucene/idx"); 
			Directory directory = FSDirectory.open(idxDir) ;
			
			IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_35, 
					new StandardAnalyzer(Version.LUCENE_35)) ;

			//创建IndexWriter
			idxWriter = new IndexWriter(directory, config);

			//创建Document
			Document doc = null ;
			File dir = new File("D:/lucene/example");
			for (File file : dir.listFiles()) {
				doc = new Document() ;
				doc.add(new Field("content", new FileReader(file)));  
				doc.add(new Field("filename" , file.getName() , Store.YES , Index.NOT_ANALYZED));
				doc.add(new Field("filepath", file.getPath(), Store.YES, Index.NOT_ANALYZED)); 

				//写入到Directory
				idxWriter.addDocument(doc); 
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(idxWriter != null){
				try {
					idxWriter.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

	}

	/**
	 * 搜索
	 */
	public void search(){
		
		try{
			//创建Directory
			File idxDir = new File("D:/lucene/idx"); 
			Directory directory = FSDirectory.open(idxDir);
			
			//创建IndexReader
			IndexReader idxReader = IndexReader.open(directory);
			
			//创建IndexSearch
			IndexSearcher idxSearcher = new IndexSearcher(idxReader);
			
			//创建搜索参数QueryParser
			QueryParser queryParser = new QueryParser(Version.LUCENE_35, "content" , 
					new StandardAnalyzer(Version.LUCENE_35));
			
			//创建搜索参数Query
			Query query = queryParser.parse("third");
			
			//执行搜索得到TopDocs
			TopDocs topDocs = idxSearcher.search(query, 10) ;
			
			ScoreDoc[] docs = topDocs.scoreDocs ;
			
			for (ScoreDoc doc : docs) {
				//从ScoreDoc中得到Document对象
				Document document = idxSearcher.doc(doc.doc) ;
				System.out.println(document.get("filename") + "   " + document.get("filepath"));
			}
			
			idxReader.close(); 
			idxSearcher.close();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			
		}
		
	}

}
