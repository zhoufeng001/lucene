package lucene.test;

import lucene.HelloLucene;

import org.junit.Test;

public class HelloTest {
	
	@Test
	public void testCreateIndex(){
		HelloLucene hl = new HelloLucene() ;
		hl.createIndex(); 
	}
	
	@Test
	public void testSearch(){
		HelloLucene hl = new HelloLucene();
		hl.search();
	}

}
