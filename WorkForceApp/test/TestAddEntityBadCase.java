import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;




//import org.junit.Rule;
import org.junit.Test;
//import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
//import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
@RunWith(Parameterized.class)
public class TestAddEntityBadCase {
	
	//@Rule
	//public ExpectedException exception=ExpectedException.none();
	
	private String name;
	private String geography;
	private String metric;
	private String timeperiod;
	private String[] file_paths;
	private int[] related_entities;
	private boolean isbelief;
	private String person;
	private String strength;
	private String note;
	
	public TestAddEntityBadCase(String name, String geography
			                    ,String metric, String timeperiod
			                    ,String[] file_paths,int[] related_entities
			                    ,boolean isbelief,String person
			                    ,String strength,String note){
		
		this.name=name;
		Template.setgeography(geography);
		Template.setMetric(metric);	
		Template.setTimePeriod(timeperiod);
		this.file_paths=file_paths;
		
		this.related_entities=new int[related_entities.length];
		for(int i=0;i<related_entities.length;i++){
			this.related_entities[i]=related_entities[i];
		}	
		
		this.isbelief=isbelief;
		this.person=person;
		this.strength=strength;	
		this.note=note;
		
	}
	
	@Parameters
	public static Collection<Object[]> dataBad(){
		/*Bad Cases -> return error exception */
		int[] related_entities={5,2};		
	                         
		Object[][] data=new Object[][]{
	    					//name is null
	    					{null,"USA","0.36","2010 ~ 2015",null,related_entities,true,"Tom","undecided","note"},
	    					//strength is not any of "undecided", "belief", and "unbelief"
	    					{"relocate rate","USA","0.36","2010 ~ 2015",null,related_entities,true,"Tom","wrong","note"},
	    					//strength is null
	    					{"relocate rate","USA","0.36","2010 ~ 2015",null,related_entities,true,"Tom",null,"note"}};		      
		return Arrays.asList(data);
	}
	
	@Test
	public void testAddEntity() {
		//exception.expect(NullPointerException.class);
		//exception.expectMessage("Id, name, or strength can't be null.");
		EntityProcessor ep=new EntityProcessor();		
		assertEquals("",false,ep.addEntity(name,geography,metric,timeperiod,file_paths,
					related_entities,isbelief,person,strength,note));
	}

}