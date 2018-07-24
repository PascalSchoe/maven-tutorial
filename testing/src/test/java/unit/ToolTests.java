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
		
		
		// 1. Werkzeug anfaenglich nicht in Nutzung ?

		// 2. InUse aendern

		// 3. InUse nun true?
	}
}
