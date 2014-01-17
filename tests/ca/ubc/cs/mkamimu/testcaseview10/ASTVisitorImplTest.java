package ca.ubc.cs.mkamimu.testcaseview10;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ASTVisitorImplTest {

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
	public final void testASTVisitorImpl() {
		/*
		ASTParser parser = ASTParser.newParser(AST.JLS4);
		parser.setSource(unit);
		CompilationUnit unitp = (CompilationUnit)parser.createAST(new NullProgressMonitor());
		ASTVisitorImpl astvis = new ASTVisitorImpl(unitp, this.globalTestInformation);
		unitp.accept(astvis);

		CompilationUnit cu = new CompilationUnit(null);
		TestInformation testinfo = new TestInformation();
		ASTVisitorImpl ast = new ASTVisitorImpl(cu, testinfo);
		*/
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testVisitTypeDeclaration() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testEndVisitTypeDeclaration() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testVisitMethodDeclaration() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testEndVisitMethodDeclaration() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testVisitMethodInvocation() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testEndVisitMethodInvocation() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testEndVisitClassInstanceCreation() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testVisitClassInstanceCreation() {
		fail("Not yet implemented"); // TODO
	}

}
