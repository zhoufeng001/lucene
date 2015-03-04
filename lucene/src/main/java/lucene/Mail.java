package lucene;

public class Mail {

	private String name ;
	
	private int attachs ;
	
	private String id ;
	
	private String content ;
	
	private String email ;

	public Mail() {}

	public Mail(String name, int attachs, String id, String content,
			String email) {
		this.name = name;
		this.attachs = attachs;
		this.id = id;
		this.content = content;
		this.email = email;
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
	
}
