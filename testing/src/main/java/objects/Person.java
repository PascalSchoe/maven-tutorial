package objects;

public class Person {
	private int age;
	private String firstname;
	private String lastname;
	
	
	public Person(int age, String firstname, String lastname){
		this.age = age ;
		this.firstname = firstname;
		this.lastname = lastname;
	}
	
	
	
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
}
