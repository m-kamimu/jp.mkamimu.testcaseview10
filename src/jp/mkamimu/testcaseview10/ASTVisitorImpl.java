package jp.mkamimu.testcaseview10;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.SimpleName;

public class ASTVisitorImpl extends ASTVisitor {
	
	private CompilationUnit cu;
	private String currentMethodName = null;

	// line, number
	private HashMap<Integer, Integer> linelist = null;
	
	private List<List<String>> arglist = new ArrayList<List<String>>();
	private List<String> assertarglist = new ArrayList<String>();
	
	//private List<List<String>> wholelist = new ArrayList<List<String>>();
	//private List<String> wholeassertarglist = new ArrayList<String>();
	
	private List<String> methodinvoname = new ArrayList<String>();
	
	private boolean searchmode = false;  // false: get simplename/arglist , true: get: linenum
	
	public void setSearchmode(boolean searchmode) {
		this.searchmode = searchmode;
	}
	public void setHashMap(HashMap<Integer, Integer> linelist) {
		this.linelist = linelist;
	}
	public HashMap<Integer, Integer> getHashMap() {
		return this.linelist;
	}
	
	public boolean needAnalysis() {
		//System.out.println(arglist.size() + ":" + countflag);
		if (arglist.size() >= countflag) {
			return true;
		} else {
			return false;
		}
	}
	

	private int countflag = 0;
	
	public void countup() {
		countflag++;
	}
	
	ASTVisitorImpl(CompilationUnit cu) {
		this.cu = cu;
	}
	
	public void setCurrentMethod(String currentMethodName) {
		this.currentMethodName = currentMethodName;
	}

	Stack<String> currentMethod = new Stack<String>();
	
