package it;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import objects.Person;
import objects.Tool;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PersonToolInteractionsIT {
	
	Person p; 
	Tool t;
	
	@BeforeEach
	void init(){
		p = new Person(29, "Pascal", "Schönfeld");
		t = new Tool("Blender9001", "Kitchen Stuff and Co");
	}
	
	
	@Test
	public void toolOwnerShip_personPicksTool_ToolShouldHaveUserReference(){
		// 1. kein User am Anfang
		
		// 2 . User setzen 

		// 3. inUse sollte true sein

		// 4. Referenz auf Person vorhanden? 
		
	}
	
	@Test
	public void toolOwnerShip_userReleasesTool_ToolShouldBeNotInUse(){
		// 1. user setzen
		
		// 2. inUse sollte true sein
		
		// 3. Werkzeug freigeben

		// 4. Werkzeug nicht mehr in Nutzung und es gibt keine Referenz mehr auf den vorherigen Nutzer
	}
}
