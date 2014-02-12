package jp.mkamimu.testcaseview10.tests;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.List;

import jp.mkamimu.testcaseview10.ASTMethodDeclarationVisitorImpl;
import jp.mkamimu.testcaseview10.ASTVisitorImpl;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.Statement;
import org.junit.Test;

public class ASTVisitorImplTest {

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

		HashMap<Statement, Integer> linelistall = new HashMap<Statement, Integer>();
		HashMap<Statement, String> lineliststrall = new HashMap<Statement, String>();
		
		for (int j = 0; j < methodlist.size(); j++) {
			ASTVisitorImpl astvis = new ASTVisitorImpl();
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

		for(Statement keys : linelistall.keySet()) {
			System.out.println(keys +":"+ linelistall.get(keys));
		}
		
		

		

		/*
		int l = 1;
		try {
			BufferedReader reader = new BufferedReader(new FileReader("testdata/ASTRewriteSnippet.java"));
			String line;
			while((line = reader.readLine()) != null) {
				Integer linenum = linelistall.get(l);
				//String linetoken = lineliststrall.get(l);
				
				if (linenum == null) {
					System.out.println(l + "::								"+line);
					//str.append(l + "::								"+line+"\n");
				} else {
					System.out.println(l+":" + linelistall.get(l) + ":" + lineliststrall.get(l) + ":						"+line);
					//str.append(l+":" + linelistall.get(l) + ":" + lineliststrall.get(l) + ":						"+line+"\n");
				}
				l++;
			}
			reader.close();
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
		
		elinelistall.put(57,3);
		elineliststrall.put(57,"project");
		
		elinelistall.put(58,4);
		elineliststrall.put(58,"description");
		
		elinelistall.put(59,3);
		elineliststrall.put(59,"project");
		
		elinelistall.put(61,3);
		elineliststrall.put(61,"javaProject");
		
		elinelistall.put(64,4);
		elineliststrall.put(64,"cpentry");

		elinelistall.put(65,3);
		elineliststrall.put(65,"javaProject");

		elinelistall.put(68,3);
		elineliststrall.put(68,"javaProject");

		elinelistall.put(69,4);
		elineliststrall.put(69,"options");

		elinelistall.put(70,4);
		elineliststrall.put(70,"options");

		elinelistall.put(71,4);
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
		elineliststrall.put(101,"block");

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
		elineliststrall.put(116,"listRewrite");

		elinelistall.put(119,3);
		elineliststrall.put(119,"res");

		elinelistall.put(122,1);
		elineliststrall.put(122,"cu");
		elinelistall.put(123,2);
		elineliststrall.put(123,"document");
		elinelistall.put(124,1);
		elineliststrall.put(124,"cu");
		
		
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

		for(int m = 0; m < l; m++) {
			System.out.println(m +","+elinelistall.get(m)+"," +linelistall.get(m));
			assertEquals(elinelistall.get(m), linelistall.get(m));
			
			System.out.println(m +","+elineliststrall.get(m)+"," +lineliststrall.get(m));
			assertEquals(elineliststrall.get(m), lineliststrall.get(m));
		}*/
		
		fail("Not yet implemented"); // TODO
	}

}
