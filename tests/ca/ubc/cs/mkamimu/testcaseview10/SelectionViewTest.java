package ca.ubc.cs.mkamimu.testcaseview10;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ca.ubc.cs.mkamimu.testcaseview10.SelectionView;

public class SelectionViewTest {

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
	public final void testLargest() {
		SelectionView sv = new SelectionView();
		int i = sv.largest();
		assertEquals(-1,i);
		
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testGetOneMethodICompilationUnitInfo() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testGetCurrentProject() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testSetCurrentProject() {
		fail("Not yet implemented"); // TODO
	}

}
