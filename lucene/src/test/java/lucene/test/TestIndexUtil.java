package lucene.test;

import lucene.IndexUtil;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Query;
import org.apache.lucene.util.Version;
import org.junit.Test;

public class TestIndexUtil {

	
	@Test
	public void testCreateIdx(){
		IndexUtil iu = new IndexUtil() ;
		iu.deleteAll();
		iu.createIndex();
	}
	
	@Test
	public void testReadDocsInfo(){
		IndexUtil iu = new IndexUtil(); 
		iu.readDocsInfo();
	}
	
	@Test
	public void testSearch(){
		IndexUtil iu = new IndexUtil() ;
		iu.searchDoc("visited"); 
	}
	
	@Test
	public void testSearch2(){
		IndexUtil iu = new IndexUtil() ;
		iu.searchDoc2("visited"); 
	}
	
	@Test
	public void testDelete(){
		IndexUtil iu = new IndexUtil() ;
		iu.deleteIndex(); 
	}
	
	@Test
	public void testUnDeleteAll(){
		IndexUtil iu = new IndexUtil();
		iu.recoverIndex(); 
	}
	
	@Test
	public void testForceDelete(){
		IndexUtil iu = new IndexUtil() ;
		iu.forceDelete(); 
	}
	
	@Test
	public void testDeleteAll(){
		IndexUtil iu = new IndexUtil();
		iu.deleteAll(); 
	}
	
	@Test
	public void testUpdate(){
		IndexUtil iu = new IndexUtil();
		iu.updateAll();
	}
	
	@Test
	public void testSearchByQueryParser() throws ParseException{
		IndexUtil iu = new IndexUtil();
		QueryParser parser = new QueryParser(Version.LUCENE_35, "content", new StandardAnalyzer(Version.LUCENE_35));
		parser.setAllowLeadingWildcard(true); 
		Query query = parser.parse("are beautiful");
		query = parser.parse("name:jake");
		query = parser.parse("email:*@qq.com");
		iu.searchByQueryParser(query, 10); 
	}
}
