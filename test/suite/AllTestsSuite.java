package suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import controller.FeedbackServletTest;
import controller.FeedbackServletTest;
import dal.CategoriesDAOTest;
import dal.VnPayUtilTest;
import utils.ValidatorTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	FeedbackServletTest.class,
	CategoriesDAOTest.class,
	VnPayUtilTest.class,
	ValidatorTest.class
})
public class AllTestsSuite {
}


