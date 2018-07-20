package unit;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import objects.Person;

public class PersonTests {
	Person p; 
	
	@BeforeEach
	void init(){
		p = new Person(32, "Bernd", "seinNachname");
	}
	
	@DisplayName("Setter und der Person Klasse sollte funktionieren")
	@ParameterizedTest
	@CsvSource({"65, Horst, Meier", "29, Pascal, Schönfeld"})
	public void setter_changingValues_shouldSucceed(int age, String firstname, String lastname){
		
		// wurde die Person erfolgreich erstellt?
		assertEquals(32, p.getAge());
		assertEquals("Bernd", p.getFirstname());
		assertEquals("seinNachname", p.getLastname());
		
		p.setAge(age);
		p.setFirstname(firstname);
		p.setLastname(lastname);

		// haben die Setter das gemacht was wir wollten?
		assertEquals(age, p.getAge());
		assertEquals(firstname, p.getFirstname());
		assertEquals(lastname, p.getLastname());
	}
}
