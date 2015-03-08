package lucene;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Index;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.NumericField;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

/**
 * 索引工具
 * @author zf
 *
 */
public class IndexUtil {

	
	private Mail[] mails = {
			new Mail("zhangsan",2,"1","welcome to visited the space","aa@qq.com","2015-03-07"),
			new Mail("lisi",3,"2","hello boy","bb@163.com","2015-02-07"),
			new Mail("john",1,"3","my name is zf","cc@sina.com","2015-01-07"),
			new Mail("jetty",4,"4","out of the are question","dd@gmail.com","2014-12-07"),
			new Mail("mike",6,"5","you are beautiful","ee@qq.com","2014-11-08"),
			new Mail("jake",5,"6","run out of money","ff@163.com","2015-03-07"),
	};
	
	private Directory directory = null ;
	
	public IndexUtil(){
		try {
			directory = FSDirectory.open(new File("D:/lucene/02"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 创建索引
	 */
	public void createIndex(){
		IndexWriter idxWriter = null ;
		IndexWriterConfig writerCfg = new IndexWriterConfig(Version.LUCENE_35, new StandardAnalyzer(Version.LUCENE_35)) ; 
		try {
			idxWriter = new IndexWriter(directory, writerCfg) ;
			Document doc = null ;
			for (int i = 0; i < mails.length; i++) {
				doc = new Document() ;
				doc.add(new Field("content", mails[i].getContent() , Store.NO , Index.ANALYZED));
				doc.add(new Field("id", mails[i].getId(), Store.YES, Index.NOT_ANALYZED_NO_NORMS)); 
				doc.add(new Field("email", mails[i].getEmail(), Store.YES, Index.NOT_ANALYZED)); 
				doc.add(new Field("name", mails[i].getName(), Store.YES, Index.NOT_ANALYZED_NO_NORMS));   
				doc.add(new NumericField("attachs",Store.YES , true).setIntValue(mails[i].getAttachs()));
				doc.add(new NumericField("date", Store.YES,true).setLongValue(mails[i].getDate().getTime())); 
				float boost = 0f ;
				if(mails[i].getEmail().contains("@qq.com")){
					boost = 2.0f; 
				}else if(mails[i].getEmail().contains("@163.com")){
					boost = 1.8f; 
				}else if(mails[i].getEmail().contains("@gmail.com")){
					boost = 1.5f; 
				}else if(mails[i].getEmail().contains("@sina.com")){
					boost = 1.2f; 
				}else{
					boost = 1.0f ;
				}
				doc.setBoost(boost); 
				idxWriter.addDocument(doc); 
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
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
	 * 读取文档信息
	 */
	public void readDocsInfo(){
		IndexReader idxReader = null ;
		try {
			idxReader = IndexReader.open(directory) ;
			int maxDoc = idxReader.maxDoc();
			int numDocs = idxReader.numDocs();
			int delDocs = idxReader.numDeletedDocs();
			System.out.println("maxDoc：" + maxDoc); 
			System.out.println("numDoc：" + numDocs); 
			System.out.println("delDoc：" + delDocs); 
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(idxReader != null){
				try {
					idxReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 搜索文档信息
	 * @param key 关键字（邮箱/标题/内容）
	 */
	public void searchDoc(String key){
		IndexReader idxReader = null ;
		IndexSearcher idxSearcher = null ;
		try {
			idxReader = IndexReader.open(directory) ;
			idxSearcher = new IndexSearcher(idxReader);
			QueryParser queryParser = new QueryParser(Version.LUCENE_35, "content", new StandardAnalyzer(Version.LUCENE_35)) ;
			Query query = queryParser.parse(key) ;
			TopDocs topDocs = idxSearcher.search(query, 10) ;
			for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
				int docID = scoreDoc.doc;
				Document document = idxSearcher.doc(docID);
				String content = document.get("content");
				String mail = document.get("email");
				String id = document.get("id");
				String name = document.get("name");
				float boost = document.getBoost();
				int attachs = Integer.parseInt((document.get("attachs")));
				long date = Long.parseLong(document.get("date"));
				System.out.printf("id:%s,boost:%f,score:%f,mail:%s,name:%s,attachs:%d,date:%tF,content:%s%n" ,
						id ,boost,scoreDoc.score, mail , name , attachs,date,content);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(idxReader != null){
				try {
					idxReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(idxSearcher != null){
				try {
					idxSearcher.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	/**
	 * 搜索文档信息
	 * @param key 关键字（邮箱/标题/内容）
	 */
	public void searchDoc2(String key){
		IndexReader idxReader = null ;
		IndexSearcher idxSearcher = null ;
		try {
			idxReader = IndexReader.open(directory) ;
			idxSearcher = new IndexSearcher(idxReader);
			TermQuery query = new TermQuery(new Term("content", key)) ;
			TopDocs topDocs = idxSearcher.search(query, 10) ;
			for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
				int docID = scoreDoc.doc;
				Document document = idxSearcher.doc(docID);
				String content = document.get("content");
				String mail = document.get("email");
				String id = document.get("id");
				String name = document.get("name");
				System.out.printf("id:%s,mail:%s,name:%s,content:%s%n" ,
						id , mail , name , content);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(idxReader != null){
				try {
					idxReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(idxSearcher != null){
				try {
					idxSearcher.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 删除索引
	 */
	public void deleteIndex(){
		IndexWriter idxWriter = null ;
		IndexWriterConfig writerCfg = new IndexWriterConfig(Version.LUCENE_35, new StandardAnalyzer(Version.LUCENE_35)) ; 
		try {
			idxWriter = new IndexWriter(directory, writerCfg) ;
			
			idxWriter.deleteDocuments(new Term("id", "2")); 
			
			System.out.println("删除成功");
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
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
	 * 恢复被删除的文档
	 */
	public void recoverIndex(){
		IndexReader idxReader = null ;
		try {
			idxReader = IndexReader.open(directory , false) ;
			idxReader.undeleteAll();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(idxReader != null){
				try {
					idxReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 强制清空回收站
	 */
	public void forceDelete(){
		IndexWriter idxWriter = null ;
		IndexWriterConfig writerCfg = new IndexWriterConfig(Version.LUCENE_35, new StandardAnalyzer(Version.LUCENE_35)) ; 
		try {
			idxWriter = new IndexWriter(directory, writerCfg) ;
			idxWriter.forceMergeDeletes(); 
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
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
	 * 清空所有索引
	 */
	public void deleteAll(){
		IndexWriter idxWriter = null ;
		IndexWriterConfig writerCfg = new IndexWriterConfig(Version.LUCENE_35, new StandardAnalyzer(Version.LUCENE_35)) ; 
		try {
			idxWriter = new IndexWriter(directory, writerCfg) ;
			idxWriter.deleteAll();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
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
	 * 更新索引
	 * Lucene的更新其实是先删除，再添加
	 */
	public void updateAll(){
		IndexWriter idxWriter = null ;
		IndexWriterConfig writerCfg = new IndexWriterConfig(Version.LUCENE_35, new StandardAnalyzer(Version.LUCENE_35)) ; 
		try {
			idxWriter = new IndexWriter(directory, writerCfg) ;
			Document document = new Document() ;
			document.add(new Field("content", "xxxxxxxxxx hello", Store.NO , Index.ANALYZED_NO_NORMS));
			document.add(new Field("id", "2", Store.YES, Index.NOT_ANALYZED)); 
			document.add(new Field("email", "xxxxxxxx", Store.YES, Index.ANALYZED)); 
			document.add(new Field("name", "xxxxxxxx", Store.YES, Index.NOT_ANALYZED));   
			idxWriter.updateDocument(new Term("id", "2"), document); 
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
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
	 * 根据QueryParser查询对象
	 * @param query
	 * @param num
	 */
	public void searchByQueryParser(Query query , int num){
		IndexReader idxReader = null ;
		IndexSearcher idxSearcher = null ;
		try {
			idxReader = IndexReader.open(directory) ;
			idxSearcher = new IndexSearcher(idxReader);
			TopDocs topDocs = idxSearcher.search(query, 10) ;
			for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
				int docID = scoreDoc.doc;
				Document document = idxSearcher.doc(docID);
				String content = document.get("content");
				String mail = document.get("email");
				String id = document.get("id");
				String name = document.get("name");
				float boost = document.getBoost();
				int attachs = Integer.parseInt((document.get("attachs")));
				long date = Long.parseLong(document.get("date"));
				System.out.printf("id:%s,boost:%f,score:%f,mail:%s,name:%s,attachs:%d,date:%tF,content:%s%n" ,
						id ,boost,scoreDoc.score, mail , name , attachs,date,content);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(idxReader != null){
				try {
					idxReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(idxSearcher != null){
				try {
					idxSearcher.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	
}
