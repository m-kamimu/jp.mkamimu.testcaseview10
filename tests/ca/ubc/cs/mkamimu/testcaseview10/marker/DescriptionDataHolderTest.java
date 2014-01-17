package ca.ubc.cs.mkamimu.testcaseview10.marker;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class DescriptionDataHolderTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public final void testClearNextDescriptionData() {
		DescriptionDataHolder dd = new DescriptionDataHolder();
		dd.clearNextDescriptionData();
		//fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testGetNextItem() {
		DescriptionDataHolder dd = new DescriptionDataHolder();
		dd.clearNextDescriptionData();
		
		DescriptionData ddi = new DescriptionData();
		ddi.setClassname("testclass");
		ddi.setTestname("testname");
		ddi.setArguments("testArguments");
		ddi.setAssertInFlag(false);
		ddi.setOccurence(0);
		ddi.setStartcolumn(10);
		ddi.setDescription("description");
		dd.add(ddi);
		
		DescriptionData ddi2 = new DescriptionData();
		ddi2.setClassname("testclass");
		ddi2.setTestname("testname");
		ddi2.setArguments("testArguments2");
		ddi2.setAssertInFlag(false);
		ddi2.setOccurence(0);
		ddi2.setStartcolumn(10);
		ddi2.setDescription("description2");
		dd.add(ddi2);
		
		assertEquals(ddi2, dd.getNextItem("testname"));
		assertEquals(ddi, dd.getNextItem("testname"));

		//fail("Not yet implemented"); // TODO
	}
	
	@Test
	public final void testGetNextItem2() {
		DescriptionDataHolder dd = new DescriptionDataHolder();
		dd.clearNextDescriptionData();
		
		DescriptionData ddi = new DescriptionData();
		ddi.setClassname("testclass");
		ddi.setTestname("testname");
		ddi.setArguments("testArguments");
		ddi.setAssertInFlag(false);
		ddi.setOccurence(0);
		ddi.setStartcolumn(10);
		ddi.setDescription("description");
		dd.add(ddi);
		
		DescriptionData ddi2 = new DescriptionData();
		ddi2.setClassname("testclass");
		ddi2.setTestname("testname");
		ddi2.setArguments("testArguments2");
		ddi2.setAssertInFlag(false);
		ddi2.setOccurence(10);
		ddi2.setStartcolumn(10);
		ddi2.setDescription("description2");
		dd.add(ddi2);
		
		assertEquals(ddi, dd.getNextItem("testname"));
		assertEquals(ddi2, dd.getNextItem("testname"));

		//fail("Not yet implemented"); // TODO
	}


	@Test
	public final void testGetNextItem3() {

		DescriptionDataHolder dd = new DescriptionDataHolder();
		dd.clearNextDescriptionData();

		DescriptionData ddi = new DescriptionData();
		ddi.setClassname("testclass");
		ddi.setTestname("testname");
		ddi.setArguments("testArguments");
		ddi.setAssertInFlag(false);
		ddi.setOccurence(0);
		ddi.setStartcolumn(10);
		ddi.setDescription("description");
		dd.add(ddi);

		assertEquals(null, dd.getNextItem("testname2"));
		assertEquals(ddi, dd.getNextItem("testname"));
		
		//fail("Not yet implemented"); // TODO
	}


	@Test
	public final void testAdd() {
		DescriptionDataHolder dd = new DescriptionDataHolder();
		dd.clearNextDescriptionData();
	
		DescriptionData ddi = new DescriptionData();
		ddi.setClassname("testclass");
		ddi.setTestname("testname");
		ddi.setArguments("testArguments");
		ddi.setAssertInFlag(false);
		ddi.setOccurence(0);
		ddi.setStartcolumn(10);
		ddi.setDescription("description");
		dd.add(ddi);
	
		assertEquals(ddi, dd.getNextItem("testname"));
		
		//fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testGetTestName() {
		DescriptionDataHolder dd = new DescriptionDataHolder();
		dd.clearNextDescriptionData();

		DescriptionData ddi = new DescriptionData();
		ddi.setClassname("testclass");
		ddi.setTestname("testname");
		ddi.setArguments("testArguments");
		ddi.setAssertInFlag(false);
		ddi.setOccurence(0);
		ddi.setStartcolumn(10);
		ddi.setDescription("description");
		dd.add(ddi);
		
		List<String> testname = dd.getTestName("testclass");
		assertEquals("testname", testname.get(0));
		
	}

}
