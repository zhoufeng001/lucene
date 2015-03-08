package lucene;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Mail {
	
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	private String name ;
	
	private int attachs ;
	
	private String id ;
	
	private String content ;
	
	private String email ;

	private Date date ;
	
	public Mail() {}

	public Mail(String name, int attachs, String id, String content,
			String email , String date) {
		this.name = name;
		this.attachs = attachs;
		this.id = id;
		this.content = content;
		this.email = email;
		try {
			this.date = sdf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAttachs() {
		return attachs;
	}

	public void setAttachs(int attachs) {
		this.attachs = attachs;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
}
