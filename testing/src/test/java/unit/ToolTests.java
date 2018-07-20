package unit;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import objects.Tool;

public class ToolTests {
	Tool t; 
	
	@BeforeEach
	void init(){
		t = new Tool("blender9001", "kitchen stuff and co");
	}
	
	@DisplayName("Zustand von \"inUse\" sollte wechselbar sein.")
	@Test
	public void inUse_change_shouldSucceed(){
		
		
		// anfaenglich sollte das Werzeug nicht in Benutzung sein
		assertEquals(false,t.isInUse());
		

		// hat die Aenderung funktioniert?
		t.setInUse(true);
		assertEquals(true, t.isInUse());
	}
}
