package ca.ubc.cs.mkamimu.testcaseview10.marker;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class DescriptionDataTest {

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
	public final void testGetOccurence() {
		DescriptionData dd = new DescriptionData();
		dd.setOccurence(10);
		
		assertEquals(10, dd.getOccurence());
	}

	@Test
	public final void testSetOccurence() {
		DescriptionData dd = new DescriptionData();
		dd.setOccurence(10);
		
		assertEquals(10, dd.getOccurence());
	}

	@Test
	public final void testGetStartcolumn() {
		DescriptionData dd = new DescriptionData();
		dd.setStartcolumn(10);
		
		assertEquals(10, dd.getStartcolumn());
	}

	@Test
	public final void testSetStartcolumn() {
		DescriptionData dd = new DescriptionData();
		dd.setStartcolumn(10);
		
		assertEquals(10, dd.getStartcolumn());
	}

	@Test
	public final void testGetTestname() {
		DescriptionData dd = new DescriptionData();
		dd.setTestname("testname");
		
		assertEquals("testname", dd.getTestname());
	}

	@Test
	public final void testSetTestname() {
		DescriptionData dd = new DescriptionData();
		dd.setTestname("testname");
		
		assertEquals("testname", dd.getTestname());
	}

	@Test
	public final void testGetDescription() {
		DescriptionData dd = new DescriptionData();
		dd.setDescription("description");
		
		assertEquals("description", dd.getDescription());
	}

	@Test
	public final void testSetDescription() {
		DescriptionData dd = new DescriptionData();
		dd.setDescription("description");
		
		assertEquals("description", dd.getDescription());
	}

	@Test
	public final void testGetArguments() {
		DescriptionData dd = new DescriptionData();
		dd.setArguments("argumentdescription");
		
		assertEquals("argumentdescription", dd.getArguments());
	}

	@Test
	public final void testSetArguments() {
		DescriptionData dd = new DescriptionData();
		dd.setArguments("argumentdescription");
		
		assertEquals("argumentdescription", dd.getArguments());
	}

	@Test
	public final void testGetClassname() {
		DescriptionData dd = new DescriptionData();
		dd.setClassname("classname");
		
		assertEquals("classname", dd.getClassname());
	}

	@Test
	public final void testSetClassname() {
		DescriptionData dd = new DescriptionData();
		dd.setClassname("classname");
		
		assertEquals("classname", dd.getClassname());
	}

}
