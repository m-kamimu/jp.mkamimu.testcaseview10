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

		HashMap<Integer, Integer> elinelistall = new HashMap<Integer, Integer>();
		HashMap<Integer, String> elineliststrall = new HashMap<Integer, String>();

		elinelistall.put(52,3);
		elineliststrall.put(52,"project");
		
		elinelistall.put(53,3);
		elineliststrall.put(53,"project");
		
		elinelistall.put(54,3);
		elineliststrall.put(54,"project");
		
		elinelistall.put(57,2);
		elineliststrall.put(57,"description");
		
		elinelistall.put(58,1);
		elineliststrall.put(58,"String");
		
		elinelistall.put(59,2);
		elineliststrall.put(59,"description");
		
		elinelistall.put(61,2);
		elineliststrall.put(61,"JavaCore");
		
		elinelistall.put(64,4);
		elineliststrall.put(64,"cpentry");

		elinelistall.put(65,2);
		elineliststrall.put(65,"JavaCore");

		elinelistall.put(68,3);
		elineliststrall.put(68,"javaProject");

		elinelistall.put(69,3);
		elineliststrall.put(69,"options");

		elinelistall.put(70,2);
		elineliststrall.put(70,"JavaCore");

		elinelistall.put(71,3);
		elineliststrall.put(71,"options");

		elinelistall.put(72,3);
		elineliststrall.put(72,"javaProject");
		
		elinelistall.put(75,2);
		elineliststrall.put(75,"root");

		elinelistall.put(76,1);
		elineliststrall.put(76,"pack1");
		
		elinelistall.put(77,0);
		elineliststrall.put(77,"buf");
		elinelistall.put(78,0);
		elineliststrall.put(78,"buf");
		elinelistall.put(79,0);
		elineliststrall.put(79,"buf");
		elinelistall.put(80,0);
		elineliststrall.put(80,"buf");
		elinelistall.put(81,0);
		elineliststrall.put(81,"buf");
		elinelistall.put(82,0);
		elineliststrall.put(82,"buf");
		elinelistall.put(83,0);
		elineliststrall.put(83,"buf");
		elinelistall.put(84,0);
		elineliststrall.put(84,"buf");
		elinelistall.put(85,0);
		elineliststrall.put(85,"buf");
		elinelistall.put(86,0);
		elineliststrall.put(86,"buf");

		elinelistall.put(89,2);
		elineliststrall.put(89,"parser");
		elinelistall.put(90,1);
		elineliststrall.put(90,"cu");
		elinelistall.put(91,2);
		elineliststrall.put(91,"parser");
		elinelistall.put(92,2);
		elineliststrall.put(92,"parser");
		elinelistall.put(93,3);
		elineliststrall.put(93,"astRoot");

		elinelistall.put(96,4);
		elineliststrall.put(96,"rewrite");

		elinelistall.put(99,3);
		elineliststrall.put(99,"astRoot");
		elinelistall.put(100,4);
		elineliststrall.put(100,"typeDecl");
		elinelistall.put(101,5);
		elineliststrall.put(101,"Block");

		elinelistall.put(104,4);
		elineliststrall.put(104,"ast");
		elinelistall.put(105,4);
		elineliststrall.put(105,"ast");
		elinelistall.put(106,4);
		elineliststrall.put(106,"ast");
		
		elinelistall.put(108,4);
		elineliststrall.put(108,"ast");
		elinelistall.put(109,4);
		elineliststrall.put(109,"ast");
		elinelistall.put(110,4);
		elineliststrall.put(110,"ast");

		elinelistall.put(114,4);
		elineliststrall.put(114,"rewrite");
		elinelistall.put(115,5);
		elineliststrall.put(115,"listRewrite");
		elinelistall.put(116,5);
		elineliststrall.put(115,"listRewrite");

		elinelistall.put(119,3);
		elineliststrall.put(119,"res");

		
		elinelistall.put(127,0);
		elineliststrall.put(127,"preview");
		elinelistall.put(129,0);
		elineliststrall.put(129,"buf");
		elinelistall.put(130,0);
		elineliststrall.put(130,"buf");
		elinelistall.put(131,0);
		elineliststrall.put(131,"buf");
		elinelistall.put(132,0);
		elineliststrall.put(132,"buf");
		elinelistall.put(133,0);
		elineliststrall.put(133,"buf");
		elinelistall.put(134,0);
		elineliststrall.put(134,"buf");
		elinelistall.put(135,0);
		elineliststrall.put(135,"buf");
		elinelistall.put(136,0);
		elineliststrall.put(136,"buf");
		elinelistall.put(137,0);
		elineliststrall.put(137,"buf");
		elinelistall.put(138,0);
		elineliststrall.put(138,"buf");
		elinelistall.put(139,0);
		elineliststrall.put(139,"buf");
		elinelistall.put(140,0);
		elineliststrall.put(140,"preview");

		elinelistall.put(142,3);
		elineliststrall.put(142,"project");

		for(int l = 0; l < linelistall.size(); l++) {
			assertEquals(elinelistall.get(l), linelistall.get(l));
			assertEquals(elineliststrall.get(l), lineliststrall.get(l));
		}
		
		//fail("Not yet implemented"); // TODO
	}

}
