package ca.ubc.cs.mkamimu.testcaseview10.marker;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class DescriptionDataForWritingTest {

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
	public final void testGetDescriptionInOrder() {
		DescriptionDataForWriting dd = new DescriptionDataForWriting();

		DescriptionData ddi = new DescriptionData();
		ddi.setClassname("testclass");
		ddi.setTestname("testname");
		ddi.setArguments("testArguments");
		ddi.setAssertInFlag(false);
		ddi.setOccurence(0);
		ddi.setStartcolumn(1);
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
		
		List<DescriptionData> ddlist = dd.getDescriptionInOrder();
		assertEquals("description", ddlist.get(0).getDescription());
		assertEquals("description2", ddlist.get(1).getDescription());
		
		//fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testGetDescriptionInOrder2() {
		DescriptionDataForWriting dd = new DescriptionDataForWriting();

		DescriptionData ddi = new DescriptionData();
		ddi.setClassname("testclass");
		ddi.setTestname("testname");
		ddi.setArguments("testArguments");
		ddi.setAssertInFlag(false);
		ddi.setOccurence(0);
		ddi.setStartcolumn(1);
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
		
		DescriptionData ddi3 = new DescriptionData();
		ddi3.setClassname("testclass");
		ddi3.setTestname("testname");
		ddi3.setArguments("testArguments3");
		ddi3.setAssertInFlag(false);
		ddi3.setOccurence(0);
		ddi3.setStartcolumn(12);
		ddi3.setDescription("description3");
		dd.add(ddi3);
		
		List<DescriptionData> ddlist = dd.getDescriptionInOrder();
		assertEquals("description", ddlist.get(0).getDescription());
		assertEquals("description2", ddlist.get(1).getDescription());
		assertEquals("description3", ddlist.get(2).getDescription());
		
		//fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testGetDescriptionInOrder3() {
		DescriptionDataForWriting dd = new DescriptionDataForWriting();

		DescriptionData ddi = new DescriptionData();
		ddi.setClassname("testclass");
		ddi.setTestname("testname");
		ddi.setArguments("testArguments");
		ddi.setAssertInFlag(false);
		ddi.setOccurence(0);
		ddi.setStartcolumn(1);
		ddi.setDescription("description");
		dd.add(ddi);
		
		DescriptionData ddi2 = new DescriptionData();
		ddi2.setClassname("testclass");
		ddi2.setTestname("testname");
		ddi2.setArguments("testArguments2");
		ddi2.setAssertInFlag(false);
		ddi2.setOccurence(10);
		ddi2.setStartcolumn(20);
		ddi2.setDescription("description2");
		dd.add(ddi2);
		
		DescriptionData ddi3 = new DescriptionData();
		ddi3.setClassname("testclass");
		ddi3.setTestname("testname");
		ddi3.setArguments("testArguments3");
		ddi3.setAssertInFlag(false);
		ddi3.setOccurence(0);
		ddi3.setStartcolumn(12);
		ddi3.setDescription("description3");
		dd.add(ddi3);
		
		List<DescriptionData> ddlist = dd.getDescriptionInOrder();
		assertEquals("description", ddlist.get(0).getDescription());
		assertEquals("description3", ddlist.get(1).getDescription());
		assertEquals("description2", ddlist.get(2).getDescription());
		
		//fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testGetDescriptionInOrder4() {
		DescriptionDataForWriting dd = new DescriptionDataForWriting();

		DescriptionData ddi = new DescriptionData();
		ddi.setClassname("testclass");
		ddi.setTestname("testname");
		ddi.setArguments("testArguments");
		ddi.setAssertInFlag(false);
		ddi.setOccurence(0);
		ddi.setStartcolumn(1);
		ddi.setDescription("description");
		dd.add(ddi);
		
		DescriptionData ddi2 = new DescriptionData();
		ddi2.setClassname("testclass");
		ddi2.setTestname("testname1");
		ddi2.setArguments("testArguments2");
		ddi2.setAssertInFlag(false);
		ddi2.setOccurence(0);
		ddi2.setStartcolumn(2);
		ddi2.setDescription("description2");
		dd.add(ddi2);
		
		List<DescriptionData> ddlist = dd.getDescriptionInOrder();
		assertEquals("description", ddlist.get(0).getDescription());
		assertEquals("description2", ddlist.get(1).getDescription());
		
		//fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testGetDescriptionInOrder5() {
		DescriptionDataForWriting dd = new DescriptionDataForWriting();

		DescriptionData ddi = new DescriptionData();
		ddi.setClassname("testclass");
		ddi.setTestname("testname");
		ddi.setArguments("testArguments");
		ddi.setAssertInFlag(false);
		ddi.setOccurence(0);
		ddi.setStartcolumn(1);
		ddi.setDescription("description");
		dd.add(ddi);
		
		DescriptionData ddi2 = new DescriptionData();
		ddi2.setClassname("testclass");
		ddi2.setTestname("testname1");
		ddi2.setArguments("testArguments2");
		ddi2.setAssertInFlag(false);
		ddi2.setOccurence(0);
		ddi2.setStartcolumn(2);
		ddi2.setDescription("description2");
		dd.add(ddi2);
		
		DescriptionData ddi3 = new DescriptionData();
		ddi3.setClassname("testclass");
		ddi3.setTestname("testname");
		ddi3.setArguments("testArguments3");
		ddi3.setAssertInFlag(false);
		ddi3.setOccurence(0);
		ddi3.setStartcolumn(3);
		ddi3.setDescription("description3");
		dd.add(ddi3);
		
		DescriptionData ddi4 = new DescriptionData();
		ddi4.setClassname("testclass");
		ddi4.setTestname("testname");
		ddi4.setArguments("testArguments4");
		ddi4.setAssertInFlag(false);
		ddi4.setOccurence(0);
		ddi4.setStartcolumn(4);
		ddi4.setDescription("description4");
		dd.add(ddi4);


		
		List<DescriptionData> ddlist = dd.getDescriptionInOrder();
		assertEquals("description", ddlist.get(0).getDescription());
		assertEquals("description2", ddlist.get(1).getDescription());
		assertEquals("description3", ddlist.get(2).getDescription());
		assertEquals("description4", ddlist.get(3).getDescription());

		
		//fail("Not yet implemented"); // TODO
	}


	@Test
	public final void testGetDescriptionInOrder6() {
		DescriptionDataForWriting dd = new DescriptionDataForWriting();

		DescriptionData ddi = new DescriptionData();
		ddi.setClassname("testclass");
		ddi.setTestname("testname");
		ddi.setArguments("testArguments");
		ddi.setAssertInFlag(false);
		ddi.setOccurence(0);
		ddi.setStartcolumn(1);
		ddi.setDescription("description");
		dd.add(ddi);
		
		DescriptionData ddi2 = new DescriptionData();
		ddi2.setClassname("testclass");
		ddi2.setTestname("testname1");
		ddi2.setArguments("testArguments2");
		ddi2.setAssertInFlag(false);
		ddi2.setOccurence(0);
		ddi2.setStartcolumn(2);
		ddi2.setDescription("description2");
		dd.add(ddi2);
		
		DescriptionData ddi3 = new DescriptionData();
		ddi3.setClassname("testclass");
		ddi3.setTestname("testname");
		ddi3.setArguments("testArguments3");
		ddi3.setAssertInFlag(false);
		ddi3.setOccurence(0);
		ddi3.setStartcolumn(4);
		ddi3.setDescription("description3");
		dd.add(ddi3);
		
		DescriptionData ddi4 = new DescriptionData();
		ddi4.setClassname("testclass");
		ddi4.setTestname("testname");
		ddi4.setArguments("testArguments4");
		ddi4.setAssertInFlag(false);
		ddi4.setOccurence(0);
		ddi4.setStartcolumn(3);
		ddi4.setDescription("description4");
		dd.add(ddi4);

		List<DescriptionData> ddlist = dd.getDescriptionInOrder();
		assertEquals("description", ddlist.get(0).getDescription());
		assertEquals("description2", ddlist.get(1).getDescription());
		assertEquals("description3", ddlist.get(3).getDescription());
		assertEquals("description4", ddlist.get(2).getDescription());

		
		//fail("Not yet implemented"); // TODO
	}

}
