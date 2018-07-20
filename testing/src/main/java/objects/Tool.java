package objects;

public class Tool {
	private String name;
	private String brandName;
	private Person user;
	private boolean inUse;
	
	
	public Tool(String name, String brandName) {
			this.name = name;
			this.brandName = brandName;
			this.inUse = false;
			
			this.user = null;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public boolean isInUse() {
		return inUse;
	}

	public void setInUse(boolean inUse) {
		this.inUse = inUse;
	}

	public void release() {
		this.inUse = false;
		this.user = null;
	}

	public Person getUser() {
		return user;
	}

	public void setUser(Person user) {
		this.user = user;
		this.inUse = true;
	}

}
