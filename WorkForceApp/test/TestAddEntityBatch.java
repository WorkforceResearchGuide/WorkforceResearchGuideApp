import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)

public class TestAddEntityBatch {
	@Parameter
	public String filepath;
	
	@Parameters
	public static Collection<Object[]> data(){
										/*Good Cases */	                         
		Object[][]data=new Object[][]{{"C:\\Users\\Shin-Yi\\git\\WorkforceResearchGuideApp\\WorkForceApp\\CSV\\entitiesGood.csv"}
									  /*Bad Cases */
									  //null name, null isBelief, null Strength
									  ,{"C:\\Users\\Shin-Yi\\git\\WorkforceResearchGuideApp\\WorkForceApp\\CSV\\entitiesBad.csv"}
									  //filepath doesn't exist
									  ,{"\\XLSX\\notexist.csv"}
									  ,{"..\\NOTEX"}};		      
		return Arrays.asList(data);
	}
	
	@Test
	public void testAddEntityBatch() {
		EntityProcessor ep=new EntityProcessor();
		ep.addEntityBatch(filepath);
	}

}
