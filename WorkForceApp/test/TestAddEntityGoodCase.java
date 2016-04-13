import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import workforceresearch.Entity;
import workforceresearch.EntityProcessor;
@RunWith(Parameterized.class)
public class TestAddEntityGoodCase {
	
	@Parameter
	public Entity actual_entity_good;
	
	@Parameters
	public static Collection<Entity[]> dataGood(){
		/*Successful Cases*/
		int[] related_entities={1};
		Entity entity_actual=new Entity(1,"relocate rate","USA","0.36","2010 ~ 2015",null,related_entities,true,"Tom","undecided","note");
		//country is null
//		int[] related_entities1={5,3};
//		Entity entity_actual1=new Entity(1,"relocate rate",null,"0.36","2010 ~ 2015",null,related_entities1,true,"Tom","undecided","note");		
//		//metric is null
//		Entity entity_actual2=new Entity(1,"relocate rate","USA",null,"2010 ~ 2015",null,related_entities1,true,"Tom","undecided","note");
//		//time period is null
//		Entity entity_actual3=new Entity(1,"relocate rate","USA","0.36",null,null,related_entities1,true,"Tom","undecided","note");
//		//file_paths is not null
//		String[] file_paths={"\\XLSX\\entities.xlsx"};
//		Entity entity_actual4=new Entity(1,"relocate rate","USA","0.36","2010 ~ 2015",file_paths,related_entities1,true,"Tom","undecided","note");
//		//related_entities is null
//		int[] related_entities2={};
//		Entity entity_actual5=new Entity(1,"relocate rate","USA","0.36","2010 ~ 2015",null,related_entities2,true,"Tom","undecided","note");
//		//person is null
//		Entity entity_actual6=new Entity(1,"relocate rate","USA","0.36","2010 ~ 2015",null,related_entities1,true,null,"belief","note");
//		//isbelief is upper case or lower case
//		Entity entity_actual7=new Entity(1,"relocate rate","USA","0.36","2010 ~ 2015",null,related_entities1,true,null,"UbelIef","note");
		
		
		Entity[][] data=new Entity[][]{{entity_actual}};
//									{entity_actual1},
//									{entity_actual2},
//									{entity_actual3},
//									{entity_actual4},
//									{entity_actual5},
//									{entity_actual6},
//									{entity_actual7}};
											      
		return Arrays.asList(data);
	}
	
	@Test
	public void testAddEntity() {
		EntityProcessor ep=new EntityProcessor();
		assertEquals("",false,ep.addEntity(actual_entity_good.getName()
				                          ,actual_entity_good.getgeography(),actual_entity_good.getMetric()
				                          ,actual_entity_good.getTimePeriod(),actual_entity_good.getFilePaths()
				                          ,actual_entity_good.getRelatedEntities(),actual_entity_good.getIsBelief()
				                          ,actual_entity_good.getPerson(),actual_entity_good.getStrength().toString()
				                          ,actual_entity_good.getNote()));
	}

}