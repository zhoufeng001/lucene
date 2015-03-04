package lucene.lucene;

import org.junit.Test;

public class HelloTest {
	
	@Test
	public void testCreateIndex(){
		HelloLucene hl = new HelloLucene() ;
		hl.createIndex(); 
	}

}
