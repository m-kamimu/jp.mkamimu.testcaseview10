package jp.mkamimu.testcaseview10.tests;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.List;

import jp.mkamimu.testcaseview10.ASTMethodDeclarationVisitorImpl;
import jp.mkamimu.testcaseview10.ASTVisitorImpl;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
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
        StringBuffer sb = new StringBuffer();
		
		try {
	        FileReader f = new FileReader("testdata/ASTRewriteSnippet.java");
	        BufferedReader b = new BufferedReader(f);
	        String s;
	        while((s = b.readLine())!=null){
	            sb.append(s);
	            sb.append("\n");
	        }
	        b.close();
		} catch (Exception e) {
	       	e.printStackTrace();
	    }

		ASTParser parser = ASTParser.newParser(AST.JLS4);
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		parser.setSource(sb.toString().toCharArray());
		CompilationUnit unitp = (CompilationUnit)parser.createAST(new NullProgressMonitor());
		//ASTVisitorImpl astvis = new ASTVisitorImpl(unitp);
		
		System.out.println("Start");
		ASTMethodDeclarationVisitorImpl astmvis = new ASTMethodDeclarationVisitorImpl();
		unitp.accept(astmvis);
		List<String> methodlist = astmvis.getMethodDeclarationList();

		HashMap<Integer, Integer> linelistall = new HashMap<Integer, Integer>();
		HashMap<Integer, String> lineliststrall = new HashMap<Integer, String>();
		
		for (int j = 0; j < methodlist.size(); j++) {
			ASTVisitorImpl astvis = new ASTVisitorImpl(unitp);
			String methodname = methodlist.get(j);
			//str.append(methodname + "\n");
			System.out.println(methodname + "\n");
			
			astvis.setCurrentMethod(methodname);
			astvis.setHashMap(linelistall);
			astvis.setHashMapstr(lineliststrall);
			// for assert
			astvis.setSearchmode(false);
			unitp.accept(astvis);
			astvis.setSearchmode(true);
			unitp.accept(astvis);
			
			// first arg
			while(astvis.needAnalysis()) {
				//for (int i = 0; i < 10; i++) {
				astvis.countup();
				astvis.setSearchmode(false);
				unitp.accept(astvis);
				astvis.setSearchmode(true);
				unitp.accept(astvis);
			}
			
			astvis.printassertarglist();
			astvis.printarglist();
			linelistall = astvis.getHashMap();
		}

		for(Integer keys : linelistall.keySet()) {
			System.out.println(keys +":"+ linelistall.get(keys));
		}

		try {
			BufferedReader reader = new BufferedReader(new FileReader("testdata/ASTRewriteSnippet.java"));
			int l = 1;
			String line;
			while((line = reader.readLine()) != null) {
				Integer linenum = linelistall.get(l);
				String linetoken = lineliststrall.get(l);
				
				if (linenum == null) {
					System.out.println(l + "::								"+line);
					//str.append(l + "::								"+line+"\n");
				} else {
					System.out.println(l+":" + linelistall.get(l) + ":" + lineliststrall.get(l) + ":						"+line);
					//str.append(l+":" + linelistall.get(l) + ":" + lineliststrall.get(l) + ":						"+line+"\n");
				}
				l++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		fail("Not yet implemented"); // TODO
	}

}