	/* (non-Javadoc)
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.MethodDeclaration)
	 */
	@Override
	public boolean visit(MethodDeclaration node) {
		// TODO Auto-generated method stub
		currentMethod.push(node.getName().toString());
		return super.visit(node);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#endVisit(org.eclipse.jdt.core.dom.MethodDeclaration)
	 */
	@Override
	public void endVisit(MethodDeclaration node) {
		// TODO Auto-generated method stub
		if (node.getName().equals(currentMethod.peek())) {
			currentMethod.pop();
		}
		super.endVisit(node);
	}
	
	
	public String printassertarglist() {
		StringBuffer strbuf = new StringBuffer();
		for(int i = 0; i < assertarglist.size(); i++) {
			System.out.println("assertarglist: " + assertarglist.get(i).toString());
			strbuf.append("assertarglist: " + assertarglist.get(i).toString());
			strbuf.append("\n");
		}
		return strbuf.toString();
	}

	public String printarglist() {
		StringBuffer strbuf = new StringBuffer();
		for(int i = 0; i < arglist.size(); i++) {
			for (int j = 0; j < arglist.get(i).size(); j++) {
				System.out.println("arglist: " + i + ":" + arglist.get(i).get(j).toString());
				strbuf.append("arglist: " + i + ":" + arglist.get(i).get(j).toString());
				strbuf.append("\n");
			}
		}
		return strbuf.toString();
	}

	/*
	public String printassertwholelist() {
		StringBuffer strbuf = new StringBuffer();
		for(int i = 0; i < wholeassertarglist.size(); i++) {
			System.out.println("assertwholelist: " + wholeassertarglist.get(i).toString());
			strbuf.append("assertwholelist: " + wholeassertarglist.get(i).toString());
			strbuf.append("\n");
		}
		return strbuf.toString();
	}

	public String printwholelist() {
		StringBuffer strbuf = new StringBuffer();
		for(int i = 0; i < wholelist.size(); i++) {
			for (int j = 0; j < wholelist.get(i).size(); j++) {
				System.out.println("wholelist: " + i + ":" + wholelist.get(i).get(j).toString());
				strbuf.append("wholelist: " + i + ":" + wholelist.get(i).get(j).toString());
				strbuf.append("\n");
			}
			System.out.println("wholelist: " + wholelist.get(i).toString());
		}
		return strbuf.toString();
	}*/
	
	private int sntmpflag = 0;
	
	public boolean visit(SimpleName node) {
		if (methodinvoname.contains(node.toString())) {
			return super.visit(node);
		}
		
		if (assertFlag) {
			assertarglist.add(node.toString());
		}
		//System.out.println(node);
		
		
		if (currentMethod.isEmpty() || !currentMethod.peek().equals(currentMethodName)) {
			return super.visit(node);
		}
		
		boolean tmpflag = false; 
		System.out.println(countflag + ": Simple:" + node.toString());
		System.out.println(countflag + ": Simpleline:" + cu.getLineNumber(node.getStartPosition()));
		//System.out.println("SimpleParent:" + node.getParent().toString());
		
		if (!this.searchmode) { // false : get simplename
			if (countflag > 0) {
				if (countflag == 1) {
					// node is equal to assert arg
					if (assertarglist.contains(node.toString())) {
						tmpflag = true;
					}
					// node parent is equal to asssert arg
					if (node.getParent() != null 
							&& assertarglist.contains(node.getParent().toString())) {
						tmpflag = true;
					}
					
				} else if (arglist.size() > countflag - 2) {
					// node is in previous list.
					if (arglist.get(countflag - 2).contains(node.toString())) {
						tmpflag = true;
					}
					// node parent is in previous list
					if (node.getParent() != null 
							&& arglist.get(countflag - 2).contains(node.getParent().toString())) {
						tmpflag = true;
					}
	
					// but parent is already in previous list. false;.
					for(int j = 2; j < countflag; j++) {
						if (arglist.get(j - 2).contains(node.toString())) {
							System.out.println(node.toString());
							tmpflag = false;
						}
					}
					
				}
			}
		} else { // true: get linenumber  -> add simplename
			// line already has number
			if (linelist.get(cu.getLineNumber(node.getStartPosition())) != null) {
				tmpflag = true;
			} 

			// but parent is already in previous list. false;.
			for(int j = 2; j <= countflag; j++) {
				if (arglist.get(j - 2).contains(node.toString())) {
					System.out.println(node.toString());
					tmpflag = false;
				}
			}
		}
			
		if (tmpflag == true) {
			sntmpflag++;
		}
		System.out.println("allflag: " + tmpflag);

		if (sntmpflag > 0) {
			if (!this.searchmode) { // add number
				if (linelist.get(cu.getLineNumber(node.getStartPosition())) == null) {
					linelist.put(cu.getLineNumber(node.getStartPosition()), countflag - 1);
					System.out.println(node.toString() +":a:" + cu.getLineNumber(node.getStartPosition()) +":"+ (countflag - 1));
					
				} else {
					System.out.println(node.toString() +":b:" + cu.getLineNumber(node.getStartPosition()) +":"+ (countflag - 1));
				}

			/*	
				List<Integer> linelistcount = new ArrayList<Integer>();
				List<String> arglistcount = new ArrayList<String>();
				List<String> wholelistcount = new ArrayList<String>();
	
				if (!node.toString().equals("null")) {
					arglistcount.add(node.toString());
					linelistcount.add(cu.getLineNumber(node.getStartPosition()));
				}
				wholelistcount.add(node.getParent().toString().replaceAll("\n",""));
				//System.out.println(node.toString() +"thisline:" + cu.getLineNumber(node.getStartPosition()) +":"+ (countflag - 1));
				
				if (arglist.size() > countflag - 1) {
					List<String> tmparglistcount = arglist.get(countflag - 1);
					tmparglistcount.addAll(arglistcount);
					arglist.set(countflag - 1, tmparglistcount);
					
					/*List<String> tmpwholelistcount = wholelist.get(countflag - 1);
					tmpwholelistcount.addAll(wholelistcount);
					wholelist.set(countflag - 1, tmpwholelistcount);*/
					
					//List<Integer> tmplinelistcount = linelist.get(countflag - 1);
					//tmplinelistcount.addAll(linelistcount);
					//linelist.set(countflag - 1, tmplinelistcount);
//						if (linelist.get(cu.getLineNumber(node.getStartPosition())) == null) {
//							linelist.put(cu.getLineNumber(node.getStartPosition()), countflag - 1);
//						//	System.out.println(node.toString() +"b:" + cu.getLineNumber(node.getStartPosition()) +":"+ (countflag - 1));
//						}
				/*	
					} else {
						if (!arglist.contains(arglistcount)) {
							arglist.add(arglistcount);
							//wholelist.add(wholelistcount);
							//linelist.add(linelistcount);
//							if (linelist.get(cu.getLineNumber(node.getStartPosition())) == null) {
//								linelist.put(cu.getLineNumber(node.getStartPosition()), countflag - 1);
//							//	System.out.println(node.toString() +"a:" + cu.getLineNumber(node.getStartPosition()) +":"+ (countflag - 1));
//							}
						}
					}*/
			} else { // add simplename
//					if (linelist.get(cu.getLineNumber(node.getStartPosition())) == null) {
//						linelist.put(cu.getLineNumber(node.getStartPosition()), countflag - 1);
//						System.out.println(node.toString() +":a:" + cu.getLineNumber(node.getStartPosition()) +":"+ (countflag - 1));
//						
//					} else {
//						System.out.println(node.toString() +":b:" + cu.getLineNumber(node.getStartPosition()) +":"+ (countflag - 1));
//					}
				
				if (!methodinvoname.contains(node.toString())) {
					for(int j = 2; j < countflag; j++) {
						if (arglist.get(j - 2).contains(node.toString())) {
							System.out.println(node.toString());
							tmpflag = false;
						}
					}
					if (tmpflag) {
						System.out.println("not in :" + (countflag - 1) + ":" + node.toString());
						List<String> arglistcount = new ArrayList<String>();
						arglistcount.add(node.toString());
						
						if (arglist.size() > countflag - 1) {
							List<String> tmparglistcount = arglist.get(countflag - 1);
							tmparglistcount.addAll(arglistcount);
							arglist.set(countflag - 1, tmparglistcount);
						} else {
							arglist.add(arglistcount);
						}
					}
				}
			}
		}
		
		return super.visit(node);
	}
	
	public void endVisit(SimpleName node) {
		if (sntmpflag > 0) {
			sntmpflag--;
		}
	}
	
	
	private boolean assertFlag = false;
	/* (non-Javadoc)
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.MethodInvocation)
	 */
	@Override
	public boolean visit(MethodInvocation node) {
		if (!methodinvoname.contains(node.getName().toString())) {
			methodinvoname.add(node.getName().toString());
		}
		
		if (currentMethod.isEmpty() || !currentMethod.peek().equals(currentMethodName)) {
			return super.visit(node);
		}
		
		// TODO Auto-generated method stub
		if ((assertFlag || node.getName().toString().startsWith("assert")) && countflag == 0) {
			
			for(int i = 0; i < node.arguments().size(); i++) {
				if (!node.arguments().get(i).toString().equals("null")) {
					assertarglist.add(node.arguments().get(i).toString());
				}
			}
			if (node.getExpression() != null) {
				assertarglist.add(node.getExpression().toString());
				if (node.getExpression().toString().contains(".")) {
					String tmp = node.getExpression().toString();
					while(tmp.indexOf(".") > 0) {
						tmp = tmp.substring(0, tmp.indexOf("."));
						//System.out.println("tmp:" + tmp);
						assertarglist.add(tmp);
					}
				}
			}
			//wholeassertarglist.add(node.toString());
			
			//System.out.println("assertname: " + node.getName());
			System.out.println("assertnodep:" + node.getExpression());
			
			assertFlag = true;
		} else if (!(assertFlag || node.getName().toString().startsWith("assert"))) {
			//methodnamelist.add(node.getName().toString());
		}
		
		return super.visit(node);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#endVisit(org.eclipse.jdt.core.dom.MethodInvocation)
	 */
	@Override
	public void endVisit(MethodInvocation node) {
		// TODO Auto-generated method stub
		if (assertFlag || node.getName().toString().startsWith("assert")) {
			assertFlag = false;
		}
		super.endVisit(node);
	}
	
	
}
