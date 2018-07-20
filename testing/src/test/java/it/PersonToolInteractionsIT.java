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
		// kein User am Anfang
		assertEquals(null, t.getUser());
		assertEquals(false, t.isInUse());
		
		t.setUser(p);
		
		// inUse sollte true sein
		assertEquals(true, t.isInUse());

		// user sollte durch das Werkzeug referenziert werden
		assertEquals(p, t.getUser());
		
	}
	
	@Test
	public void toolOwnerShip_userReleasesTool_ToolShouldBeNotInUse(){
		t.setUser(p);
		
		// inUse sollte true sein
		assertEquals(true, t.isInUse());
		
		// Werkzeug wird nicht mehr genutzt 
		t.release();
		assertEquals(false, t.isInUse());
		assertEquals(null, t.getUser());
	}
}
