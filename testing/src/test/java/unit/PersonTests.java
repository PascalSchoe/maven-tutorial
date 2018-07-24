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
		
		// 1. alle Felder der Person auf Richtigkeit testen
		
		// 2. Felder neu belegen mit Parametern aus Methode

		// 3. Alles richtig gesetzt?
	}
}
