package jp.mkamimu.testcaseview10;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.Statement;

public class ASTVisitorImpl extends ASTVisitor {
	
	//private CompilationUnit cu;
	private String currentMethodName = null;

	// line, number
	//private HashMap<Integer, Integer> linelist = null;
	//private HashMap<Integer, String> lineliststr = null;
	
	private HashMap<Statement, Integer> stlist = null;
	private HashMap<Statement, String> stliststr = null;	
	
	private List<List<String>> arglist = new ArrayList<List<String>>();
	private List<String> assertarglist = new ArrayList<String>();
	
	private List<String> methodinvoname = new ArrayList<String>();
	
	private boolean searchmode = false;  // false: get simplename/arglist , true: get: linenum
	
	public void setSearchmode(boolean searchmode) {
		this.searchmode = searchmode;
	}
	
	public void setHashMap(HashMap<Statement, Integer> stlist) {
		this.stlist = stlist;
	}
	public HashMap<Statement, Integer> getHashMap() {
		return this.stlist;
	}
	
	public void setHashMapstr(HashMap<Statement, String> stliststr) {
		this.stliststr = stliststr;
	}
	public HashMap<Statement, String> getHashMapstr() {
		return this.stliststr;
	}

	
	/*
	public void setHashMap(HashMap<Integer, Integer> linelist) {
		this.linelist = linelist;
	}
	public HashMap<Integer, Integer> getHashMap() {
		return this.linelist;
	}
	
	public void setHashMapstr(HashMap<Integer, String> lineliststr) {
		this.lineliststr = lineliststr;
	}
	public HashMap<Integer, String> getHashMapstr() {
		return this.lineliststr;
	}
	*/

	
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
	
