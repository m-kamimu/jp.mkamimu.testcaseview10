package ca.ubc.cs.mkamimu.testcaseview10;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestInformationTest {

	TestInformation testinfo;
	@Before
	public void setUp() throws Exception {
		testinfo = new TestInformation();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public final void testIsLock() {
		assertFalse(testinfo.isLock());
	}

	@Test
	public final void testSetLock() {
		testinfo.setLock(true);
		assertTrue(testinfo.isLock());
	}

	@Test
	public final void testGetMethodIintlList() {
		List<Integer> list = testinfo.getMethodIintlList();
		assertNotNull(list);
		assertEquals(0,list.size());
	}

	@Test
	public final void testSetMethodIintlList() {
		List<Integer> listl = new ArrayList<Integer>();
		listl.add(1);
		testinfo.setMethodIintlList(listl);
		
		List<Integer> list = testinfo.getMethodIintlList();
		assertNotNull(list);
		assertEquals(1,list.size());
	}

	@Test
	public final void testGetMethodAintlList() {
		List<Integer> list = testinfo.getMethodAintlList();
		assertNotNull(list);
		assertEquals(0,list.size());
	}

	@Test
	public final void testSetMethodAintlList() {
		List<Integer> listl = new ArrayList<Integer>();
		listl.add(1);
		testinfo.setMethodAintlList(listl);
		
		List<Integer> list = testinfo.getMethodAintlList();
		assertNotNull(list);
		assertEquals(1,list.size());
	}

	@Test
	public final void testGetMethodIintsList() {
		List<Integer> list = testinfo.getMethodIintsList();
		assertNotNull(list);
		assertEquals(0,list.size());
	}

	@Test
	public final void testSetMethodIintsList() {
		List<Integer> listl = new ArrayList<Integer>();
		listl.add(1);
		testinfo.setMethodIintsList(listl);
		
		List<Integer> list = testinfo.getMethodIintsList();
		assertNotNull(list);
		assertEquals(1,list.size());
	}

	@Test
	public final void testGetMethodAintsList() {
		List<Integer> list = testinfo.getMethodAintsList();
		assertNotNull(list);
		assertEquals(0,list.size());
	}

	@Test
	public final void testSetMethodAintsList() {
		List<Integer> listl = new ArrayList<Integer>();
		listl.add(1);
		testinfo.setMethodAintsList(listl);
		
		List<Integer> list = testinfo.getMethodAintsList();
		assertNotNull(list);
		assertEquals(1,list.size());
	}

	@Test
	public final void testGetMethodAOccurence() {
		List<String> listl = new ArrayList<String>();
		listl.add("methoddescription1");
		listl.add("methoddescription1");
		listl.add("methoddescription2");
		testinfo.setMethodAList(listl);
		
		int occurence = testinfo.getMethodAOccurence("methoddescription1");
		assertEquals(2,occurence);
	}

	@Test
	public final void testGetMethodAOccurenceInTest() {
		List<String> listd = new ArrayList<String>();
		listd.add("method1");
		testinfo.setMethodADList(listd);
		
		List<String> listl = new ArrayList<String>();
		listl.add("methoddescription1");
		testinfo.setMethodAList(listl);
		
		int occurence = testinfo.getMethodAOccurenceInTest("methoddescription1","method1");
		assertEquals(1,occurence);
	}

	@Test
	public final void testGetMethodIOccurence() {
		List<String> listl = new ArrayList<String>();
		listl.add("methoddescription1");
		listl.add("methoddescription1");
		listl.add("methoddescription2");
		testinfo.setMethodIList(listl);
		
		int occurence = testinfo.getMethodIOccurence("methoddescription1");
		assertEquals(2,occurence);
	}

	@Test
	public final void testGetMethodIOccurenceInTest() {
		List<String> listd = new ArrayList<String>();
		listd.add("method1");
		testinfo.setMethodIDList(listd);
		
		List<String> listl = new ArrayList<String>();
		listl.add("methoddescription1");
		testinfo.setMethodIList(listl);
		
		int occurence = testinfo.getMethodIOccurenceInTest("methoddescription1","method1");
		assertEquals(1,occurence);
	}

	@Test
	public final void testGetMethodDList() {
		List<String> listd = new ArrayList<String>();
		listd.add("method1");
		testinfo.setMethodDList(listd);
		
		List<String> listd2 = testinfo.getMethodDList();
		assertEquals("method1", listd2.get(0));
	}

	@Test
	public final void testGetMethodIList() {
		List<String> listd = new ArrayList<String>();
		listd.add("method1");
		testinfo.setMethodIList(listd);
		
		List<String> listd2 = testinfo.getMethodIList();
		assertEquals("method1", listd2.get(0));
	}

	@Test
	public final void testGetMethodAList() {
		List<String> listd = new ArrayList<String>();
		listd.add("method1");
		testinfo.setMethodAList(listd);
		
		List<String> listd2 = testinfo.getMethodAList();
		assertEquals("method1", listd2.get(0));
	}

	@Test
	public final void testGetMethodIDList() {
		List<String> listd = new ArrayList<String>();
		listd.add("method1");
		testinfo.setMethodIDList(listd);
		
		List<String> listd2 = testinfo.getMethodIDList();
		assertEquals("method1", listd2.get(0));
	}

	@Test
	public final void testGetMethodADList() {
		List<String> listd = new ArrayList<String>();
		listd.add("method1");
		testinfo.setMethodADList(listd);
		
		List<String> listd2 = testinfo.getMethodADList();
		assertEquals("method1", listd2.get(0));
	}
	
	@Test
	public final void testGetMethodAArgList() {
		List<String> listd = new ArrayList<String>();
		listd.add("method1");
		testinfo.setMethodADList(listd);
		testinfo.getMethodAArgList().add("arguments1");
		
		List<String> listd2 = testinfo.getMethodADList();
		assertEquals("method1", listd2.get(0));
		
		List<String> listd2a = testinfo.getMethodAArgList();
		assertEquals("arguments1", listd2a.get(0));
	}

	@Test
	public final void testGetMethodIArgList() {
		List<String> listd = new ArrayList<String>();
		listd.add("method1");
		testinfo.setMethodIDList(listd);
		testinfo.getMethodIArgList().add("arguments1");
		
		List<String> listd2 = testinfo.getMethodIDList();
		assertEquals("method1", listd2.get(0));
		
		List<String> listd2a = testinfo.getMethodIArgList();
		assertEquals("arguments1", listd2a.get(0));
	}


}
