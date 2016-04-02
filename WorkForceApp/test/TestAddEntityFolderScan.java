import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
@RunWith(Parameterized.class)
public class TestAddEntityFolderScan {
	@Parameter
	public String folderpath;
	
	@Parameters
	public static Collection<Object[]> data(){
										/*Good Cases */	                         
		Object[][]data=new Object[][]{{"C:\\Users\\Shin-Yi\\git\\WorkforceResearchGuideApp\\WorkForceApp\\FOLDERSCAN\\"}
									  /*Bad Cases */
									  //Empty Folder
									  ,{"C:\\Users\\Shin-Yi\\git\\WorkforceResearchGuideApp\\WorkForceApp\\EMPTYFOLDER\\"}
									  //Folder doesn't exist
									  ,{"C:\\Users\\Shin-Yi\\git\\WorkforceResearchGuideApp\\WorkForceApp\\NoFolder\\"}};      
		return Arrays.asList(data);
	}
	
	@Test
	public void testAddEntityFolderScan() {
		EntityProcessor ep=new EntityProcessor();
		ep.addEntityFolderScan(folderpath);
	}

}