	public ASTVisitorImpl() {
		//this.cu = cu;
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
		//System.out.println(node.getName());
		//System.out.println(currentMethod.peek());
		if (node.getName().toString().equals(currentMethod.peek())) {
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

	private int sntmpflag = 0;
	
	public boolean visit(SimpleName node) {
		if (node.toString().substring(0,1).matches("[A-Z]")) {
			return super.visit(node); // starts from upper case 
		}
		
		if (methodinvoname.contains(node.toString())) {
			return super.visit(node);
		}
		
		if (assertFlag) {
			assertarglist.addAll(splitCamelCase(node.toString()));
		}
		//System.out.println(node);
		
		if (currentMethod.isEmpty() || !currentMethod.peek().equals(currentMethodName)) {
			return super.visit(node);
		}
		//System.out.println("currentmethod:" + currentMethodName);
		
		boolean tmpflag = false; 
		//System.out.println(countflag + ": Simple:" + node.toString());
		//System.out.println(countflag + ": Simpleline:" + cu.getLineNumber(node.getStartPosition()));
		//System.out.println("SimpleParent:" + node.getParent().toString());
		String worktmp = null;
		
		
		if (!this.searchmode) { // false : get simplename
			if (countflag > 0) {
				if (countflag == 1) {
					// node is equal to assert arg
					if (contains(splitCamelCase(node.toString()), assertarglist) != null) {
						tmpflag = true;
						worktmp = contains(splitCamelCase(node.toString()), assertarglist);
					}
					// node parent is equal to assert arg
					if (node.getParent() != null 
							&& contains(splitCamelCase(node.getParent().toString()), assertarglist) != null) {
						tmpflag = true;
						worktmp = contains(splitCamelCase(node.getParent().toString()), assertarglist); 
					}
					
				} else if (arglist.size() > countflag - 2) {
					// node is in previous list.
					if (contains(splitCamelCase(node.toString()), arglist.get(countflag - 2)) != null) {
						tmpflag = true;
						worktmp = contains(splitCamelCase(node.toString()), arglist.get(countflag - 2));
					}
					// node parent is in previous list
					if (node.getParent() != null 
							&& contains(splitCamelCase(node.getParent().toString()), arglist.get(countflag - 2))  != null) {
						tmpflag = true;
						worktmp = contains(splitCamelCase(node.getParent().toString()), arglist.get(countflag - 2));
					}
	
					// but parent is already in previous list. false;.
					for(int j = 2; j < countflag; j++) {
						if (contains(splitCamelCase(node.toString()), arglist.get(j - 2)) != null) {
							//System.out.println(node.toString());
							tmpflag = false;
						}
					}
					
				}
			}
		} else { // true: get linenumber  -> add simplename
			// line already has number
			if (getStatementNode((ASTNode)node) != null && stlist.get(getStatementNode((ASTNode)node)) != null) {
			//if (linelist.get(cu.getLineNumber(node.getStartPosition())) != null) {
				tmpflag = true;
			} 

			// but parent is already in previous list. false;.
			for(int j = 2; j <= countflag; j++) {
				if (contains(splitCamelCase(node.toString()), arglist.get(j - 2)) != null) {
					//System.out.println(node.toString());
					tmpflag = false;
				}
			}
		}
			
		if (tmpflag == true) {
			sntmpflag++;
		}
		//System.out.println("allflag: " + tmpflag);

		if (sntmpflag > 0) {
			if (!this.searchmode) { // add number
				if (getStatementNode((ASTNode)node) != null && stlist.get(getStatementNode((ASTNode)node)) == null) {
				//if (linelist.get(cu.getLineNumber(node.getStartPosition())) == null) {
					stlist.put(getStatementNode((ASTNode)node), countflag - 1);
					//linelist.put(cu.getLineNumber(node.getStartPosition()), countflag - 1);
					if (worktmp != null) {
						stliststr.put(getStatementNode((ASTNode)node), worktmp);
					}
					/*
					if (contains(splitCamelCase(node.toString()), assertarglist) != null) {
						stliststr.put(getStatementNode((ASTNode)node), contains(splitCamelCase(node.toString()), assertarglist));
					} else if (countflag >= 1 && arglist.size() > countflag - 1) {
						stliststr.put(getStatementNode((ASTNode)node), contains(splitCamelCase(node.toString()), arglist.get(countflag - 1)));
					}
					*/
					//lineliststr.put(cu.getLineNumber(node.getStartPosition()), node.toString());
					//System.out.println(node.toString() +":a:" + cu.getLineNumber(node.getStartPosition()) +":"+ (countflag - 1));
					
				} else {
					//System.out.println(node.toString() +":b:" + cu.getLineNumber(node.getStartPosition()) +":"+ (countflag - 1));
				}

			} else { // add simplename
				
				if (!methodinvoname.contains(node.toString())) {
					for(int j = 2; j < countflag; j++) {
						if (contains(splitCamelCase(node.toString()), arglist.get(j - 2)) != null) {
							//System.out.println(node.toString());
							tmpflag = false;
						}
					}
					if (tmpflag) {
						//System.out.println("not in :" + (countflag - 1) + ":" + node.toString());
						List<String> arglistcount = new ArrayList<String>();
						arglistcount.addAll(splitCamelCase(node.toString()));
						
						if (arglist.size() > countflag - 1 && countflag > 0) {
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
	
	private Statement getStatementNode(ASTNode node) {
		ASTNode tmpnode = node.getParent();
		while(tmpnode != null) {
			if (tmpnode instanceof Statement) {
				return (Statement) tmpnode; 
			} else {
				tmpnode = tmpnode.getParent();
			}
		}
		return (Statement)tmpnode;
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
					assertarglist.addAll(splitCamelCase(node.arguments().get(i).toString()));
				}
			}
			if (node.getExpression() != null) {
				assertarglist.addAll(splitCamelCase(node.getExpression().toString()));
				if (node.getExpression().toString().contains(".")) {
					String tmp = node.getExpression().toString();
					while(tmp.indexOf(".") > 0) {
						tmp = tmp.substring(0, tmp.indexOf("."));
						//System.out.println("tmp:" + tmp);
						assertarglist.addAll(splitCamelCase(tmp));
					}
				}
			}
			//System.out.println("assertnodep:" + node.getExpression());
			
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
		if (assertFlag && node.getName().toString().startsWith("assert")) {
			assertFlag = false;
		}
		super.endVisit(node);
	}
	
	
	private static List<String> splitCamelCase(String s) {
	   String s2 = s.replace(".", " ");
	   String s3 = s2.replace("(", " ");
	   String s4 = s3.replace(")"," ");
	   String s5 = s4.replace("\""," ");
	   String s6 = s5.replace("["," ");
	   String s7 = s6.replace("]"," ");
	   String s8 = s7.replace("{"," ");
	   String s9 = s8.replace("}"," ");
	   String s10 = s9.replace("="," ");
	   String stmp = s10.replaceAll(
	      String.format("%s|%s",
	         "(?<=[A-Z])(?=[A-Z][a-z])",
 	         "(?<=[^A-Z])(?=[A-Z])"
	      /*String.format("%s|%s|%s",
	         "(?<=[A-Z])(?=[A-Z][a-z])",
	         "(?<=[^A-Z])(?=[A-Z])",
	         "(?<=[A-Za-z])(?=[^A-Za-z])"*/
	      ),
	      " "
	   );
	   String str[] = stmp.split(" ");
	   List<String> strlist = new ArrayList<String>();
	   for(int i = 0; i < str.length;i++) {
		   if (!str[i].equals("()")) {
			   strlist.add(str[i]);
		   }
	   }
	   return strlist;
	}
	
	private static String contains(List<String> wordlist,  List<String> targetlist) {
		for(int i = 0; i < wordlist.size(); i++) {
			if (targetlist.contains(wordlist.get(i))) {
				return wordlist.get(i);
			}
		}
		return null;
	}
	
		
}
