import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;


@RunWith(Suite.class)
@SuiteClasses({ TestAddEntityGoodCase.class, TestAddEntityBadCase.class,TestAddEntityBatch.class })
public class AllTests {
	
}
