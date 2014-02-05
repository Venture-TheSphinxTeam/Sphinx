package json;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import models.EntityCollection;

import org.junit.*;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class EntityCollectionTest {

	protected static String testJSON;
	protected ObjectMapper om;
	
	public EntityCollectionTest(){}
	
	
	
	@BeforeClass
	public static void beforeClass() throws IOException{
		
		System.out.println(System.getProperty("user.dir") +
				File.separator+"test"+
				File.separator+"json"+
				File.separator+"EntityCollectionTest.txt");
		File file = new File(System.getProperty("user.dir") +
				File.separator+"test"+
				File.separator+"json"+
				File.separator+"EntityCollectionTest.txt");
		Scanner sc;
		sc = new Scanner(file);
		testJSON = sc.nextLine();
		
	}
	
	
	@Before
	public void setup(){
		om = new ObjectMapper();
	}
	
	
	
	@Test
	public void testEntityCollectionParsing() throws JsonParseException, JsonMappingException, IOException{
	om.readValue(testJSON, EntityCollection.class);
		
		
	}
	
	
}
