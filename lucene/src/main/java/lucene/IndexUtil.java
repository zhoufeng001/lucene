package lucene;

/**
 * 索引工具
 * @author zf
 *
 */
public class IndexUtil {
	
	private String ids[] = {"1","2","3","4","5","6"} ;
	
	private String emails[] = {
			"aa@qq.com",
			"bb@sina.com",
			"cc@163.com",
			"dd@qq.com",
			"ee@gmail.com",
			"ff@163.com",
	} ;
	
	private String contents[] = {
			"welcome to visited the space" ,
			"hello boy" ,
			"my name is zf" ,
			"i like football" ,
			"how are you" ,
			"how are you , and you ?" ,
	} ;
	private int[] attachs = {2,3,1,4,6,5};
	
	private String[] names = {
			"zhangsan","lisi","john","jetty","mike","jake"
	};
	
	private Mail[] mails = {
			new Mail("zhangsan",2,"1","welcome to visited the space","aa@qq.com"),
			new Mail("lisi",3,"2","hello boy","aa@qq.com"),
			new Mail("john",1,"3","my name is zf","aa@qq.com"),
			new Mail("jetty",4,"4","welcome to visited the space","aa@qq.com"),
			new Mail("mike",6,"5","welcome to visited the space","aa@qq.com"),
			new Mail("jake",5,"6","welcome to visited the space","aa@qq.com"),
	};
	
	
	
}
